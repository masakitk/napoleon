package napoleon.model.player;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class NapoleonTest {

	@Test
	public void T01_�؂�D�Ŏ����������Ă��Ȃ�����J�[�h�l�����Ȃ��ň�ԋ����J�[�h��Ԃ�() {
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 1));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.SomeTrumpCard(declaration), equalTo(Card.New(Suit.Club, 13)));

		napoleon.takeCard(Card.New(Suit.Club, 13));
		napoleon.takeCard(Card.New(Suit.Club, 11));
		assertThat(napoleon.SomeTrumpCard(declaration), equalTo(Card.New(Suit.Club, 12)));
	}
}
