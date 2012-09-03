package model;

import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.RowSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class Director {

	private static final int NUMBER_OF_MEMBERS = 4;
	private Table table;
	private Dealer dealer;
	private Player[] players;
	private Declaration fixedDeclaration;
	private Player napoleon;
	private boolean isNobodyDeclared;

	public static Director New(Table table, Dealer dealer, Player[] players) {
		Director instance = new Director();
		instance.table = table;
		instance.dealer = dealer;
		instance.players = players;
		return instance;
	}

	public Status getGameState() {
		return !dealer.hasServed() ? Status.Initial 
				: isNobodyDeclared ? Status.GameEnded 
				: null != napoleon ? Status.NapoleonDefined
				: Status.CardServed;
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

	public Player getNapoleon() {
		return napoleon;
	}

}
