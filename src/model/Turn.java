package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class Turn {
	private HashMap<Player, Card> cardHash = new LinkedHashMap();
	private int no;
	
	private Turn(int no) {
		super();
		this.no = no;
	}
	
	public int getNo() {
		return no;
	}

	public static Turn New(int no) {
		if(no < 1 || 12 < no) throw new IllegalArgumentException("ターン番号は1以上12以下である必要があります");
		
		return new Turn(no);
	}

	public boolean isLeadSuitDefined() {
		return 0 < cardHash.size();
	}

	public void addCard(Player player, Card card) {
		cardHash.put(player,  card);
	}

	public Suit getLeadSuit() {
		return isLeadSuitDefined() ? ((Card)cardHash.values().toArray()[0]).getSuit() : null;
	}

	public Player getWinner() {
		return whoOpened(getWinnerCard());
	}

	private Player whoOpened(Object winnerCard) {
		return null;
	}

	private Card getWinnerCard() {
		if(4 != cardHash.size())
			throw new IllegalStateException("全員カードを出していません");
		List<Card> list = new ArrayList<Card>(cardHash.values());
		Collections.sort(list, Card.CardNormalComparator);
		return list.get(0);
	}
	
}
