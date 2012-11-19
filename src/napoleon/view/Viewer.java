package napoleon.view;

import java.util.Collection;
import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.player.Player;
import napoleon.model.rule.Turn;

public interface Viewer {
	void show(Player[] players);
	public List<Card> sortCardsToView(List<Card> cards);
	void showMessage(String message);
	Collection<String> formatTurnCards(Turn turn);
}
