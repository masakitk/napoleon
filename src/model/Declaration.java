package model;

public class Declaration {

	@Override
	public String toString() {
		return "Declaration [runForNapoleon=" + runForNapoleon + ", suit="
				+ suit + ", cardsToCollect=" + cardsToCollect + "]";
	}

	final static Declaration Pass = new Declaration(false);
	private boolean runForNapoleon;
	private final Suit suit;
	private final int cardsToCollect;
	
	public boolean isDeclared() {
		return runForNapoleon;
	}

	private Declaration(Suit suit, int cardsToCollect) {
		this.suit = suit;
		this.cardsToCollect = cardsToCollect;
		runForNapoleon = true;
	}

	public Declaration(boolean runForNapoleon) {
		this.runForNapoleon = runForNapoleon;
		suit = null;
		cardsToCollect = 0;
	}

	public static Declaration New(Suit suit, int cardsToCollect) {
		return new Declaration(suit, cardsToCollect);
	}

}
