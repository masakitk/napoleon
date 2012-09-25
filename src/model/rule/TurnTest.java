package model.rule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;
import model.card.Card;
import model.card.Suit;
import model.player.Player;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TurnTest {
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_�C���X�^���X�������^�[����1��菬�����ꍇ�̓G���[() {
		Turn.New(0);
	}

	@Test
	public void T02_�C���X�^���X�������^�[����12���傫���ꍇ�̓G���[() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�^�[���ԍ���1�ȏ�12�ȉ��ł���K�v������܂�");
		Turn.New(13);
	}
	
	@Test
	public void T03_�ŏ��ɏo���ꂽ�X�[�g����D�ɂȂ邱��() {
		Turn turn = Turn.New(1);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
	
	@Test
	public void T04_�J�[�h��4���o����Ă���ꍇ�ɓ���J�[�h�l�������ŏ��҂𔻒f�ł���() {
		Turn turn = Turn.New(1);
		turn.addCard(player2, Card.New(Suit.Dia, 8));
		turn.addCard(player3, Card.New(Suit.Dia, 4));
		turn.addCard(player4, Card.New(Suit.Dia, 12));
		turn.addCard(player1, Card.New(Suit.Heart, 13));
		
		assertThat(turn.getWinnerCard(), equalTo(Card.New(Suit.Dia, 12)));
		assertThat(turn.getWinner(), equalTo(player4));
	}
	
}

