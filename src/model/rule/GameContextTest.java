package model.rule;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;
import model.card.Card;
import model.card.Suit;
import model.player.Player;

import org.junit.Test;

public class GameContextTest {

	public class Parameters {

		private final Declaration declaration;
		private final Card rightBower;
		private final Card leftBower;

		public Parameters(Declaration declaration, Card rightBower, Card leftBower) {
			this.declaration = declaration;
			// TODO Auto-generated constructor stub
			this.rightBower = rightBower;
			this.leftBower = leftBower;
		}

	}

	@Test
	public void T01_宣言に対して特殊カードが設定されること() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Spade, 13),
				Card.New(Suit.Spade, 11),
				Card.New(Suit.Club, 11)); 
		
		GameContextに対して宣言をセットして特殊カードを確認する(params);
	}

	private void GameContextに対して宣言をセットして特殊カードを確認する(Parameters params) {
		Table table = Table.New();
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		GameContext context = GameContext.New(table, players);
		context.setNapoleon(players[0], params.declaration);
		assertThat(context.getRightBower(), equalTo(params.rightBower));
		assertThat(context.getLeftBower(), equalTo(params.leftBower));
	}

}
