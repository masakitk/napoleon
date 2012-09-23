package model;

import model.card.Suit;

public class Declaration {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cardsToCollect;
		result = prime * result + (runForNapoleon ? 1231 : 1237);
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
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
		Declaration other = (Declaration) obj;
		if (cardsToCollect != other.cardsToCollect)
			return false;
		if (runForNapoleon != other.runForNapoleon)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Declaration [runForNapoleon=" + runForNapoleon + ", suit="
				+ suit + ", cardsToCollect=" + cardsToCollect + "]";
	}

	public final static Declaration Pass = new Declaration(false);
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

	public boolean strongerThan(Declaration currentDeclaration) {
		return currentDeclaration.cardsToCollect < cardsToCollect ||
				(	suit.strongerThan(currentDeclaration.suit) && 
						currentDeclaration.cardsToCollect <= cardsToCollect);
	}

	public boolean isStrongerDecralation(Declaration currentDeclaration) {
		if(Declaration.Pass == this) return false;
		if(null == currentDeclaration) return true;
		
		return strongerThan(currentDeclaration);
	}
}
