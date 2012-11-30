package napoleon.model.player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;

import napoleon.model.card.Card;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

public class ManualNapoleon extends Napoleon {

	protected ManualNapoleon(ManualPlayer player) {
		super(player);
	}

	public static ManualNapoleon New(ManualPlayer player) {
		return new ManualNapoleon(player);
	}
	
	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		return ManualPlayerUtil.chooseCardToOpenByManual(turn, viewer, declaration, cards);
	}
	
	@Override
	public void changeExtraCards(Declaration fixedDeclaration, Table table, Viewer viewer) {
		String[] cardsEntered = 
				ManualPlayerUtil.getInputString("input cards to unuse, as [C3,C4,C5...]", viewer).split(",");
		
		try{
		Collection<Card> unuseCards = CollectionUtils.collect(
				Arrays.asList(cardsEntered), 
				new Transformer<String, Card>(){
					@Override
					public Card transform(final String s) {
						return ManualPlayerUtil.convertToCard(s);
					}
				});
			if(invalidCardCount(unuseCards, table))
				throw new IllegalStateException();
			
			cards.addAll(table.getCards());
			cards.removeAll(unuseCards);
			table.getNoUseCards().addAll(unuseCards);
		} catch (Exception e) {
			//TODO
			throw new RuntimeException(e);
		}
	}

	private boolean invalidCardCount(Collection<Card> unuseCards, Table table) {
		// TODO Auto-generated method stub
		return false;
	}

	public void takeCards(List<Card> cardsToTake) {
		cards.addAll(cardsToTake);
	}

}
