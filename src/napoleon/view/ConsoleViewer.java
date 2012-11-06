package napoleon.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;

import napoleon.model.card.Card;
import napoleon.model.player.Player;

public class ConsoleViewer implements Viewer {
	private static final ConsoleViewer CONSOLE_VIEWER = new ConsoleViewer();

	public static ConsoleViewer GetInstance() {
		return CONSOLE_VIEWER;
	}

	public void Show(Player[] players) {

		CollectionUtils.forAllDo(Arrays.asList(players), new Closure<Player>(){

			@Override
			public void execute(Player player) {
				printPlayerCards(player);
			}
		});
	}

	protected void printPlayerCards(Player player) {
		System.out.print(player.getName());
		System.out.println("/ gained:" + sortCardsToView(player.cardsGained()));
		System.out.println("/ having:" + sortCardsToView(player.cardsHaving()));
	}

	private List<Card> sortCardsToView(List<Card> cards) {
		List<Card> sorted = new ArrayList<Card>(cards);
		Collections.sort(sorted, new Comparator<Card>(){
			@Override
			public int compare(Card c1, Card c2) {
				return c1.isUpperOrderByMarkAndStrength(c2) ? 1 : -1;
			}
		});
		return sorted;
	}
}
