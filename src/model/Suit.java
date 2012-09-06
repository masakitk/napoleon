package model;

public enum Suit {
	Spade,
	Heart,
	Dia,
	Club;

	public boolean strongerThan(Suit suit) {
		switch (this) {
		case Spade:
			return Suit.Spade != suit;
		case Heart:
			return Suit.Spade != suit && Suit.Heart != suit; 
		case Dia:
			return Suit.Club == suit;
		case Club:
			return false;
		default:
			throw new IllegalStateException(String.format("–¢‘Î‰‚Ìƒ}[ƒN‚Å‚·:%s", this));
		}
	}
}
