package model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_�C���X�^���X�������^�[����1��菬�����ꍇ�̓G���[() {
		Turn.New(new ArrayList<Card>(), 0);
	}

	@Test
	public void T02_�C���X�^���X�������^�[����12���傫���ꍇ�̓G���[() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�^�[���ԍ���1�ȏ�12�ȉ��ł���K�v������܂�");
		Turn.New(new ArrayList<Card>(), 13);
	}
	
	@Test
	public void T03_�ŏ��ɏo���ꂽ�X�[�g����D�ɂȂ邱��() {
		Turn turn = Turn.New(new ArrayList<Card>(), 1);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
}
