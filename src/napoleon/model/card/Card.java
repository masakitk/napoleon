package napoleon.model.card;

import java.util.Collection;
import java.util.Comparator;

import napoleon.model.rule.CompareContexts;
import napoleon.model.rule.GameContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Card {
	

	
	private final Suit suit;
	private final int number;
	private final boolean isJorker;
	public static Comparator<Card> CardNormalComparator;
	public static Comparator<Card> getCardNormalComparator() {
		return CardNormalComparator;
	}

	public static Comparator<Card> CardIgnoreSpecialComparator;

	public static final Card Yoromeki = Card.New(Suit.Heart, 12);
	public static final Card Mighty = Card.New(Suit.Spade, 1);
	public static final Card Jorker = Card.GetJorker();
	public static final Card RequireJorker = Card.New(Suit.Club, 3);
	
	static {
		CardNormalComparator = new CardNormalStrengthComparator();
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
		result = prime * result + getNumber();
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
		if (getNumber() != other.getNumber())
			return false;
		return true;
	}

	public static Card New(Suit suit, int no) {
		return new Card(suit, no);
	}
	
	@Override
	public String toString() {
		return isJorker ? "[Jorker]" : String.format("[%s:%d]", suit, getNumber());
	}

	public boolean strongerThanAsSuitAndNumber(Card card2) {
		return card2.isJorker ||
				(	strongerThanAsNumber(card2) ||
					(getStrengthOfNumber() == card2.getStrengthOfNumber()
						&& suit.strongerThan(card2.getSuit())));
	}

	public boolean strongerThanAsNumber(Card card2) {
		return card2.getStrengthOfNumber() < getStrengthOfNumber();
	}

	private int getStrengthOfNumber() {
		return isJorker ? 0 : getNumber() == 1 ? 14 : getNumber();
	}

	public boolean isUpperOrderByMarkAndStrength(Card c2) {
		return isJorker ? false
				: c2.isJorker ? true
				: (suit.strongerThan(c2.getSuit())
					|| (suit == c2.getSuit()
						&& strongerThanAsNumber(c2)));
	}
	
	private static class CardIgnoreSpecialStrengthComparator implements Comparator<Card>{
		static Suit leadSuit;
		public static void setLeadSuit(Suit suit) {
			leadSuit = suit;
		}
		@Override
		public int compare(Card left, Card right) {
			return left == Card.Jorker ? -1 
					: right == Card.Jorker ? 1
					: left.suit == leadSuit && right.suit != leadSuit ? 1
					: left.suit != leadSuit && right.suit == leadSuit ? -1
					: left.suit == leadSuit && right.suit == leadSuit ?
							(left.strongerThanAsNumber(right) ? 1 : -1)
					: 0;
			}
	}

	public static Comparator<? super Card> GetCardIgnoreSpecialComparator(Suit leadSuit) {
		CardIgnoreSpecialStrengthComparator.setLeadSuit(leadSuit);
		return CardIgnoreSpecialComparator;
	}

	public int getNumber() {
		return number;
	}

	public static Comparator<? super Card> getCardNormalComparator(Suit trump, Suit leadSuit, Collection<Card> cards) {
		CardNormalStrengthComparator.setTrump(trump);
		CardNormalStrengthComparator.setLeadSuit(leadSuit);
		CardNormalStrengthComparator.setCards(cards);
		return CardNormalComparator;
	}
	
	private static class CardNormalStrengthComparator implements Comparator<Card>{
		static Suit leadSuit;
		private static Collection<Card> cards;
		private static Card rightBower;
		private static Card leftBower;
		private static Suit trump;
		public static void setLeadSuit(Suit suit) {
			leadSuit = suit;
		}
		
		public static void setTrump(Suit trump) {
			CardNormalStrengthComparator.trump = trump;
			rightBower = GameContext.getRightBower(trump);
			leftBower = GameContext.getLeftBower(trump);
		}
		
		public static void setCards(Collection<Card> cards) {
			CardNormalStrengthComparator.cards = cards;
		}
		
		@Override
		public int compare(Card left, Card right) {
			return contains(Card.Mighty) && !contains(Card.Yoromeki) ? getOrderOfTargetCardWin(Card.Mighty, left, right)
					: contains(Card.Mighty) && contains(Card.Yoromeki) ? getOrderOfTargetCardWin(Card.Yoromeki, left, right)
					: contains(rightBower) ? getOrderOfTargetCardWin(rightBower, left, right)
					: isJorkerFirst() ? (left == Card.Jorker ? 1 : -1)
					: contains(leftBower) ? getOrderOfTargetCardWin(leftBower, left, right)
					: isAllSameSuit() ? getOrderOfTargetCardWin(getSame2(leadSuit), left, right)
					: left.suit == trump && right.suit != trump ? 1
					: left.suit != trump && right.suit == trump ? -1
					: left.suit == trump && right.suit == trump && left.strongerThanAsNumber(right) ? 1
					: left.suit == leadSuit && right.suit != leadSuit ? 1
					: left.suit != leadSuit && right.suit == leadSuit ? -1
					:left.suit == leadSuit && right.suit == leadSuit && left.strongerThanAsNumber(right) ? 1 : -1;
			}
		private boolean isJorkerFirst() {
			return CollectionUtils.get(cards, 0) == Card.Jorker;
		}

		private static Card getSame2(Suit leadSuit) {
			return Card.New(leadSuit, 2);
		}

		private boolean isAllSameSuit() {
			return CollectionUtils.select(cards, new Predicate() {
				
				@Override
				public boolean evaluate(Object o) {
					return !((Card)o).getSuit().equals(getLeadSuit());
				}
			}).isEmpty();
		}

		protected Suit getLeadSuit() {
			return leadSuit;
		}

		private int getOrderOfTargetCardWin(Card cardToWin, Card left, Card right) {
			return left.equals(cardToWin) ? 1
					: right.equals(cardToWin) ? -1
					: CardIgnoreSpecialComparator.compare(left, right);
		}
		private boolean contains(final Card card) {
			return CollectionUtils.exists(cards, new Predicate() {
				
				@Override
				public boolean evaluate(Object o) {
					return card.equals((Card)o);
				}
			});
		}
	}
}
