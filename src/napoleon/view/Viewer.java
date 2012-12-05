package napoleon.view;

import java.util.Collection;
import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;

public interface Viewer {
	void show(Player[] players);
	public List<Card> sortCardsToView(List<Card> cards);
	void showMessage(String message);
	Collection<String> formatTurnCards(Turn turn);
	void showGainedCardsForEachPlayer(Napoleon napoleon, Player[] players);
	void showExchangedCards(Table table);
	void showTurnResult(Integer currentTurnNo, String turnWinnerName,
			Collection<String> cardsToShow);
	void showPlayersGainedCards(Player[] players);
	void showExtraCards(List<Card> extraCards);
	Card inputCard();
	Declaration askForDeclare(Declaration currentDeclaration, List<Card> cards);
	Collection<Card> inputCardsToChange(Declaration fixedDeclaration, Table table,
			List<Card> cards);
	Card inputCardToAdjutant();
	void printPlayerCards(Player player);
}
