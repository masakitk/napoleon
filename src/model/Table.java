package model;

import java.util.ArrayList;
import java.util.List;

import model.card.Card;

public class Table {

	private Status state;
	List<Card> cards = new ArrayList<Card>();
	public List<Card> getCards() {
		return cards;
	}

	List<Card> noUseCards = new ArrayList<Card>();

	private Table() {}
	
	public static Table New() {
		return new Table();
	}

	public int cardCount() {
		return cards.size();
	}

	public Status getState() {
		return state;
	}

	public void setState(Status napoleondefined) {
		state = napoleondefined;
	}

}
