package model.rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.card.Card;
import model.card.Suit;
import model.player.Player;

public class Turn {
	private HashMap<Player, Card> cardHash = new LinkedHashMap<Player, Card>();
	private int no;
	private boolean ignoreSpecial;
	
	private Turn(int no) {
		super();
		if(no == 1 || no == 12) {
			ignoreSpecial = true;
		}
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

	private Player whoOpened(Card winnerCard) {
		for(Map.Entry<Player, Card> entry : cardHash.entrySet()) {
			if (entry.getValue() == winnerCard)
				return entry.getKey();
		}
		return null;
	}

	public Card getWinnerCard() {
		if(Table._PLAYERS_COUNT != cardHash.size())
			throw new IllegalStateException("全員カードを出していません");
		List<Card> list = new ArrayList<Card>(cardHash.values());
		if(ignoreSpecial) {
			Collections.sort(list, Card.GetCardIgnoreSpecialComparator(getLeadSuit()));
		}else {
			Collections.sort(list, Card.CardNormalComparator);
		}
		System.out.println(list.get(getMaxCardIndex()));
		return list.get(getMaxCardIndex());
	}

	private int getMaxCardIndex() {
		return cardHash.size() - 1;
	}

	public TurnStatus getStatus() {
		return cardHash.isEmpty() ? TurnStatus.HasNotYetBegan
				: cardHash.size() < Table._PLAYERS_COUNT ? TurnStatus.Processing
				: TurnStatus.Finished;
	}
	
}
