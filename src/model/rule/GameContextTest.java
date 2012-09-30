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
	public void T_�錾�ɑ΂��ē���J�[�h���ݒ肳��邱��_Spade() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Spade, 13),
				Card.New(Suit.Spade, 11),
				Card.New(Suit.Club, 11)); 
		
		GameContext�ɑ΂��Đ錾���Z�b�g���ē���J�[�h���m�F����(params);
	}

	@Test
	public void T_�錾�ɑ΂��ē���J�[�h���ݒ肳��邱��_Heart() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Heart, 13),
				Card.New(Suit.Heart, 11),
				Card.New(Suit.Dia, 11)); 
		
		GameContext�ɑ΂��Đ錾���Z�b�g���ē���J�[�h���m�F����(params);
	}

	@Test
	public void T_�錾�ɑ΂��ē���J�[�h���ݒ肳��邱��_Dia() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Dia, 13),
				Card.New(Suit.Dia, 11),
				Card.New(Suit.Heart, 11)); 
		
		GameContext�ɑ΂��Đ錾���Z�b�g���ē���J�[�h���m�F����(params);
	}

	@Test
	public void T_�錾�ɑ΂��ē���J�[�h���ݒ肳��邱��_Club() {
		Parameters params = new Parameters(
				Declaration.New(Suit.Club, 13),
				Card.New(Suit.Club, 11),
				Card.New(Suit.Spade, 11)); 
		
		GameContext�ɑ΂��Đ錾���Z�b�g���ē���J�[�h���m�F����(params);
	}

	private void GameContext�ɑ΂��Đ錾���Z�b�g���ē���J�[�h���m�F����(Parameters params) {
		Table table = Table.New();
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		GameContext context = GameContext.New(table, players);
		context.setNapoleon(players[0], params.declaration);
		assertThat(context.getRightBower(), equalTo(params.rightBower));
		assertThat(context.getLeftBower(), equalTo(params.leftBower));
	}

}
