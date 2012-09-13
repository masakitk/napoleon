package model;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void T01_1���J�[�h���o�����炻�̃J�[�h�͎茳����Ȃ��Ȃ邱��(){
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		@SuppressWarnings("serial")
		List<Card> cardsOfTurn = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 2));}};
		player.openCard(Turn.New(cardsOfTurn, 1));
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_��D���ݒ肳��Ă��đ�D������Ƃ��͑�D���o������() {
	
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(Card.New(Suit.Spade, 1));}}, 1);
		assertThat(player.openCard(turn).getSuit(), IsEqual.equalTo(Suit.Spade));
	}

	@Test
	public void T03_��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��͂Ȃ�ł������̂�1���J�[�h���o������() {
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Spade, 3));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(Card.New(Suit.Heart, 1));}}, 1);
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T04_�Ƃ肠�����N���錾���ĂȂ�������N���u��13�Ő錾����(){
		Player player = Player.New("hoge");
		assertThat(player.AskForDeclare(null), IsEqual.equalTo(Declaration.New(Suit.Club, 13)));
	}
}
