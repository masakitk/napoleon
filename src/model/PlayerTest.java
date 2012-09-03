package model;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void T01_1���J�[�h���o�����炻�̃J�[�h�͎茳����Ȃ��Ȃ邱��(){
		Player player = new Player();
		player.takeCard(new Card(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		@SuppressWarnings("serial")
		List<Card> cardsOfTurn = new ArrayList<Card>(){{add(new Card(Suit.Heart, 2));}};
		player.openCard(Turn.New(cardsOfTurn, 1));
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_��D���ݒ肳��Ă��đ�D������Ƃ��͑�D���o������() {
//		new Expectations() {
//			{
//				Turn.New(new ArrayList<Card>(), 0); returns(turnMock); 
//			}
//		};
		
		Player player = new Player();
		player.takeCard(new Card(Suit.Spade, 3));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(new Card(Suit.Spade, 1));}}, 1);
		assertThat(player.openCard(turn).getMark(), IsEqual.equalTo(Suit.Spade));
	}

	@Test
	public void T03_��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��͂Ȃ�ł������̂�1���J�[�h���o������() {
		Player player = new Player();
		player.takeCard(new Card(Suit.Spade, 3));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(new Card(Suit.Heart, 1));}}, 1);
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
}
