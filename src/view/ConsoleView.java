package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

import model.CompareContexts;
import model.Table;
import model.card.Card;
import model.player.Player;

public class ConsoleView {
	private static final ConsoleView CONSOLE_VIEW = new ConsoleView();

	public static ConsoleView GetInstance() {
		return CONSOLE_VIEW;
	}

	public void Show(Player[] players) {

		CollectionUtils.forAllDo(Arrays.asList(players), new Closure(){

			@Override
			public void execute(Object player) {
				printPlayerCards((Player) player);
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
