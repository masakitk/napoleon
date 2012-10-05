package model.player;


import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;
import model.card.Card;
import model.card.Suit;
import model.rule.Declaration;
import model.rule.Turn;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PlayerTest {

	@Mocked
	Turn turn;
	
	@Test
	public void T01_1���J�[�h���o�����炻�̃J�[�h�͎茳����Ȃ��Ȃ邱��(){
		final Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_��D���ݒ肳��Ă��đ�D������Ƃ��͑�D���o������() {
		new Expectations() {
			{
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Spade);
			}
		};
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		player.takeCard(Card.New(Suit.Heart, 3));
		assertThat(player.openCard(turn).getSuit(), IsEqual.equalTo(Suit.Spade));
	}
	
	@Test
	public void T02_�ŏ��ɃW���[�J�[�o���ꂽ��؂�D���Ȃ���Έ�ԑ傫���G�D���o������() {
		new Expectations() {
			{
				turn.isLeadSuitDefined(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
			}
		};
		Player player2 = Player.New("fuga");
		player2.takeCard(Card.Jorker);
		Card jorker = player2.openCard(turn);
		assertThat(jorker, IsEqual.equalTo(Card.Jorker));
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 1));
		player.takeCard(Card.New(Suit.Spade, 12));
		player.takeCard(Card.New(Suit.Heart, 13));
		assertThat(player.openCard(turn).getNumber(), IsEqual.equalTo(1));
	}

	@Test
	public void T03_��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��͂Ȃ�ł������̂�1���J�[�h���o������() {
		new Expectations() {
			{
				turn.isLeadSuitDefined(); returns(false);
			}
		};
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T04_�Ƃ肠�����N���錾���ĂȂ�������N���u��13�Ő錾����(){
		Player player = Player.New("hoge");
		assertThat(player.AskForDeclare(null), IsEqual.equalTo(Declaration.New(Suit.Club, 13)));
	}
}
