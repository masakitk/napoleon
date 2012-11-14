package napoleon.model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import napoleon.Runner;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Player {

	protected List<Card> cards = new ArrayList<Card>();
	private String name;
	protected List<Card> _cardsGained = new ArrayList<Card>();
	protected boolean _isAdjutant;
	protected Logger _logger;
	
	public Player(String name) {
		_logger = LogManager.getLogger(Runner.class);
		this.name = name;
	}

	public int cardCount() {
		return cards.size();
	}

	public void takeCard(Card card) {
		cards.add(card);
	}

	public Card openCard(Turn turn, Viewer viewer, Declaration declaration) {
		Card toOpen = chooseCardToOpen(turn, viewer, declaration);

		System.out.println(String.format("player:%s / Open:%s", this, toOpen));
		cards.remove(toOpen);
		return toOpen;
	}

	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		List<Card> cardsToOpen = new ArrayList<Card>();

		if(turn.isJorkerOpenedFirst()){
			cardsToOpen.add(findTrumpOrMaxNumber(turn.getTrump()));
		}
		else if(turn.isRequireJorkerOpenedFirst() && findJorker() != null){
			cardsToOpen.add(findJorker());
		}
		
		if(cardsToOpen.isEmpty()) {
			cardsToOpen.addAll(
					turn.isLeadSuitDefined() ? findSameMark(cards, turn.getLeadSuit()) : cards);
		}
		
		if(cardsToOpen.isEmpty()) {
			cardsToOpen.addAll(cards);
		}		
		
		Card toOpen = cardsToOpen.get(0);
		return toOpen;
	}

	private Card findJorker() {
		return CollectionUtils.find(cards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
				return card.equals(Card.Jorker);
			}
		});
	}

	private Card findTrumpOrMaxNumber(Suit trump) {
		Collection<Card> found = findSameMark(cards, trump);
		if (!found.isEmpty()) return (Card)found.toArray()[0];
		
		return findStrongNumber(cards);
	}

	private Card findStrongNumber(List<Card> cards) {
		return Collections.max(cards, new Comparator<Card>() {

			@Override
			public int compare(Card o1, Card o2) {
				return o1.strongerThanAsNumber(o2) ? 1 : -1;
			}
		});
	}

	protected Collection<Card> findSameMark(Collection<Card> cards, final Suit suit) {
		return CollectionUtils.select(cards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
					return card.getSuit() == suit;
			}
		} );
	}

	public Declaration AskForDeclare(Declaration currentDeclaration, Viewer viewer) {
		return null == currentDeclaration ? Declaration.New(Suit.Club, 13) : Declaration.Pass;
	}

	public String getName() {
		return name;
	}

	public Napoleon asNapoleon() {
		return (Napoleon) this;
	}

	public List<Card> cardsGained() {
		return _cardsGained;
	}

	public int getGainedCardCount() {
		return _cardsGained.size();
	}

	public List<Card> cardsHaving() {
		return cards;
	}

	public void takeCards(Collection<? extends Card> cards) {
		_cardsGained.addAll(cards);
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", \r\t cards=" + cards + ", \r\t _cardsGained=" + _cardsGained + "]";
	}

	public static Player New(String name) {
		return new Player(name);
	}

	public boolean isAdjutant() {
		return _isAdjutant;
	}

	public void setIsAdjutant(boolean b) {
		_logger.info(String.format("adjutant is %s", name));
		_isAdjutant = b;
	}
}
