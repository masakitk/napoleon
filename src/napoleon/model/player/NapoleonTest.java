package napoleon.model.player;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;

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
	
	@Test
	public void T02_�����w��}�C�e�B() {
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 1));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.Mighty));
	}
	
	@Test
	public void T03_�}�C�e�B������Ε����w��͐�J() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(GameContext.getRightBower(Suit.Club)));
	}

	@Test
	public void T04_�}�C�e�B��J������Ε����w��͗�J() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(GameContext.getLeftBower(Suit.Club)));
	}

	@Test
	public void T05_�}�C�e�B��J��J������Ε����w����߂�() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.Yoromeki));
	}

	@Test
	public void T06_�}�C�e�B��J��J���߂�������Ε����w�肤��Z�C���Q() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		player.takeCard(Card.Yoromeki);
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.New(Suit.Spade, 2)));
	}
	
	@Test
	public void T07_�}�C�e�B��J��J���߂����Z�C���Q�����ăW���[�J�[����Ε����w��W���[�J�[����() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		player.takeCard(Card.Yoromeki);
		player.takeCard(Card.New(Suit.Spade, 2));
		player.takeCard(Card.Jorker);
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.RequireJorker));
	}

	@Test
	public void T08_�}�C�e�B��J��J���߂����Z�C���Q�����ăW���[�J�[�Ȃ���Ε����w��؂�DA() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		player.takeCard(Card.Yoromeki);
		player.takeCard(Card.New(Suit.Spade, 2));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.New(Suit.Club, 1)));
	}

	@Test
	public void T09_�}�C�e�B��J��J���߂����Z�C���Q�؂�DA�����ăW���[�J�[�Ȃ���Ε����w��؂�D13() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		player.takeCard(Card.Yoromeki);
		player.takeCard(Card.New(Suit.Spade, 2));
		player.takeCard(Card.New(Suit.Club, 1));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.New(Suit.Club, 13)));
	}

}
