package napoleon.model.card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardSet {

	private List<Card> cards;

	public static CardSet New() {
		
		CardSet cardSet = new CardSet();
		cardSet.cards = GetFullCardSet();
		
		return cardSet;
	}

	private static List<Card> GetFullCardSet() {
		
		List<Card> result = new ArrayList<Card>();
		result.addAll(get13CardByMark(Suit.Spade));
		result.addAll(get13CardByMark(Suit.Heart));
		result.addAll(get13CardByMark(Suit.Dia));
		result.addAll(get13CardByMark(Suit.Club));
		result.add(Card.Jorker);
		return result;
	}
	
	private static List<Card> get13CardByMark(Suit marks) {
		return Arrays.asList(new Card[]{
					Card.New(marks, 1),
					Card.New(marks, 2),
					Card.New(marks, 3),
					Card.New(marks, 4),
					Card.New(marks, 5),
					Card.New(marks, 6),
					Card.New(marks, 7),
					Card.New(marks, 8),
					Card.New(marks, 9),
					Card.New(marks, 10),
					Card.New(marks, 11),
					Card.New(marks, 12),
					Card.New(marks, 13)
					});
	}

	public Integer count() {
		return cards.size();
	}

	public List<Card> AllCards() {
		return cards;
	}

}
