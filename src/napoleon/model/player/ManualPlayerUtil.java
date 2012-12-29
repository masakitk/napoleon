package napoleon.model.player;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Map.Entry;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.resource.Messages;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import static napoleon.model.resource.Messages.*;

public class ManualPlayerUtil {
	static Card chooseCardToOpenByManual(Turn turn, Viewer viewer,
			Declaration declaration, List<Card> cards) {
		Card toOpen = null;
		while(toOpen == null) {
			viewer.showMessage(String.format(RESOURCE.getString(CARDS_OF_THIS_TURN_AND_DECLARATION), convertNamesAndCards((Collection<Entry<Player, Card>>)turn.getCardHash().entrySet()), declaration.toShow()));
			viewer.showMessage(String.format(RESOURCE.getString(YOUR_CARDS), viewer.sortCardsToView(cards)));
			toOpen = rejectInvalidCard(viewer.inputCard(), turn, viewer, cards);
		}
		return toOpen;
	}

	static private Collection<String> convertNamesAndCards(Collection<Entry<Player, Card>> cardHash) {
		
		return CollectionUtils.collect(cardHash, new Transformer<Entry<Player, Card>, String>() {

			@Override
			public String transform(Entry<Player, Card> entry) {
				return String.format("%s:%s", entry.getKey().getName(), entry.getValue());
			}
		});
	}

	static Card rejectInvalidCard(Card card, Turn turn, Viewer viewer, List<Card> cards) {
		if(card == null) return null;
		
		if(!hasCard(cards, card)) {
			viewer.showMessage(RESOURCE.getString(Messages.YOU_HAVE_NOT_THE_CARD));
			return null;
		}

		if(turn.isJokerOpenedFirst()) {
			final Suit trump = turn.getTrump();
			if(hasAnyCardOf(cards, trump) && card.getSuit() != trump) {
				viewer.showMessage(RESOURCE.getString(Messages.YOU_MUST_OPEN_TRUMP));
				return null;
			}
			return card;
		}

		if(turn.isRequireJokerOpenedFirst()) {
			if(hasCard(cards, Card.Joker) && !card.equals(Card.Joker)) {
				viewer.showMessage(RESOURCE.getString(Messages.YOU_MUST_OPEN_JOKER));
				return null;
			}
			return card;
		}
		
		if(turn.isLeadSuitDefined() && !Player.findSameMark(cards, turn.getLeadSuit()).isEmpty()){
			if(card.getSuit() != turn.getLeadSuit()){
				viewer.showMessage(RESOURCE.getString(Messages.YOU_MUST_OPEN_LEAD_SUIT));
				return null;
			}
		}
		return card;
	}

	static private boolean hasAnyCardOf(List<Card> cards, final Suit suit) {
		return CollectionUtils.exists(cards, new Predicate<Card>() {

			@Override
			public boolean evaluate(Card card) {
				return card.getSuit() == suit;
			}
		});
	}

	static Card inputCard(Viewer viewer, Turn turn) {
		try{
			return convertToCard(inputSuitAndNumber(viewer, "input card(Ex. S1:♠A、H13:♥13 etc.."));
		} catch (IllegalArgumentException e) {
			viewer.showMessage(e.getMessage());
			return null;
		}
	}

	static Card convertToCard(String input) {
		if("JOKER".equals(input.toUpperCase())) return Card.Joker;
		
		Suit suit = convertToSuit(getSuitPart(input));
		int number = convertToNumber(getNumberPart(input));
		return Card.New(suit, number);
	}

	static protected String getNumberPart(String input) {
		return input.substring(1);
	}

	static protected String getSuitPart(String input) {
		return input.substring(0, 1);
	}

	static private boolean hasCard(List<Card> cards, final Card card) {
		return CollectionUtils.exists(cards, new Predicate<Card>() {
			@Override
			public boolean evaluate(Card c) {
				return c.equals(card);
			}
		});
	}

	static protected String inputSuitAndNumber(Viewer viewer, String information) {
		String input;
		try{
			input = getInputString(information, viewer);
		} catch (NoSuchElementException e) {
			return inputSuitAndNumber(viewer, information);
		}

		if(input.length() < 2) {
			return inputSuitAndNumber(viewer, information);
		}
		return input;
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

	static public String getInputString(String information, Viewer viewer) {
		Scanner stdReader = new Scanner(System.in);
		viewer.showMessage(String.format("%s : ", information));
		String line = stdReader.nextLine(); // ユーザの一行入力を待つ
//		viewer.showMessage(String.format("line is [%s]", line));
		return line;
	}

	public static Card[] inputUnuseCards(Viewer viewer) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean canConvertToCard(String inputString) {
		try{
			convertToCard(inputString);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

}
