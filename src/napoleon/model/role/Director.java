package napoleon.model.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import napoleon.model.card.Card;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
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
	private Turn[] turns = TurnFactory.Get12Turns();
	private Integer currentTurnNo = 1;
	private Logger logger;
	private Card cardOfAdjutant;
	protected Viewer viewer;
	
	protected Director(){
		logger = LogManager.getLogger(Director.class.getName());
	}

	public static Director New(Table table, Player[] players, ConsoleViewer viewer) {
		Director instance = new Director();
		instance.table = table;
		instance.dealer = Dealer.New(GameContext.New(table, players));
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
				napoleon = Napoleon.New(player);
				players[Arrays.asList(players).indexOf(player)] = napoleon;
				logger.info(String.format("Åönapoleon fixed:%s, %s", napoleon, fixedDeclaration));
				viewer.showMessage(String.format("Åönapoleon fixed:%s, %s", napoleon.getName(), fixedDeclaration.toShow()));
				return;
			}
			currentDeclaration = askForDeclare(currentDeclaration, lastDeclarationsOfPlayer, player);
		}
		
		if(allPlayerPassed(lastDeclarationsOfPlayer)) {
			isNobodyDeclared = true;
			logger.info("Åöall players passed.");
			return;
		}
		defineNapoleon(currentDeclaration, lastDeclarationsOfPlayer);
	}

	private Declaration askForDeclare(Declaration currentDeclaration,
			HashMap<Player, Declaration> lastDeclarationsOfPlayer, Player player) {
		Declaration declaration = player.AskForDeclare(currentDeclaration, viewer);
		while(Declaration.Pass != declaration && !declaration.isStrongerDecralation(currentDeclaration)) {
			declaration = player.AskForDeclare(currentDeclaration, viewer);
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
				return ((Declaration)lastDeclaration).isDeclared();
			};
		});
	}

	public Declaration getDeclaration() {
		return fixedDeclaration;
	}

	public Napoleon getNapoleon() {
		return napoleon;
	}

	public void letNapoleonChangeExtraCards() {
		getNapoleon().changeExtraCards(fixedDeclaration, table);
		extraCardChanged = true;
	}

	public void askForAdjutant() {
		cardOfAdjutant = getNapoleon().tellTheAdjutant(fixedDeclaration);
		logger.info(String.format("adjutant card is %s", cardOfAdjutant));
		viewer.showMessage(String.format("adjutant is who having %s", cardOfAdjutant));
		Player adjutant = findAdjutant(cardOfAdjutant);
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
		viewer.showMessage(
				String.format("Åöturn[%d], winner[%s]: cards[%s]", 
						currentTurnNo, 
						getTurnWinnerName(currentTurnNo),
						turn.getCardsToShow(viewer)));
		
		showPlayersGainedCards();
		currentTurnNo++;
	}

	protected void showPlayersGainedCards() {
		for (Player p : players){
			System.out.println(String.format("player:%s / Gained:%s", p.getName(), p.cardsGained()));
		}
	}

	private Turn getTurn(int turnNo) {
		return turns[turnNo -1];
	}

	private Collection<Player> getPlayersForTurn(int turnNo) {
		ArrayList<Player> list = new ArrayList<Player>(); 
		int leadPlayerIndex = turnNo == 1 ? 0 : Arrays.asList(players).indexOf(getTurnWinner(turnNo - 1));
//		System.out.println(String.format("leadPlayerIndex:%d", leadPlayerIndex));
		for(int i = 0; i < Table._PLAYERS_COUNT; i++){
			Player p = players[(leadPlayerIndex + i) % Table._PLAYERS_COUNT];
			logger.info(p);
			list.add(p);
		}
		return list;
	}

	private Player getTurnWinner(int turnNo) {
		return getTurn(turnNo).getWinner();
	}

	public String getTurnWinnerName(int turnNo) {
		return getTurnWinner(turnNo).getName();
	}
	
	public Team JudgeWinnerTeam(){
		viewer.showMessage(String.format("napoleon gained %s", getNapoleon().cardsGained()));
		for (Player p : players) {
			viewer.showMessage(String.format("player %s gained %s", p.getName(), p.cardsGained()));
		}
		viewer.showMessage(String.format("table cards are %s", table.getNoUseCards()));
		
		final Team winnerTeam = getWinnerTeam();
		viewer.showMessage(String.format("adjutant is %s", getAdjutantName()));
		viewer.showMessage(String.format("winner is %s", winnerTeam));
		return winnerTeam;
	}

	protected Team getWinnerTeam() {
		if(fixedDeclaration.getCardsToCollect() <= getCardCountNapleonTeamGained()){
			return Team.NapoleonTeam;
		} else {
			return Team.AlliedForcesTeam;
		}
	}

	private int getCardCountNapleonTeamGained() {
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
		return null == adjutant ? "nobady: only napoleon;" : adjutant.getName();
	}

}
