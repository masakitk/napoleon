package napoleon.model.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;

import com.sun.xml.internal.ws.util.xml.NodeListIterator;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

public class ManualPlayer extends napoleon.model.player.Player {

	private String INPUT_INFORMATION_TEXT = "1文字目にスート(S,H,D,Cのいずれか)、2文字目以降に1～13の数字を入力して下さい。";

	public ManualPlayer(String name) {
		super(name);
	}

	public static ManualPlayer New(String name) {
		return new ManualPlayer(name);
	}

	//	@Override
	//	public Declaration AskForDeclare(Declaration currentDeclaration, Viewer viewer) {
	//		viewer.showMessage(String.format("you have :[%s]", cards));
	//		String input;
	//		input = InputSuitAndNumber(viewer);
	//		
	//		String suitPart = input.substring(0, 1);
	//		String numberPart = input.substring(1);
	//		try{
	//			Suit suit = convertToSuit(suitPart);
	//			int number = convertToNumber(numberPart);
	//			return Card.New(suit, number);
	//		} catch (Exception e) {
	//			viewer.showMessage(e.getMessage());
	//			return chooseCardToOpen(turn, viewer);
	//		}
	//	}

	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		Card toOpen = null;
		while(toOpen == null) {
			viewer.showMessage(String.format("this turn opened %s, %s", turn.getCards(), declaration));
			viewer.showMessage(String.format("You have %s", viewer.sortCardsToView(cards)));
			toOpen = rejectInvalidCard(inputCard(viewer, turn), turn, viewer);
		}
		return toOpen;
	}

	Card rejectInvalidCard(Card card, Turn turn, Viewer viewer) {
		if(card == null) return null;
		
		if(!hasCard(card)) {
			viewer.showMessage("そのカードは持っていません。");
			return null;
		}

		if(turn.isJorkerOpenedFirst()) {
			if(card.getSuit() != turn.getTrump()) {
				viewer.showMessage("切り札請求された場合は、切り札をださなければなりません。");
				return null;
			}
			return card;
		}

		if(turn.isRequireJorkerOpenedFirst()) {
			if(!card.equals(Card.Jorker)) {
				viewer.showMessage("ジョーカー請求された場合は、ジョーカーをださなければなりません。");
				return null;
			}
			return card;
		}
		
		if(turn.isLeadSuitDefined() && !findSameMark(cards, turn.getLeadSuit()).isEmpty()){
			if(card.getSuit() != turn.getLeadSuit()){
				viewer.showMessage("台札がある場合は、台札をださなければなりません。");
				return null;
			}
		}
		return card;
	}

	Card inputCard(Viewer viewer, Turn turn) {
		try{
			return convertToCard(InputSuitAndNumber(viewer));
		} catch (IllegalArgumentException e) {
			viewer.showMessage(e.getMessage());
			return null;
		}
	}

	protected Card convertToCard(String input) {
		if("JORKER".equals(input.toUpperCase())) return Card.Jorker;
		
		Suit suit = convertToSuit(getSuitPart(input));
		int number = convertToNumber(getNumberPart(input));
		return Card.New(suit, number);
	}

	protected String getNumberPart(String input) {
		return input.substring(1);
	}

	protected String getSuitPart(String input) {
		return input.substring(0, 1);
	}

	private boolean hasCard(final Card card) {
		return CollectionUtils.exists(cards, new Predicate<Card>() {
			@Override
			public boolean evaluate(Card c) {
				return c.equals(card);
			}
		});
	}

	protected String InputSuitAndNumber(Viewer viewer) {
		String input;
		try{
			input = getInputString("input card(Ex. S1:SpadeA、H13:Heart13 etc..");
		} catch (NoSuchElementException e) {
			return InputSuitAndNumber(viewer);
		}

		if(input.length() < 2) {
			return InputSuitAndNumber(viewer);
		}
		return input;
	}

	private Suit convertToSuit(String suitPart) {
		if(suitPart.toUpperCase().equals("S")) return Suit.Spade;
		if(suitPart.toUpperCase().equals("H")) return Suit.Heart;
		if(suitPart.toUpperCase().equals("D")) return Suit.Dia;
		if(suitPart.toUpperCase().equals("C")) return Suit.Club;
		throw new IllegalArgumentException("1文字目はS,H,D,Cのいずれかにして下さい。");
	}

	private int convertToNumber(String numberPart) {
		try{
			return Integer.parseInt(numberPart);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("2文字目以降は1～13の数字を入力して下さい。", e);
		}
	}

	public String getInputString(String information) {
		Scanner stdReader = new Scanner(System.in);
		System.out.println(String.format("%s : ", information));
		String line = stdReader.nextLine(); // ユーザの一行入力を待つ
		System.out.println(String.format("line is [%s]", line));
		return line;
	}
}
