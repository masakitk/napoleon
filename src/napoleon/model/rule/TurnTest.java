package napoleon.model.rule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;

import org.apache.commons.collections15.CollectionUtils;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;
	@Tested Turn turn = Turn.New(1, Suit.Spade);
	Card[] parameterOfCardsToTake;

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_�C���X�^���X�������^�[����1��菬�����ꍇ�̓G���[() {
		Turn.New(0, Suit.Spade);
	}

	@Test
	public void T02_�C���X�^���X�������^�[����12���傫���ꍇ�̓G���[() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�^�[���ԍ���1�ȏ�12�ȉ��ł���K�v������܂�");
		Turn.New(13, Suit.Spade);
	}
	
	@Test
	public void T03_�ŏ��ɏo���ꂽ�X�[�g����D�ɂȂ邱��() {
		Turn turn = Turn.New(1, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
	
	@Test
	public void T03_�ŏ��ɃW���[�J�[���o���ꂽ��؂�D����D�ɂȂ邱��() {
		Turn turn = Turn.New(2, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.Jorker);
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}

	@Test
	public void T03_����J�[�h�����̃^�[���ōŏ��ɃW���[�J�[���o���ꂽ���D�����2�l�ڂɂ܂�邱��() {
		Turn turn = Turn.New(1, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.Jorker);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player2, Card.New(Suit.Dia, 5));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Dia));	
	}
	
	@Test
	public void T03_�ŏ��ɃW���[�J�[�������o���ꂽ���Ƃ𔻒�ł��邱��() {
		Turn turn = Turn.New(2, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Club, 3));
		assertThat(turn.isRequireJorkerOpenedFirst(), equalTo(true));
	}
	
	@Test
	public void T04_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���() {
		
		final Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 4), 
				player4, Card.New(Suit.Dia, 1), 
				player1, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Dia, 1),
				new Card[]{Card.New(Suit.Dia, 1), Card.New(Suit.Heart, 13)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T04_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���() {
		
		final Parameters param = new Parameters(
				Turn.New(2, Suit.Dia),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 4), 
				player4, Card.New(Suit.Dia, 1), 
				player1, Card.New(Suit.Dia, 9), 
				player4, Card.New(Suit.Dia, 1),
				new Card[]{Card.New(Suit.Dia, 1)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T05_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_�Z�C��2�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player1, Card.New(Suit.Dia, 13),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	@Test
	public void T05_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�Z�C��2() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Dia, 2),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	@Test
	public void T05_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�Z�C��2����_�ʂ̃X�[�g����() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Dia, 12),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Heart, 13)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	@Test
	public void T06_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_���߂��̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Spade, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Spade, 1),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Spade, 1), Card.New(Suit.Heart, 12)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T06_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_���߂�() {
		Parameters param = new Parameters(
				Turn.New(11, Suit.Spade),
				player2, Card.New(Suit.Spade, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Heart, 12),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Spade, 1), Card.New(Suit.Heart, 12)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T07_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_�}�C�e�B�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Spade, 1), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Heart, 1),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Spade, 1),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T07_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�}�C�e�B() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Spade, 1), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 1),
				new Card[]{Card.New(Suit.Heart, 13), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Spade, 1),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T08_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_�؂�D�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Club, 3), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Heart, 1),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	@Test
	public void T08_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�؂�D() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Club, 3), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 3),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1)});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	@Test
	public void T09_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_��J�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.New(Suit.Club, 12), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Club, 1),
				new Card[]{Card.New(Suit.Club, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T09_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_��J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.New(Suit.Club, 12), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 11),
				new Card[]{Card.New(Suit.Club, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T10_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_��J�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Spade, 12), 
				player3, Card.New(Suit.Heart, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Spade, 12),
				new Card[]{Card.New(Suit.Spade, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Club, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T10_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_��J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Spade, 12), 
				player3, Card.New(Suit.Heart, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 11),
				new Card[]{Card.New(Suit.Spade, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Club, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���_Jorker�̖���() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Club, 1),
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Club, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�ŏ���Jorker() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.Jorker,
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_JorkerVS��J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 11), 
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Club, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_JorkerVS�}�C�e�B() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.Mighty, 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.Mighty, 
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.Mighty,});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_JorkerVS���߂�() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.Mighty, 
				player1, Card.Yoromeki, 
				player1, Card.Yoromeki, 
				new Card[]{Card.Yoromeki, Card.New(Suit.Club, 1), Card.Mighty,});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}

	@Test
	public void T11_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l������ŏ��҂𔻒f�ł���_�r����Jorker() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.Jorker, 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 11),
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		�^�[�����񂵂ď��҂��m�F(param);
	}
	
	private void �^�[�����񂵂ď��҂��m�F(Parameters param) {
		turn = param.turn;
		turn.addCard(param.player1, param.card1);
		turn.addCard(param.player2, param.card2);
		turn.addCard(param.player3, param.card3);
		turn.addCard(param.player4, param.card4);
		turn.winnerGainCards();
		
		assertThat(turn.getWinnerCard(), equalTo(param.winnerCard));
		assertThat(turn.getWinner(), equalTo(param.winner));
		parameterOfCardsToTake = param.winnerWillGet;
		new Verifications(){
			{
				Collection<Card> cardToTake;
				turn.getWinner().takeCards(cardToTake = withCapture());
				assertTrue(CollectionUtils.isEqualCollection(cardToTake, Arrays.asList(parameterOfCardsToTake)));
			}
		};
	}

	class Parameters {
		public Parameters(Turn turn, Player player1, Card card1, Player player2,
				Card card2, Player player3, Card card3, Player player4,
				Card card4, Player winner, Card winnerCard, Card[] winnerWillGet) {
			super();
			this.turn = turn;
			this.player1 = player1;
			this.card1 = card1;
			this.player2 = player2;
			this.card2 = card2;
			this.player3 = player3;
			this.card3 = card3;
			this.player4 = player4;
			this.card4 = card4;
			this.winner = winner;
			this.winnerCard = winnerCard;
			this.winnerWillGet = winnerWillGet;
		}
		Turn turn;
		Player player1;
		Card card1;
		Player player2;
		Card card2;
		Player player3;
		Card card3;
		Player player4;
		Card card4;
		Player winner;
		Card winnerCard;
		Card[] winnerWillGet;
	}

	
}

