package napoleon.model.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Status;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.model.rule.TurnFactory;
import napoleon.model.rule.TurnStatus;
import napoleon.view.ConsoleView;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Director {

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
	
	protected Director(){
		logger = LogManager.getLogger(Director.class.getName());
	}

	public static Director New(Table table, Player[] players) {
		Director instance = new Director();
		instance.table = table;
		instance.dealer = Dealer.New(GameContext.New(table, players));
		instance.players = players;
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
				System.out.println(String.format("napoleon fixed:%s, %s", napoleon, fixedDeclaration));
				return;
			}
			currentDeclaration = askForDeclare(currentDeclaration, lastDeclarationsOfPlayer, player);
		}
		
		if(allPlayerPassed(lastDeclarationsOfPlayer)) {
			isNobodyDeclared = true;
			System.out.println("all players passed.");
			return;
		}
		defineNapoleon(currentDeclaration, lastDeclarationsOfPlayer);
	}

	private Declaration askForDeclare(Declaration currentDeclaration,
			HashMap<Player, Declaration> lastDeclarationsOfPlayer, Player player) {
		Declaration declaration = player.AskForDeclare(currentDeclaration);
		while(Declaration.Pass != declaration && !declaration.isStrongerDecralation(currentDeclaration)) {
			declaration = player.AskForDeclare(currentDeclaration);
		}
		
		lastDeclarationsOfPlayer.put(player, declaration);
		System.out.println(String.format("%s\t%s", player, declaration));
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
		return CollectionUtils.exists(lastDeclarationsOfPlayer.values(), new Predicate() {
			
			@Override
			public boolean evaluate(Object lastDeclaration) {
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
		getNapoleon().changeExtraCards();
		extraCardChanged = true;
	}

	public void askForAdjutant() {
		getNapoleon().tellTheAdjutant();
	}

	public void showSituationToConsole() {
		ConsoleView.GetInstance().Show(players);
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
			turn.addCard(p, p.openCard(turn));
			logger.debug(p);
		}
		
		turn.winnerGainCards();
		currentTurnNo++;
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
//			System.out.println(p);
			list.add(p);
		}
		return list;
	}

	public Player getTurnWinner(int turnNo) {
		return getTurn(turnNo).getWinner();
	}

}
