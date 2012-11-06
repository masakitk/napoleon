package napoleon.model.card;

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
			throw new IllegalStateException(String.format("未対応のマークです:%s", this));
		}
	}
	
	public String toShortString(){
		return shortStringHash(this);
	}

	private String shortStringHash(Suit suit) {
		return suit == Suit.Spade ? "♠"
				: suit == Suit.Heart ? "♥"
				: suit == Suit.Dia ? "◆"
				: suit == Suit.Club ? "♣"
				: "";
	}
}
