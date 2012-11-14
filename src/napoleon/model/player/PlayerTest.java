package napoleon.model.player;


import static org.junit.Assert.*;
import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PlayerTest {

	@Mocked
	Turn turn;
	@Mocked
	Viewer viewer;
	Declaration declaration = Declaration.New(Suit.Club, 13);
	@Test
	public void T01_1���J�[�h���o�����炻�̃J�[�h�͎茳����Ȃ��Ȃ邱��(){
		final Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		player.openCard(turn, viewer, declaration);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_��D���ݒ肳��Ă��đ�D������Ƃ��͑�D���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Spade);
			}
		};
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		player.takeCard(Card.New(Suit.Heart, 3));
		assertThat(player.openCard(turn, viewer, declaration).getSuit(), IsEqual.equalTo(Suit.Spade));
	}
	
	@Test
	public void T02_��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��ł��Ȃɂ�1����������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Spade);
			}
		};
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Dia, 3));
		player.takeCard(Card.New(Suit.Heart, 3));
		player.openCard(turn, viewer, declaration).getSuit();
		assertThat(player.cardCount(), IsEqual.equalTo(1));
	}
	
	@Test
	public void T02_�ŏ��ɃW���[�J�[�o���ꂽ��؂�D���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
				turn.isJorkerOpenedFirst(); returns(true);
				turn.getTrump(); returns(Suit.Spade);
			}
		};
		Player player2 = Player.New("fuga");
		player2.takeCard(Card.Jorker);
		Card jorker = player2.openCard(turn, viewer, declaration);
		assertThat(jorker, IsEqual.equalTo(Card.Jorker));
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 13));
		player.takeCard(Card.New(Suit.Spade, 12));
		player.takeCard(Card.New(Suit.Heart, 1));
		assertThat(player.openCard(turn, viewer, declaration), IsEqual.equalTo(Card.New(Suit.Spade, 12)));
	}

	@Test
	public void T02_�ŏ��ɃW���[�J�[�o���ꂽ��؂�D���Ȃ���Έ�ԑ傫���G�D���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
				turn.isJorkerOpenedFirst(); returns(true);
				turn.getTrump(); returns(Suit.Dia);
			}
		};
		Player player2 = Player.New("fuga");
		player2.takeCard(Card.Jorker);
		Card jorker = player2.openCard(turn, viewer, declaration);
		assertThat(jorker, IsEqual.equalTo(Card.Jorker));
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 13));
		player.takeCard(Card.New(Suit.Spade, 12));
		player.takeCard(Card.New(Suit.Heart, 1));
		assertThat(player.openCard(turn, viewer, declaration).getNumber(), IsEqual.equalTo(1));
	}

	@Test
	public void T02_�ŏ��ɃW���[�J�[�����o���ꂽ��W���[�J�[���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
			}
		};
		Player player2 = Player.New("fuga");
		player2.takeCard(Card.RequireJorker);
		Card jorker = player2.openCard(turn, viewer, declaration);
		assertThat(jorker, IsEqual.equalTo(Card.RequireJorker));
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 12));
		player.takeCard(Card.Jorker);
		player.takeCard(Card.New(Suit.Club, 1));
		assertThat(player.openCard(turn, viewer, declaration), IsEqual.equalTo(Card.Jorker));
	}

	@Test
	public void T02_�ŏ��ɃW���[�J�[�����o���ꂽ��W���[�J�[���Ȃ��ꍇ�͕��ʂɑ�D���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Club);
			}
		};
		Player player2 = Player.New("fuga");
		player2.takeCard(Card.RequireJorker);
		Card jorker = player2.openCard(turn, viewer, declaration);
		assertThat(jorker, IsEqual.equalTo(Card.RequireJorker));
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 12));
		player.takeCard(Card.New(Suit.Heart, 8));
		player.takeCard(Card.New(Suit.Club, 5));
		assertThat(player.openCard(turn, viewer, declaration), IsEqual.equalTo(Card.New(Suit.Club, 5)));
	}

	@Test
	public void T03_��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��͂Ȃ�ł������̂�1���J�[�h���o������() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
			}
		};
		
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		player.openCard(turn, viewer, declaration);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T04_�Ƃ肠�����N���錾���ĂȂ�������N���u��13�Ő錾����(){
		Player player = Player.New("hoge");
		assertThat(player.AskForDeclare(null, viewer), IsEqual.equalTo(Declaration.New(Suit.Club, 13)));
	}
}
