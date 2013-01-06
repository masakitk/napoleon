package napoleon.model.rule;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;

import org.junit.Test;

public class GameContextTest {

	public class Parameters {

		private final Declaration declaration;
		private final Card rightBower;
		private final Card leftBower;

		public Parameters(Declaration declaration, Card rightBower, Card leftBower) {
			this.declaration = declaration;
			this.rightBower = rightBower;
			this.leftBower = leftBower;
		}

	}

	@Test
	public void T_宣言に対して特殊カードが設定されること_Spade() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Spade, 13),
				Card.New(Suit.Spade, 11),
				Card.New(Suit.Club, 11)); 
		
		GameContextに対して宣言をセットして特殊カードを確認する(params);
	}

	@Test
	public void T_宣言に対して特殊カードが設定されること_Heart() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Heart, 13),
				Card.New(Suit.Heart, 11),
				Card.New(Suit.Dia, 11)); 
		
		GameContextに対して宣言をセットして特殊カードを確認する(params);
	}

	@Test
	public void T_宣言に対して特殊カードが設定されること_Dia() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Dia, 13),
				Card.New(Suit.Dia, 11),
				Card.New(Suit.Heart, 11)); 
		
		GameContextに対して宣言をセットして特殊カードを確認する(params);
	}

	@Test
	public void T_宣言に対して特殊カードが設定されること_Club() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Club, 13),
				Card.New(Suit.Club, 11),
				Card.New(Suit.Spade, 11)); 
		
		GameContextに対して宣言をセットして特殊カードを確認する(params);
	}

	private void GameContextに対して宣言をセットして特殊カードを確認する(Parameters params) {
		Table table = Table.New();
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		GameContext context = GameContext.New(params.declaration);
		assertThat(context.getRightBower(), equalTo(params.rightBower));
		assertThat(context.getLeftBower(), equalTo(params.leftBower));
	}

}
