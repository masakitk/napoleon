package napoleon.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Map.Entry;

import napoleon.model.resource.Messages;
import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.ManualPlayerUtil;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Declaration;
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

	public void printPlayerCards(Player player) {
		System.out.print(player.getName());
        System.out.printf(Messages.RESOURCE.getString("cardsGained"), sortCardsToView(player.cardsGained()));
        System.out.printf("/ having:%s%n", sortCardsToView(player.cardsHaving()));
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

	@Override
	public void showExtraCards(List<Card> extraCards) {
		showMessage(String.format("[%s] remained on the table, you can take them.", extraCards));
	}

	@Override
	public Card inputCard() {
		try{
			return convertToCard(inputSuitAndNumber("input card(Ex. S1:♠A、H13:♥13 etc.."));
		} catch (IllegalArgumentException e) {
			showMessage(e.getMessage());
			return null;
		}
	}
	
	protected String inputSuitAndNumber(String information) {
		String input;
		try{
			input = getInputString(information);
		} catch (NoSuchElementException e) {
			return inputSuitAndNumber(information);
		}

		if(input.length() < 2) {
			return inputSuitAndNumber(information);
		}
		return input;
	}
	
	static Card convertToCard(String input) {
		if("JORKER".equals(input.toUpperCase())) return Card.Jorker;
		
		Suit suit = convertToSuit(getSuitPart(input));
		int number = convertToNumber(getNumberPart(input));
		return Card.New(suit, number);
	}

	static Suit convertToSuit(String suitPart) {
		if(suitPart.toUpperCase().equals("S")) return Suit.Spade;
		if(suitPart.toUpperCase().equals("H")) return Suit.Heart;
		if(suitPart.toUpperCase().equals("D")) return Suit.Dia;
		if(suitPart.toUpperCase().equals("C")) return Suit.Club;
		throw new IllegalArgumentException("1文字目はS,H,D,Cのいずれかのスートにして下さい。（Jorkerの場合を除く）");
	}

	static int convertToNumber(String numberPart) {
		try{
			return Integer.parseInt(numberPart);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("2文字目以降は1～13の数字を入力して下さい。", e);
		}
	}

	static protected String getNumberPart(String input) {
		return input.substring(1);
	}

	static protected String getSuitPart(String input) {
		return input.substring(0, 1);
	}

	public String getInputString(String information) {
		Scanner stdReader = new Scanner(System.in);
		showMessage(String.format("%s : ", information));
		String line = stdReader.nextLine(); // ユーザの一行入力を待つ
		return line;
	}

	@Override
	public Declaration askForDeclare(Declaration currentDeclaration, List<Card> cards) {
		showMessage(String.format("you have :[%s]", sortCardsToView(cards)));
		String input;
		input = inputSuitAndNumber("input declaration(Ex. S13:♠A、H15:♥15、Pass etc..");

		if(input.toUpperCase().equals("PASS"))
			return Declaration.Pass;
		
		String suitPart = input.substring(0, 1);
		String numberPart = input.substring(1);
		try{
			Suit suit = convertToSuit(suitPart);
			int number = convertToNumber(numberPart);
			return Declaration.New(suit, number);
		} catch (Exception e) {
			showMessage(e.getMessage());
			return askForDeclare(currentDeclaration, cards);
		}
	}

	@Override
	public Collection<Card> inputCardsToChange(Declaration fixedDeclaration,
			Table table, List<Card> cards) {
		String[] cardsEntered = getInputString("input cards to unuse, as [C3,C4,C5...]").split(",");

		if(!canConvertAllToCard(cardsEntered)) {
			return inputCardsToChange(fixedDeclaration, table, cards);
		}

		Collection<Card> unuseCards = CollectionUtils.collect(
				Arrays.asList(cardsEntered), 
				new Transformer<String, Card>(){
					@Override
					public Card transform(final String s) {
						return convertToCard(s);
					}
				});

		List<Card> extraCards = table.getCards();
		if(invalidCardCount(unuseCards, extraCards.size())){
			showMessage(String.format("select %d unuse cards", extraCards.size()));
			return inputCardsToChange(fixedDeclaration, table, cards);
		}

		Collection<Card> wrongCards = getWrongCards(unuseCards, extraCards, cards);
		if(!wrongCards.isEmpty()){
			showMessage(String.format("you don't have %s", wrongCards));
			return inputCardsToChange(fixedDeclaration, table, cards);
		}
		return unuseCards;
	}

	public boolean canConvertToCard(String inputString) {
		try{
			convertToCard(inputString);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private boolean canConvertAllToCard(String[] cardsEntered) {
		return !CollectionUtils.exists(Arrays.asList(cardsEntered), new Predicate<String>() {

			@Override
			public boolean evaluate(String inputString) {
				return !ManualPlayerUtil.canConvertToCard(inputString);
			}
		});
	}

	private boolean invalidCardCount(Collection<Card> unuseCards, int size) {
		return size != unuseCards.size();
	}

	private Collection<Card> getWrongCards(Collection<Card> unuseCards, final Collection<Card> extraCards, final Collection<Card> cards) {
		return CollectionUtils.select(unuseCards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
				return !cards.contains(card) && !extraCards.contains(card);
			}
		});
	}

	@Override
	public Card inputCardToAdjutant() {
		showMessage("input card to adjutant");
		return inputCard();
	}
}
