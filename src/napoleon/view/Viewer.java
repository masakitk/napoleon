package napoleon.view;

import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.player.Player;

public interface Viewer {
	void show(Player[] players);
	public List<Card> sortCardsToView(List<Card> cards);
	void showMessage(String message);
}
