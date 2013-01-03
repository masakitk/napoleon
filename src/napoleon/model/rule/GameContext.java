package napoleon.model.rule;

import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;


public class GameContext {

	private final Table table;
	private final Player[] players;
    private Card rightBower;
	private Card leftBower;

    private GameContext(Table table, Player[] players) {
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

    public void setNapoleon(Declaration declaration) {
        setSpecialCards(declaration);
	}

	private void setSpecialCards(Declaration declaration) {
		rightBower = getRightBower(declaration.getSuit());
		leftBower = getLeftBower(declaration.getSuit());
	}

	public static Card getLeftBower(Suit leadSuit) {
		return Card.New(getSameColorAnotherSuit(leadSuit), 11);
	}

	public static Card getRightBower(Suit trump) {
		return Card.New(trump, 11);
	}

	public static Suit getSameColorAnotherSuit(Suit suit) {
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
			throw new IllegalArgumentException(String.format("%s:未対応のスートです", suit));
		}
	}

    public void addExtraCardsOnTable(List<Card> cards) {
		getTable().cards.addAll(cards);
	}

	public Card getRightBower() {
		return rightBower;
	}

	public Card getLeftBower() {
		return leftBower;
	}


}
