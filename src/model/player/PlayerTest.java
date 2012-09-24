package model.player;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import model.card.Card;
import model.card.Suit;
import model.rule.Declaration;
import model.rule.Turn;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class PlayerTest {

	@Mocked
	Turn turn;
	
	@Test
	public void T01_1枚カードを出したらそのカードは手元からなくなること(){
		final Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		player.openCard(turn);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_台札が設定されていて台札があるときは台札を出すこと() {
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
	public void T03_台札が設定されていて台札がないときはなんでもいいので1枚カードを出すこと() {
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
	public void T04_とりあえず誰も宣言してなかったらクラブの13で宣言する(){
		Player player = Player.New("hoge");
		assertThat(player.AskForDeclare(null), IsEqual.equalTo(Declaration.New(Suit.Club, 13)));
	}
}
