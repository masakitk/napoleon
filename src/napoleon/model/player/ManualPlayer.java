package napoleon.model.player;

import napoleon.model.card.Card;
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

	@Override
	public Declaration askForDeclare(Declaration currentDeclaration, Viewer viewer) {
		return viewer.askForDeclare(currentDeclaration, cards);
	}

	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		return ManualPlayerUtil.chooseCardToOpenByManual(turn, viewer, declaration, cards, false);
	}

}
