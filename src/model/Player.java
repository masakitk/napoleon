package model;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {

	public List<Card> cards = new ArrayList<Card>();

	public int cardCount() {
		return cards.size();
	}

	@Override
	public Manifesto replyForDeclare() {
		// TODO Auto-generated method stub
		return null;
	}

}
