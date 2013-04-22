package napoleon.model.role;

import java.io.Serializable;
import java.util.*;

import napoleon.model.card.Card;
import napoleon.model.player.ManualNapoleon;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.resource.Messages;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Status;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.model.rule.TurnFactory;
import napoleon.model.rule.TurnStatus;
import napoleon.view.ConsoleViewer;
import napoleon.view.Viewer;

import org.apache.commons.collections15.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static napoleon.model.resource.Messages.*;


public class Director implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3142206610755761773L;
	
	protected static final int NUMBER_OF_MEMBERS = 4;
	protected Table table;
	protected Dealer dealer;
	protected Player[] players;
	protected Declaration fixedDeclaration;
	protected Napoleon napoleon;
	protected boolean isNobodyDeclared;
	protected boolean extraCardChanged;
	protected Turn[] turns;
	private Integer currentTurnNo = 1;
	private Logger logger;
    protected Viewer viewer;

	protected Director(){
		logger = LogManager.getLogger(Director.class.getName());
	}

	public static Director New(Table table, Player[] players, ConsoleViewer viewer) {
		Director instance = new Director();
		instance.table = table;
		instance.dealer = Dealer.New(table, players);
		instance.players = players;
		instance.viewer = viewer;
		return instance;
	}

	public Status getGameState() {
		return !dealer.hasServed() ? Status.Initial 
				: isNobodyDeclared ? Status.GameEnded 
				: null == napoleon ? Status.CardServed
				: extraCardChanged ? Status.ExtraCardsChanged
				: Status.NapoleonDefined;
	}

	public void serveCards() {
		dealer.Serve();
	}
	
	public void defineNapoleon() {
		HashMap<Player, Declaration> lastDeclarationsOfPlayer = new HashMap<Player, Declaration>();
		defineNapoleon(null, lastDeclarationsOfPlayer);
	}
	
	private void defineNapoleon(
			Declaration unFixedDeclaration, 
			HashMap<Player, Declaration> lastDeclarationsOfPlayer) {
		Declaration currentDeclaration = unFixedDeclaration;
		for(Player player : players) {
			if(lastDeclarationsOfPlayer.containsKey(player) && currentDeclaration == lastDeclarationsOfPlayer.get(player)) {
				fixedDeclaration = currentDeclaration;
                GameContext.Init(currentDeclaration.getSuit());
				turns = TurnFactory.Get12Turns(fixedDeclaration.getSuit());
				napoleon = Napoleon.New(player);
				players[Arrays.asList(players).indexOf(player)] = napoleon;
				logger.info(String.format("★napoleon fixed:%s, %s", napoleon, fixedDeclaration));
				viewer.showMessage(
                        String.format(
                                RESOURCE.getString(NAPOLEON_FIXED),
                                napoleon.getName(), fixedDeclaration.toShow()));
				return;
			}
			currentDeclaration = askForDeclare(currentDeclaration, lastDeclarationsOfPlayer, player);
		}
		
		if(allPlayerPassed(lastDeclarationsOfPlayer)) {
			isNobodyDeclared = true;
			logger.info("★all players passed.");
			return;
		}
		defineNapoleon(currentDeclaration, lastDeclarationsOfPlayer);
	}

	private Declaration askForDeclare(Declaration currentDeclaration,
			HashMap<Player, Declaration> lastDeclarationsOfPlayer, Player player) {
		Declaration declaration = player.askForDeclare(currentDeclaration, viewer);
		while(Declaration.Pass != declaration && !declaration.isStrongerDecralation(currentDeclaration)) {
			declaration = player.askForDeclare(currentDeclaration, viewer);
		}
		
		lastDeclarationsOfPlayer.put(player, declaration);
		logger.info(String.format("%s\t%s", player, declaration));
		if(declaration != Declaration.Pass) {
			currentDeclaration = declaration;
		}
		return currentDeclaration;
	}

	private boolean allPlayerPassed(HashMap<Player,Declaration> lastDeclarationsOfPlayer) {
		return lastDeclarationsOfPlayer.size() == NUMBER_OF_MEMBERS 
				&& !existsAnyDeclarations(lastDeclarationsOfPlayer);
	}

	private boolean existsAnyDeclarations(
			HashMap<Player, Declaration> lastDeclarationsOfPlayer) {
		return CollectionUtils.exists(lastDeclarationsOfPlayer.values(), new Predicate<Declaration>() {
			
			@Override
			public boolean evaluate(Declaration lastDeclaration) {
				return lastDeclaration.isDeclared();
			}
        });
	}

	public Declaration getDeclaration() {
		return fixedDeclaration;
	}

	public Napoleon getNapoleon() {
		return napoleon;
	}

	public void letNapoleonChangeExtraCards() {
		if(napoleon instanceof ManualNapoleon)
			viewer.showExtraCards(table.getCards());

		napoleon.changeExtraCards(fixedDeclaration, table, viewer);
		extraCardChanged = true;
	}

	public void askForAdjutant() {
        GameContext.getCurrent().setAdjutantCard(getNapoleon().tellTheAdjutant(fixedDeclaration, viewer));
		logger.info(String.format("adjutant card is %s", GameContext.getCurrent().getAdjutantCard()));
		viewer.showMessage(String.format(RESOURCE.getString(CARD_OF_ADJUTANT), GameContext.getCurrent().getAdjutantCard()));
		Player adjutant = findAdjutant(GameContext.getCurrent().getAdjutantCard());
		if(null == adjutant) return;
		adjutant.setIsAdjutant(true);
	}

	public void showSituation() {
		viewer.show(players);
	}
	
	public Integer getCurrentTurnNo() {
		return currentTurnNo;
	}
	
	public TurnStatus getCurrentTurnStatus() {
		return getTurn(currentTurnNo).getStatus();
	}

	public void beginTurn(int turnNo) {
		Turn turn = getTurn(turnNo);
		for (Player p : getPlayersForTurn(turnNo)){
			turn.addCard(p, p.openCard(turn, viewer, fixedDeclaration));
		}
		
		turn.winnerGainCards();
		viewer.showTurnResult(currentTurnNo, getTurnWinnerName(currentTurnNo), turn.getCardsToShow(viewer));
		
		viewer.showPlayersGainedCards(players);
		currentTurnNo++;
	}

	private Turn getTurn(int turnNo) {
		return turns[turnNo -1];
	}

	Collection<Player> getPlayersForTurn(int turnNo) {
		ArrayList<Player> list = new ArrayList<Player>(); 
		int leadPlayerIndex = 
				turnNo == 1 
				? Arrays.asList(players).indexOf(findPlayerByName(napoleon.getName()))
				: Arrays.asList(players).indexOf(getTurnWinner(turnNo - 1));
		for(int i = 0; i < Table._PLAYERS_COUNT; i++){
			Player p = players[(leadPlayerIndex + i) % Table._PLAYERS_COUNT];
			logger.info(p);
			list.add(p);
		}
		return list;
	}

	private Player findPlayerByName(final String name) {
		return CollectionUtils.find(Arrays.asList(players), new Predicate<Player>() {

			@Override
			public boolean evaluate(Player player) {
				return name.equals(player.getName());
			}
		});
	}

	private Player getTurnWinner(int turnNo) {
		return getTurn(turnNo).getWinner();
	}

	public String getTurnWinnerName(int turnNo) {
		return getTurnWinner(turnNo).getName();
	}
	
	public Team JudgeWinnerTeam(){
		viewer.showGainedCardsForEachPlayer(getNapoleon(), players);
		viewer.showExchangedCards(table);
		
		final Team winnerTeam = getWinnerTeam();
		viewer.showMessage(String.format(RESOURCE.getString(PLAYER_OF_ADJUTANT), getAdjutantName()));
		viewer.showMessage(String.format(
                RESOURCE.getString(WINNER_DETAIL),
                getCardCountNapoleonTeamGained(),
                RESOURCE.getString(winnerTeam.name())));
		return winnerTeam;
	}

	protected Team getWinnerTeam() {
		int napoleonTeamGained = getCardCountNapoleonTeamGained();
		if(napoleonTeamGained == Card.PictureCardCount)
			return Team.AlliedForcesTeam;
		
		if(fixedDeclaration.getCardsToCollect() <= napoleonTeamGained){
			return Team.NapoleonTeam;
		} else {
			return Team.AlliedForcesTeam;
		}
	}

	private int getCardCountNapoleonTeamGained() {
		Player adjutant = getAdjutant();
		int napoleonTeamGained = napoleon.getGainedCardCount() 
				+ ((null == adjutant) ? 0 : adjutant.getGainedCardCount());
		logger.debug(String.format("napoleon team gained %d", napoleonTeamGained));
		return napoleonTeamGained;
	}

	public Player getAdjutant() {
		return org.apache.commons.collections15.CollectionUtils.find(
				Arrays.asList(players), new org.apache.commons.collections15.Predicate<Player>() {

			@Override
			public boolean evaluate(Player player) {
				return player.isAdjutant();
			}
		});
	}

	private Player findAdjutant(final Card cardOfAdjutant) {
		return org.apache.commons.collections15.CollectionUtils.find(
				Arrays.asList(players), new org.apache.commons.collections15.Predicate<Player>() {

			@Override
			public boolean evaluate(Player player) {
				return player.cardsHaving().contains(cardOfAdjutant);
			}
		});
	}

	public String getAdjutantName() {
		Player adjutant = getAdjutant();
		return null == adjutant ? RESOURCE.getString(Messages.ADJUTANT_NAME_WHEN_NAPOLEON_ALONE) : adjutant.getName();
	}

}
