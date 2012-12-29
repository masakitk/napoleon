package napoleon.model.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import napoleon.Runner;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;
import napoleon.view.Viewer;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Turn {
	private HashMap<Player, Card> cardHash = new LinkedHashMap<Player, Card>();
	private int no;
	private boolean ignoreSpecial;
	public final Suit trump;
	private Logger _logger = LogManager.getLogger(Runner.class);
	
	private Turn(int no, Suit trump) {
		super();
		this.trump = trump;
		if(no == 1 || no == 12) {
			ignoreSpecial = true;
		}
		this.no = no;
	}
	
	public int getNo() {
		return no;
	}

	public static Turn New(int no, Suit trump) {
		if(no < 1 || 12 < no) throw new IllegalArgumentException("ターン番号は1以上12以下である必要があります");
		
		return new Turn(no, trump);
	}

	public boolean isLeadSuitDefined() {
		return 0 < cardHash.size();
	}

	public void addCard(Player player, Card card) {
		cardHash.put(player,  card);
	}

	public Suit getLeadSuit() {
		return !isLeadSuitDefined() ? null
				: getFirstCard() == Card.Joker ? getLeadSuitWhenJokerFirst()
				: getFirstCard().getSuit();
	}

	private Suit getLeadSuitWhenJokerFirst() {
		return isIgnoreSpecial() ? (
				2 <= cardHash.size() ? getCardAt(1).getSuit() : null)
		: trump;
	}

	private Card getFirstCard() {
		return getCardAt(0);
	}

	private Card getCardAt(int index) {
		return (Card) CollectionUtils.get(cardHash.values(), index);
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
		if(isIgnoreSpecial()) {
			Collections.sort(list, Card.GetCardIgnoreSpecialComparator(getLeadSuit()));
		}else {
			Collections.sort(list, Card.getCardNormalComparator(getTrump(), getLeadSuit(), cardHash.values()));
		}
//		System.out.println(list.get(getMaxCardIndex()));
		return list.get(getMaxCardIndex());
	}

	public Suit getTrump() {
		return trump;
	}

	private int getMaxCardIndex() {
		return cardHash.size() - 1;
	}

	public TurnStatus getStatus() {
		return cardHash.isEmpty() ? TurnStatus.HasNotYetBegan
				: cardHash.size() < Table._PLAYERS_COUNT ? TurnStatus.Processing
				: TurnStatus.Finished;
	}

	public void winnerGainCards() {
		getWinner().gainCards(getPictureCards());
	}

	private Collection<? extends Card> getPictureCards() {
		return CollectionUtils.select(cardHash.values(), new Predicate<Card>() {
			
			@Override
			public boolean evaluate(Card card) {
				return isPictureCard(card);
			}
		});
	}

	protected boolean isPictureCard(Card card) {
		return card == null ? false
				: card.getNumber() == 1 ? true
				: 10 <= card.getNumber() ? true
				: false;
	}

	public boolean isJokerOpenedFirst() {
		return isOpenedFirst(Card.Joker);
	}

	public boolean isRequireJokerOpenedFirst() {
		return isOpenedFirst(Card.RequireJoker);
	}

	private boolean isOpenedFirst(Card cardToAssert) {
		return !isIgnoreSpecial() && 0 < cardHash.size() && getFirstCard().equals(cardToAssert);
	}

	protected boolean isIgnoreSpecial() {
		return ignoreSpecial;
	}

	public Collection<Card> getCards() {
		return cardHash.values();
	}

	public HashMap<Player, Card> getCardHash() {
		return cardHash;
	}

	public Collection<String> getCardsToShow(Viewer viewer) {
		return viewer.formatTurnCards(this);
	}
}
