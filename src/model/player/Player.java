package model.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Declaration;
import model.Turn;
import model.card.Card;
import model.card.Suit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class Player {

	private List<Card> cards = new ArrayList<Card>();
	private String name;
	private List<Card> _cardsGained = new ArrayList<Card>();
	
	public Player(String name) {
		this.name = name;
	}

	public static Player New(String name) {
		return new Player(name);
	}
	
	public int cardCount() {
		return cards.size();
	}

	public void takeCard(Card card) {
		cards.add(card);
	}

	public Card openCard(Turn turn) {
		Collection<Card> cardsToOpen = turn.isLeadSuitDefined()
				? findSameMark(cards, turn.getLeadSuit())
						: (Collection<Card>)cards;

		if(cardsToOpen.size() == 0)
			cardsToOpen.addAll(cards);
				
		Card toOpen = (Card)CollectionUtils.get(cardsToOpen, 0);
		cards.remove(toOpen);
		return toOpen;
	}

	@SuppressWarnings("unchecked")
	private Collection<Card> findSameMark(Collection<Card> cards, final Suit mark) {
		return CollectionUtils.select(cards, new Predicate(){

			@Override
			public boolean evaluate(Object card) {
				return ((Card)card).getSuit() == mark;
			}
			
		});
	}

	public Declaration AskForDeclare(Declaration currentDeclaration) {
		return null == currentDeclaration ? Declaration.New(Suit.Club, 13) : Declaration.Pass;
	}

	public Declaration getLastDeclaration() {
		// TODO Auto-generated method stub
		return null;
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

	public List<Card> cardsHaving() {
		return cards;
	}

}
