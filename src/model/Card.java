package model;

public class Card {
	
	public static Card Jorker = Card.GetJorker();
	
	private final Suit suit;
	private final int number;
	private final boolean isJorker;
	
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

	public int getNumber() {
		return number;
	}

	public static Card New(Suit suit, int no) {
		return new Card(suit, no);
	}
	
}
