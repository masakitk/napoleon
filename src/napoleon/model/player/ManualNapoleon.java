package napoleon.model.player;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

public class ManualNapoleon extends Napoleon {

	protected ManualNapoleon(ManualPlayer player) {
		super(player);
	}

	public static ManualNapoleon New(ManualPlayer player) {
		return new ManualNapoleon(player);
	}
	
	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		return ManualPlayerUtil.chooseCardToOpenByManual(turn, viewer, declaration, cards, true);
	}
	
	@Override
	public void changeExtraCards(Declaration fixedDeclaration, Table table, Viewer viewer) {
		viewer.printPlayerHavingCards(this);
		Collection<Card> unusedCards = viewer.inputCardsToChange(fixedDeclaration, table, cards);
			List<Card> extraCards = table.getCards();
			cards.addAll(extraCards);
			cards.removeAll(unusedCards);
			table.removeCards(extraCards);
			table.getNoUseCards().addAll(unusedCards);
	}

	private boolean canConvertAllToCard(String[] cardsEntered) {
		return !CollectionUtils.exists(Arrays.asList(cardsEntered), new Predicate<String>() {

			@Override
			public boolean evaluate(String inputString) {
				return !ManualPlayerUtil.canConvertToCard(inputString);
			}
		});
	}

	private Collection<Card> getWrongCards(Collection<Card> unuseCards, final Collection<Card> extraCards) {
		return CollectionUtils.select(unuseCards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
				return !cards.contains(card) && !extraCards.contains(card);
			}
		});
	}

	private boolean invalidCardCount(Collection<Card> unuseCards, int size) {
		return size != unuseCards.size();
	}

	public void takeCards(List<Card> cardsToTake) {
		cards.addAll(cardsToTake);
	}

	@Override
	public Card tellTheAdjutant(Declaration fixedDeclaration, Viewer viewer) {
		return viewer.inputCardToAdjutant();
	}	
}
