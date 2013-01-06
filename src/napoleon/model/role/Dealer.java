package napoleon.model.role;

import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.card.CardSet;
import napoleon.model.player.Player;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Table;


public class Dealer {

	private static final int Rounds = 12;
	private static final int RestOfCardsAfterServe = 5;
	private List<Card> cards;
    private final Player[] players;
    private final Table table;

    public Dealer(Table table, Player[] players) {
        this.players = players;
        this.table = table;
        cards = CardSet.New().AllCards();
    }

    public static Dealer New(Table table, Player[] players) {
        return new Dealer(table, players);
    }
	public void Serve() {
		for (int i = 0; i < Rounds; i++) {
			players[0].takeCard(getRandomCard());
			players[1].takeCard(getRandomCard());
			players[2].takeCard(getRandomCard());
			players[3].takeCard(getRandomCard());
		}
        table.getCards().addAll(cards);
}

	private Card getRandomCard() {
		Card card = cards.get((int)(Math.random() * cards.size()));
		cards.remove(card);
		return card;
	}

	public Integer cardCount() {
		return cards.size();
	}

    public boolean hasServed() {
		return cards.size() == RestOfCardsAfterServe;
	}
}
