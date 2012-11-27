package napoleon.model.player;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.apache.commons.collections15.CollectionUtils;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

public class ManualPlayer extends napoleon.model.player.Player {

	private String INPUT_INFORMATION_TEXT = "1文字目にスート(S,H,D,Cのいずれか)、2文字目以降に1～13の数字を入力して下さい。";

	public ManualPlayer(String name) {
		super(name);
	}

	public static ManualPlayer New(String name) {
		return new ManualPlayer(name);
	}

	@Override
	public Declaration AskForDeclare(Declaration currentDeclaration, Viewer viewer) {
		viewer.showMessage(String.format("you have :[%s]", cards));
		String input;
		input = ManualPlayerUtil.inputSuitAndNumber(viewer, "input declaration(Ex. S13:♠A、H15:♥15、Pass etc..");

		if(input.toUpperCase().equals("PASS"))
			return Declaration.Pass;
		
		String suitPart = input.substring(0, 1);
		String numberPart = input.substring(1);
		try{
			Suit suit = ManualPlayerUtil.convertToSuit(suitPart);
			int number = ManualPlayerUtil.convertToNumber(numberPart);
			return Declaration.New(suit, number);
		} catch (Exception e) {
			viewer.showMessage(e.getMessage());
			return AskForDeclare(currentDeclaration, viewer);
		}
	}

	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		return ManualPlayerUtil.chooseCardToOpenByManual(turn, viewer, declaration, cards);
	}

}
