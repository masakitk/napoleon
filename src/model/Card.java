package model;

public class Card {
	
	public static Card Jorker = Card.GetJorker();
	
	private final Suit mark;
	private final int number;
	private final boolean isJorker;
	
	public Card(Suit mark, int number) {
		this(mark, number, false);
	}

	public Card(Suit mark, int number, boolean isJorker) {
		super();
		this.mark = mark;
		this.number = number;
		this.isJorker = isJorker;
	}

	private static Card GetJorker() {
		Card card = new Card(null, 0, true);
		return card;
	}

	public Suit getMark() {
		return mark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isJorker ? 1231 : 1237);
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
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
		if (mark != other.mark)
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	public int getNumber() {
		return number;
	}
	
}
