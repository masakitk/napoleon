package napoleon.model.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import napoleon.model.card.Card;


public class Table {

	public static final int _PLAYERS_COUNT = 4;
    List<Card> cards = new ArrayList<Card>();
	public List<Card> getCards() {
		return cards;
	}

	List<Card> noUseCards = new ArrayList<Card>();

	public List<Card> getNoUseCards() {
		return noUseCards;
	}

	private Table() {}
	
	public static Table New() {
		return new Table();
	}

	public int cardCount() {
		return cards.size();
	}

    public void removeCards(Collection<Card> cardsToRemove) {
		cards.removeAll(cardsToRemove);
	}

}
