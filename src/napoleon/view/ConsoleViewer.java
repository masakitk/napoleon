package napoleon.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Transformer;

import napoleon.model.card.Card;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;

public class ConsoleViewer implements Viewer {
	private static final ConsoleViewer CONSOLE_VIEWER = new ConsoleViewer();

	public static ConsoleViewer GetInstance() {
		return CONSOLE_VIEWER;
	}

	public void show(Player[] players) {

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

	public List<Card> sortCardsToView(List<Card> cards) {
		List<Card> sorted = new ArrayList<Card>(cards);
		Collections.sort(sorted, new Comparator<Card>(){
			@Override
			public int compare(Card c1, Card c2) {
				return c1.isUpperOrderByMarkAndStrength(c2) ? 1 : -1;
			}
		});
		return sorted;
	}

	public void showMessage(String message) {
		System.out.println(message);
	}

	@Override
	public Collection<String> formatTurnCards(Turn turn) {
		return CollectionUtils.collect(
				turn.getCardHash().entrySet(), new Transformer<Entry<Player, Card>, String>() {

			@Override
			public String transform(Entry<Player, Card> entry) {
				return String.format("%s:%s", entry.getKey().getName(), entry.getValue());
			}
		});
	}

	@Override
	public void showGainedCardsForEachPlayer(Napoleon napoleon, Player[] players) {
		showMessage(String.format("napoleon gained %s", napoleon.cardsGained()));
		for (Player p : players) {
			showMessage(String.format("player %s gained %s", p.getName(), p.cardsGained()));
		}
	}

	@Override
	public void showExchangedCards(Table table) {
		showMessage(String.format("table cards are %s", table.getNoUseCards()));
	}

	@Override
	public void showTurnResult(Integer currentTurnNo, String turnWinnerName,
			Collection<String> cardsToShow) {
		showMessage(String.format("★turn[%d], winner[%s]: cards[%s]", 
						currentTurnNo, 
						turnWinnerName,
						cardsToShow));
	}

	@Override
	public void showPlayersGainedCards(Player[] players) {
		for (Player p : players){
			showMessage(String.format("player:%s / Gained:%s", p.getName(), p.cardsGained()));
		}
	}
}
