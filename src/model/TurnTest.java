package model;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void test�C���X�^���X�������^�[����1��菬�����ꍇ�̓G���[() {
		Turn.New(new ArrayList<Card>(), 0);
	}

	@Test
	public void test�C���X�^���X�������^�[����12���傫���ꍇ�̓G���[() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("�^�[���ԍ���1�ȏ�12�ȉ��ł���K�v������܂�");
		Turn.New(new ArrayList<Card>(), 13);
	}
}
