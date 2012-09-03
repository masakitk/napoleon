package model;

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
					new Card(marks, 1),
					new Card(marks, 2),
					new Card(marks, 3),
					new Card(marks, 4),
					new Card(marks, 5),
					new Card(marks, 6),
					new Card(marks, 7),
					new Card(marks, 8),
					new Card(marks, 9),
					new Card(marks, 10),
					new Card(marks, 11),
					new Card(marks, 12),
					new Card(marks, 13)
					});
	}

	public Integer count() {
		return cards.size();
	}

	public List<Card> AllCards() {
		return cards;
	}

}
