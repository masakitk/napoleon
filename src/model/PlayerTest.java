package model;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PlayerTest {

	@Test
	public void test1���J�[�h���o�����炻�̃J�[�h�͎茳����Ȃ��Ȃ邱��(){
		Player player = new Player();
		player.takeCard(new Card(Marks.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		@SuppressWarnings("serial")
		List<Card> cardsOfTurn = new ArrayList<Card>(){{add(new Card(Marks.Heart, 2));}};
		player.openCard(Turn.New(cardsOfTurn, 1));
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void test��D���ݒ肳��Ă��đ�D������Ƃ��͑�D���o������() {
//		new Expectations() {
//			{
//				Turn.New(new ArrayList<Card>(), 0); returns(turnMock); 
//			}
//		};
		
		Player player = new Player();
		player.takeCard(new Card(Marks.Spade, 3));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(new Card(Marks.Spade, 1));}}, 1);
		assertThat(player.openCard(turn).getMark(), IsEqual.equalTo(Marks.Spade));
	}

	@Test
	public void test��D���ݒ肳��Ă��đ�D���Ȃ��Ƃ��͂Ȃ�ł������̂�1���J�[�h���o������() {
		Player player = new Player();
		player.takeCard(new Card(Marks.Spade, 3));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		@SuppressWarnings("serial")
		Turn turn = Turn.New(new ArrayList<Card>(){{add(new Card(Marks.Heart, 1));}}, 1);
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
}
