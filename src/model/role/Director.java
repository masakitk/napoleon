package model.role;

import java.util.Collection;
import java.util.HashMap;

import model.Declaration;
import model.GameContext;
import model.Status;
import model.Table;
import model.Turn;
import model.TurnStatus;
import model.player.Napoleon;
import model.player.Player;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import view.ConsoleView;

public class Director {

	protected static final int NUMBER_OF_MEMBERS = 4;
	protected Table table;
	protected Dealer dealer;
	protected Player[] players;
	protected Declaration fixedDeclaration;
	protected Player napoleon;
	protected boolean isNobodyDeclared;
	protected boolean extraCardChanged;
	private Turn[] turns;
	
	protected Director(){}

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
				napoleon = player;
				return;
			}
			currentDeclaration = askForDeclare(currentDeclaration, lastDeclarationsOfPlayer, player);
		}
		
		if(allPlayerPassed(lastDeclarationsOfPlayer)) {
			isNobodyDeclared = true;
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
		System.out.print(player);
		System.out.println(declaration);
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
		return napoleon.asNapoleon();
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public TurnStatus getCurrentTurnStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void beginTurn(int turnNo) {
		for (Player p : getPlayersForTurn(turnNo)){
			
		}
	}

	private Collection<Player> getPlayersForTurn(int turnNo) {
		Player first = turnNo == 1 ? players[0] : getTurnWinner(turnNo - 1);
		//TODO
		return null;
	}

	public Player getTurnWinner(int turnNo) {
		return turns[turnNo].getWinner();
	}

}
