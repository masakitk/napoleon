package model;

import java.util.List;

import model.card.Card;
import model.player.Player;

public class GameContext {

	private final Table table;
	private final Player[] players;
	private Player napoleon;

	public GameContext(Table table, Player[] players) {
		this.table = table;
		this.players = players;
	}

	public static GameContext New(Table table, Player[] players) {
		return new GameContext(table, players);
	}

	public Player[] getPlayers() {
		return players;
	}

	public Table getTable() {
		return table;
	}

	public Boolean isNapoleonDetermined() {
		return  null != napoleon;
	}

	public Player getPlayer(int i) {
		return players[i];
	}

	public void setNapoleon(Player player) {
		napoleon = player;
	}

	public Player getNapoleon() {
		return napoleon;
	}

	public void addClosedCardsOnTable(List<Card> cards) {
		getTable().cards.addAll(cards);
	}

}
