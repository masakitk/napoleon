package model.rule;

import java.util.List;

import model.card.Card;
import model.card.Suit;
import model.player.Player;

public class GameContext {

	private final Table table;
	private final Player[] players;
	private Player napoleon;
	private Declaration declaration;
	private Card rightBower;
	private Card leftBower;

	public Declaration getDeclaration() {
		return declaration;
	}

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

	public void setNapoleon(Player player, Declaration declaration) {
		napoleon = player;
		this.declaration = declaration;
		setSpecialCards(declaration);
	}

	private void setSpecialCards(Declaration declaration) {
		rightBower = Card.New(declaration.getSuit(), 11);
		leftBower = Card.New(getSameColorSuit(declaration.getSuit()), 11);
	}

	private Suit getSameColorSuit(Suit suit) {
		switch (suit) {
		case Spade:
			return Suit.Club;
		case Heart:
			return Suit.Dia;
		case Dia:
			return Suit.Heart;
		case Club:
			return Suit.Spade;
		default:
			throw new IllegalArgumentException(String.format("%s:‚Í–¢‘Î‰ž‚Å‚·", suit));
		}
	}

	public Player getNapoleon() {
		return napoleon;
	}

	public void addClosedCardsOnTable(List<Card> cards) {
		getTable().cards.addAll(cards);
	}

	public Card getRightBower() {
		return rightBower;
	}

	public Card getLeftBower() {
		return leftBower;
	}

}
