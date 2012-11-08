package napoleon.model.card;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class CardTest {

	@Test
	public void �J�[�h�쐬������0�̓G���[() {
		try{
			Card.New(Suit.Heart, 0);
			fail("�`�F�b�N�ł��ĂȂ�");
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), IsEqual.equalTo("������1����13�͈̔͂ɂ���܂���B"));
		}
	}

	@Test
	public void �J�[�h�쐬������14�̓G���[() {
		try{
			Card.New(Suit.Spade, 14);
			fail("�`�F�b�N�ł��ĂȂ�");
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), IsEqual.equalTo("������1����13�͈̔͂ɂ���܂���B"));
		}
	}

}
