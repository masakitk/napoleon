package napoleon.model.rule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;

import org.apache.commons.collections15.CollectionUtils;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;
	@Tested Turn turn = Turn.New(1, Suit.Spade);
	Card[] parameterOfCardsToTake;

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_インスタンス生成時ターンが1より小さい場合はエラー() {
		Turn.New(0, Suit.Spade);
	}

	@Test
	public void T02_インスタンス生成時ターンが12より大きい場合はエラー() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("ターン番号は1以上12以下である必要があります");
		Turn.New(13, Suit.Spade);
	}
	
	@Test
	public void T03_最初に出されたスートが台札になること() {
		Turn turn = Turn.New(1, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
	
	@Test
	public void T03_最初にジョーカーが出されたら切り札が台札になること() {
		Turn turn = Turn.New(2, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.Jorker);
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}

	@Test
	public void T03_特殊カード無効のターンで最初にジョーカーが出されたら台札未定で2人目にまわること() {
		Turn turn = Turn.New(1, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.Jorker);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player2, Card.New(Suit.Dia, 5));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Dia));	
	}
	
	@Test
	public void T03_最初にジョーカー請求が出されたことを判定できること() {
		Turn turn = Turn.New(2, Suit.Spade);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Club, 3));
		assertThat(turn.isRequireJorkerOpenedFirst(), equalTo(true));
	}
	
	@Test
	public void T04_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる() {
		
		final Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 4), 
				player4, Card.New(Suit.Dia, 1), 
				player1, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Dia, 1),
				new Card[]{Card.New(Suit.Dia, 1), Card.New(Suit.Heart, 13)});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T04_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる() {
		
		final Parameters param = new Parameters(
				Turn.New(2, Suit.Dia),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 4), 
				player4, Card.New(Suit.Dia, 1), 
				player1, Card.New(Suit.Dia, 9), 
				player4, Card.New(Suit.Dia, 1),
				new Card[]{Card.New(Suit.Dia, 1)});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T05_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_セイム2の無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player1, Card.New(Suit.Dia, 13),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T05_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_セイム2() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Dia, 2),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T05_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_セイム2無効_別のスートあり() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Dia, 8), 
				player3, Card.New(Suit.Dia, 2), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Dia, 12),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Heart, 13)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T06_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_よろめきの無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Spade, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Spade, 1),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Spade, 1), Card.New(Suit.Heart, 12)});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T06_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_よろめき() {
		Parameters param = new Parameters(
				Turn.New(11, Suit.Spade),
				player2, Card.New(Suit.Spade, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Dia, 12), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Heart, 12),
				new Card[]{Card.New(Suit.Dia, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Spade, 1), Card.New(Suit.Heart, 12)});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T07_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_マイティの無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Spade),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Spade, 1), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Heart, 1),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Spade, 1),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T07_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_マイティ() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Spade),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 13), 
				player4, Card.New(Suit.Spade, 1), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 1),
				new Card[]{Card.New(Suit.Heart, 13), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Spade, 1),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T08_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_切り札の無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Club, 3), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Heart, 1),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T08_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_切り札() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Heart, 1), 
				player3, Card.New(Suit.Heart, 12), 
				player4, Card.New(Suit.Club, 3), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 3),
				new Card[]{Card.New(Suit.Heart, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1)});
		
		ターンを回して勝者を確認(param);
	}
	
	@Test
	public void T09_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_裏Jの無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.New(Suit.Club, 12), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Club, 1),
				new Card[]{Card.New(Suit.Club, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T09_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_裏J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.New(Suit.Club, 12), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 11),
				new Card[]{Card.New(Suit.Club, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T10_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_正Jの無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.New(Suit.Spade, 12), 
				player3, Card.New(Suit.Heart, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.New(Suit.Spade, 12),
				new Card[]{Card.New(Suit.Spade, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Club, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T10_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_正J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Spade, 12), 
				player3, Card.New(Suit.Heart, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 11),
				new Card[]{Card.New(Suit.Spade, 12), Card.New(Suit.Dia, 13), Card.New(Suit.Heart, 1), Card.New(Suit.Club, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる_Jorkerの無視() {
		Parameters param = new Parameters(
				Turn.New(1, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player3, Card.New(Suit.Club, 1),
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Club, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_最初にJorker() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player2, Card.Jorker,
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_JorkerVS正J() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.New(Suit.Club, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Club, 11), 
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Club, 11),});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_JorkerVSマイティ() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.Mighty, 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.Mighty, 
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.Mighty,});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_JorkerVSよろめき() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.Jorker, 
				player3, Card.New(Suit.Club, 1), 
				player4, Card.Mighty, 
				player1, Card.Yoromeki, 
				player1, Card.Yoromeki, 
				new Card[]{Card.Yoromeki, Card.New(Suit.Club, 1), Card.Mighty,});
		
		ターンを回して勝者を確認(param);
	}

	@Test
	public void T11_カードが4枚出されている場合に特殊カード考慮ありで勝者を判断できる_途中にJorker() {
		Parameters param = new Parameters(
				Turn.New(2, Suit.Club),
				player2, Card.New(Suit.Club, 1), 
				player3, Card.Jorker, 
				player4, Card.New(Suit.Spade, 11), 
				player1, Card.New(Suit.Dia, 13), 
				player4, Card.New(Suit.Spade, 11),
				new Card[]{Card.New(Suit.Dia, 13), Card.New(Suit.Club, 1), Card.New(Suit.Spade, 11),});
		
		ターンを回して勝者を確認(param);
	}
	
	private void ターンを回して勝者を確認(Parameters param) {
		turn = param.turn;
		turn.addCard(param.player1, param.card1);
		turn.addCard(param.player2, param.card2);
		turn.addCard(param.player3, param.card3);
		turn.addCard(param.player4, param.card4);
		turn.winnerGainCards();
		
		assertThat(turn.getWinnerCard(), equalTo(param.winnerCard));
		assertThat(turn.getWinner(), equalTo(param.winner));
		parameterOfCardsToTake = param.winnerWillGet;
		new Verifications(){
			{
				Collection<Card> cardToTake;
				turn.getWinner().takeCards(cardToTake = withCapture());
				assertTrue(CollectionUtils.isEqualCollection(cardToTake, Arrays.asList(parameterOfCardsToTake)));
			}
		};
	}

	class Parameters {
		public Parameters(Turn turn, Player player1, Card card1, Player player2,
				Card card2, Player player3, Card card3, Player player4,
				Card card4, Player winner, Card winnerCard, Card[] winnerWillGet) {
			super();
			this.turn = turn;
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
		Turn turn;
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

