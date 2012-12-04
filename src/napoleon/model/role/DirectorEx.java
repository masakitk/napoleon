package napoleon.model.role;

import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

public class DirectorEx extends Director {

	public void setNapoleon(Napoleon napoleon) {
		this.napoleon = napoleon;
	}

	public void setIsNobodyDeclared(boolean isNobodyDeclared) {
		this.isNobodyDeclared = isNobodyDeclared;
	}

	public static DirectorEx NewEx(Table table, Dealer dealer, Player[] players, Viewer viewer) {
		DirectorEx instance = new DirectorEx();
		instance.table = table;
		instance.dealer = dealer;
		instance.players = players;
		instance.viewer = viewer;
		return instance;
	}

	public void SetExtraCardChanged(boolean b) {
		extraCardChanged = b;
	}

	public void setTurns(Turn[] turns) {
		this.turns = turns;
	}


}

