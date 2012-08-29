package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class Player {

	private List<Card> cards = new ArrayList<Card>();
	
	public int cardCount() {
		return cards.size();
	}

	public Manifesto replyForDeclare() {
		// TODO Auto-generated method stub
		return null;
	}

	public void takeCard(Card card) {
		cards.add(card);
	}

	public Card openCard(Turn turn) {
		Collection<Card> cardsToOpen = turn.isLeadMarkDefined()
				? findSameMark(cards, turn.getLeadMark())
						: (Collection<Card>)cards;

		if(cardsToOpen.size() == 0)
			cardsToOpen.addAll(cards);
				
		Card toOpen = (Card)CollectionUtils.get(cardsToOpen, 0);
		cards.remove(toOpen);
		return toOpen;
	}

	@SuppressWarnings("unchecked")
	private Collection<Card> findSameMark(Collection<Card> cards, final Marks mark) {
		return CollectionUtils.select(cards, new Predicate(){

			@Override
			public boolean evaluate(Object card) {
				return ((Card)card).getMark() == mark;
			}
			
		});
	}

}
