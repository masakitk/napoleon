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

import napoleon.model.player.ManualNapoleon;
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

import static napoleon.model.resource.Messages.*;

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
        System.out.printf(RESOURCE.getString(CARDS_GAINED), sortCardsToView(player.cardsGained()));
        System.out.printf(RESOURCE.getString(CARDS_HAVING), sortCardsToView(player.cardsHaving()));
	}

    @Override
    public void printPlayerHavingCards(Player player) {
        System.out.print(player.getName());
        System.out.printf(RESOURCE.getString(CARDS_HAVING), sortCardsToView(player.cardsHaving()));
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
		showMessage(String.format(RESOURCE.getString(Messages.NAPOLEON_GAINED_CARDS), napoleon.cardsGained()));
		for (Player p : players) {
			showMessage(String.format(RESOURCE.getString(Messages.PLAYER_GAINED_CARDS), p.getName(), p.cardsGained()));
		}
	}

	@Override
	public void showExchangedCards(Table table) {
		showMessage(String.format(RESOURCE.getString(Messages.UNUSED_CARDS), table.getNoUseCards()));
	}

	@Override
	public void showTurnResult(Integer currentTurnNo, String turnWinnerName,
			Collection<String> cardsToShow) {
		showMessage(String.format(RESOURCE.getString(Messages.TURN_WINNER),
						currentTurnNo, 
						turnWinnerName,
						cardsToShow));
	}

	@Override
	public void showPlayersGainedCards(Player[] players) {
		for (Player p : players){
			showMessage(String.format(RESOURCE.getString(Messages.PLAYER_GAINED_CARDS), p.getName(), p.cardsGained()));
		}
	}

	@Override
	public void showExtraCards(List<Card> extraCards) {
		showMessage(String.format(RESOURCE.getString(Messages.EXTRA_CARDS), extraCards));
	}

    private Card inputCard(String message) {
        try{
            return convertToCard(inputSuitAndNumber(message));
        } catch (IllegalArgumentException e) {
            showMessage(e.getMessage());
            return null;
        }
    }

	@Override
	public Card inputCard() {
		return inputCard(RESOURCE.getString(Messages.INPUT_CARD_OR_GO_ADJUTANT));
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
		if("JOKER".equals(input.toUpperCase())) return Card.Joker;

		Suit suit = convertToSuit(getSuitPart(input));
		int number = convertToNumber(getNumberPart(input));
		return Card.New(suit, number);
	}

	static Suit convertToSuit(String suitPart) {
		if(suitPart.toUpperCase().equals("S")) return Suit.Spade;
		if(suitPart.toUpperCase().equals("H")) return Suit.Heart;
		if(suitPart.toUpperCase().equals("D")) return Suit.Dia;
		if(suitPart.toUpperCase().equals("C")) return Suit.Club;
		throw new IllegalArgumentException("1文字目はS,H,D,Cのいずれかのスートにして下さい。（Jokerの場合を除く）");
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
		showMessage(String.format(RESOURCE.getString(YOUR_CARDS), sortCardsToView(cards)));
		String input;
		input = inputSuitAndNumber(RESOURCE.getString(Messages.INPUT_DECLARATION));

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
		String[] cardsEntered = getInputString(RESOURCE.getString(Messages.INPUT_UNUSED_CARDS_WITH_CONSOLE)).split(",");

		if(!canConvertAllToCard(cardsEntered)) {
			return inputCardsToChange(fixedDeclaration, table, cards);
		}

		Collection<Card> unusedCards = CollectionUtils.collect(
				Arrays.asList(cardsEntered),
				new Transformer<String, Card>(){
					@Override
					public Card transform(final String s) {
						return convertToCard(s);
					}
				});

		List<Card> extraCards = table.getCards();
		if(invalidCardCount(unusedCards, extraCards.size())){
			showMessage(String.format(RESOURCE.getString(Messages.SELECT_UNUSED_CARDS), extraCards.size()));
			return inputCardsToChange(fixedDeclaration, table, cards);
		}

		Collection<Card> wrongCards = getWrongCards(unusedCards, extraCards, cards);
		if(!wrongCards.isEmpty()){
			showMessage(String.format(RESOURCE.getString(Messages.YOU_HAVE_NOT_THE_CARD), wrongCards));
			return inputCardsToChange(fixedDeclaration, table, cards);
		}
		return unusedCards;
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

	private boolean invalidCardCount(Collection<Card> unusedCards, int size) {
		return size != unusedCards.size();
	}

	private Collection<Card> getWrongCards(Collection<Card> unusedCards, final Collection<Card> extraCards, final Collection<Card> cards) {
		return CollectionUtils.select(unusedCards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
				return !cards.contains(card) && !extraCards.contains(card);
			}
		});
	}

	@Override
	public Card inputCardToAdjutant() {
		showMessage(RESOURCE.getString(Messages.INPUT_ADJUTANT_CARD));
		return inputCard(RESOURCE.getString(Messages.INPUT_CARD));
	}
}
