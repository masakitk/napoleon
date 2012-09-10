package model;

import java.util.List;

public class Turn {
	private List<Card> cards;
	private int no;
	
	private Turn(List<Card> cards, int no) {
		super();
		this.cards = cards;
		this.no = no;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int getNo() {
		return no;
	}

	public static Turn New(List<Card> list, int no) {
		if(no < 1 || 12 < no) throw new IllegalArgumentException("ターン番号は1以上12以下である必要があります");
		
		return new Turn(list, no);
	}

	public boolean isLeadSuitDefined() {
		return 0 < cards.size();
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public Suit getLeadSuit() {
		return isLeadSuitDefined() ? cards.get(0).getSuit() : null;
	}
	
}
