package napoleon.model.player;

import java.util.Collection;
import java.util.Map.Entry;

import napoleon.model.card.Card;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

public class ManualNapoleon extends Napoleon {

	protected ManualNapoleon(ManualPlayer player) {
		super(player);
	}

	public static Napoleon New(ManualPlayer player) {
		return new ManualNapoleon(player);
	}
	
	@Override
	protected Card chooseCardToOpen(Turn turn, Viewer viewer, Declaration declaration) {
		return ManualPlayerUtil.chooseCardToOpenByManual(turn, viewer, declaration, cards);
	}
}
