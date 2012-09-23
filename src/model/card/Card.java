package model.card;

import java.util.Comparator;

import model.CompareContexts;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Card {
	
	public static Card Jorker = Card.GetJorker();
	
	private final Suit suit;
	private final int number;
	private final boolean isJorker;
	public static Comparator<Card> CardNormalComparator;
	public static Comparator<Card> CardIgnoreSpecialComparator;
	static {
		CardNormalComparator = new CardStrengthComparator();
		CardIgnoreSpecialComparator = new CardIgnoreSpecialStrengthComparator();
	}
	
	
	private Card(Suit mark, int number) {
		this(mark, number, false);
	}

	private Card(Suit suit, int number, boolean isJorker) {
		super();
		this.suit = suit;
		this.number = number;
		this.isJorker = isJorker;
	}

	private static Card GetJorker() {
		Card card = new Card(null, 0, true);
		return card;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isJorker ? 1231 : 1237);
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (isJorker != other.isJorker)
			return false;
		if (suit != other.suit)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	public static Card New(Suit suit, int no) {
		return new Card(suit, no);
	}
	
	@Override
	public String toString() {
		return isJorker ? "[Jorker]" : String.format("[%s:%d]", suit, number);
	}

	public boolean strongerThan(Card card2, CompareContexts contexts) {
		if(CompareContexts.IgnoreSpecial != contexts) {
			throw new NotImplementedException();
		}
		return card2.isJorker ||
				(	strongerThanAsNumber(card2) ||
					(getStrengthOfNumber() == card2.getStrengthOfNumber()
						&& suit.strongerThan(card2.getSuit())));
	}

	private boolean strongerThanAsNumber(Card card2) {
		return card2.getStrengthOfNumber() < getStrengthOfNumber();
	}

	private int getStrengthOfNumber() {
		return isJorker ? 0 : number == 1 ? 14 : number;
	}

	public boolean isUpperOrderByMarkAndStrength(Card c2) {
		return isJorker ? false
				: c2.isJorker ? true
				: (suit.strongerThan(c2.getSuit())
					|| (suit == c2.getSuit()
						&& strongerThanAsNumber(c2)));
	}
	
	private static class CardStrengthComparator implements Comparator<Card>{
		@Override
		public int compare(Card left, Card right) {
			return left.strongerThan(right, CompareContexts.Normal) ? 1 : -1;
			}
	}
	
	private static class CardIgnoreSpecialStrengthComparator implements Comparator<Card>{
		@Override
		public int compare(Card left, Card right) {
			return left.strongerThan(right, CompareContexts.IgnoreSpecial) ? 1 : -1;
			}
	}
}
