package napoleon.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

public class ManualPlayer extends napoleon.model.player.Player {

	public ManualPlayer(String name) {
		super(name);
	}
	
	public static Player New(String name) {
		return new ManualPlayer(name);
	}
	
	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer) {
		String input = getInputString("input card(Ex. S1:SpadeA、H13:Heart13 etc..");
		String suitPart = input.substring(0, 1);
		String numberPart = input.substring(1);
		try{
			Suit suit = convertToSuit(suitPart);
			int number = convertToNumber(numberPart);
			return Card.New(suit, number);
		} catch (Exception e) {
			viewer.showMessage(e.getMessage());
			return chooseCardToOpen(turn, viewer);
		}
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

	public static String getInputString(String information) {
		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print(String.format("%s : ", information));
			String line;
			while ((line = stdReader.readLine()) != null) { // ユーザの一行入力を待つ
			}
			return line;
		} catch (IOException e) {
			e.getStackTrace();
			throw new RuntimeException("IOException thrown", e);
		} finally {
			try {
				stdReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
