package model;

import java.util.ArrayList;
import java.util.List;

public class Table {

	List<Card> cards = new ArrayList<Card>();
	List<Card> noUseCards = new ArrayList<Card>();

	private Table() {}
	
	public static Table New() {
		return new Table();
	}

	public int cardCount() {
		return cards.size();
	}

}
