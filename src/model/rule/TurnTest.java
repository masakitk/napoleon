package model.rule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import model.card.Card;
import model.card.Suit;
import model.player.Player;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;
	@Tested Turn turn = Turn.New(1);
	

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_インスタンス生成時ターンが1より小さい場合はエラー() {
		Turn.New(0);
	}

	@Test
	public void T02_インスタンス生成時ターンが12より大きい場合はエラー() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("ターン番号は1以上12以下である必要があります");
		Turn.New(13);
	}
	
	@Test
	public void T03_最初に出されたスートが台札になること() {
		Turn turn = Turn.New(1);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
	
	@Test
	public void T04_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる() {
		
		final Parameters param = new Parameters(
				Suit.Club,
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 4), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Dia, 12),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Heart, 13)});
		
		ターンを回して勝者を確認(param);
		
		new Verifications(){
			{
				Collection<Card> cardToTake;
				player4.takeCards(cardToTake = withCapture());
				assertTrue(CollectionUtils.isEqualCollection(cardToTake, Arrays.asList(param.winnerWillGet)));
			}
		};
	}

	@Test
	public void T05_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_セイム2の無視() {
		Parameters param = new Parameters(
				Suit.Club,
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player1, Card.New(Suit.Dia, 13),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T06_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_よろめきの無視() {
		Parameters param = new Parameters(
				Suit.Club,
				player2, Card.New(Suit.Spade, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Spade, 1),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Spade, 1), Card.New(Suit.Heart, 12)});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T07_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_マイティの無視() {
		Parameters param = new Parameters(
				Suit.Club,
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Spade, 1), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Heart, 1),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Spade, 1),});
		
		ターンを回して勝者を確認(param);
	}

	private void ターンを回して勝者を確認(Parameters param) {
		turn.addCard(param.player1, param.card1);
		turn.addCard(param.player2, param.card2);
		turn.addCard(param.player3, param.card3);
		turn.addCard(param.player4, param.card4);
		turn.close();
		
		assertThat(turn.getWinnerCard(), equalTo(param.winnerCard));
		assertThat(turn.getWinner(), equalTo(param.winner));
	}

	class Parameters {
		public Parameters(Suit trunp, Player player1, Card card1, Player player2,
				Card card2, Player player3, Card card3, Player player4,
				Card card4, Player winner, Card winnerCard, Card[] winnerWillGet) {
			super();
			this.trump = trunp;
			this.player1 = player1;
			this.card1 = card1;
			this.player2 = player2;
			this.card2 = card2;
			this.player3 = player3;
			this.card3 = card3;
			this.player4 = player4;
			this.card4 = card4;
			this.winner = winner;
			this.winnerCard = winnerCard;
			this.winnerWillGet = winnerWillGet;
		}
		Suit trump;
		Player player1;
		Card card1;
		Player player2;
		Card card2;
		Player player3;
		Card card3;
		Player player4;
		Card card4;
		Player winner;
		Card winnerCard;
		Card[] winnerWillGet;
	}
	
}

