package napoleon.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import napoleon.model.*;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Turn;

public class ManualPlayer extends napoleon.model.player.Player {

	public ManualPlayer(String name) {
		super(name);
	}
	
	@Override
	protected Card chooseCardToOpen(Turn turn) {
		String input = getInputString("input card(Ex. S1:SpadeA、H13:Heart13 etc..");
		String suitPart = input.substring(0, 1);
		String numberPart = input.substring(1);
		try{
			Suit suit = convertToSuit(suitPart);
			int number = convertToNumber(numberPart);
			return Card.New(suit, number);
		} catch (Exception e) {
			return chooseCardToOpen(turn);
		}
	}

	private Suit convertToSuit(String suitPart) {
		// TODO Auto-generated method stub
		return null;
	}

	private int convertToNumber(String numberPart) {
		return Integer.parseInt(numberPart);
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
