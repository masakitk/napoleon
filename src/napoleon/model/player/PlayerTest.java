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
	public void T01_1枚カードを出したらそのカードは手元からなくなること(){
		final Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		assertThat(player.cardCount(), IsEqual.equalTo(1));
		
		player.openCard(turn, viewer, declaration);
		assertThat(player.cardCount(), IsEqual.equalTo(0));
	}
	
	@Test
	public void T02_台札が設定されていて台札があるときは台札を出すこと() {
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
	public void T02_台札が設定されていて台札がないときでもなにか1枚だすこと() {
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
	public void T02_最初にジョーカー出されたら切り札を出すこと() {
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
	public void T02_最初にジョーカー出されたら切り札がなければ一番大きい絵札を出すこと() {
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
	public void T02_最初にジョーカー請求出されたらジョーカーを出すこと() {
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(false);
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);

				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(any);
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
			}
		};
		Player player1 = Player.New("fuga");
		player1.takeCard(Card.New(Suit.Club, 3));
		Card jorker = player1.openCard(turn, viewer, declaration);
		assertThat(jorker, IsEqual.equalTo(Card.New(Suit.Club, 3)));
		
		Player player2 = Player.New("hoge");
		player2.takeCard(Card.New(Suit.Club, 1));
		player2.openCard(turn, viewer, declaration);

		Player player3 = Player.New("piyo");
		player3.takeCard(Card.Jorker);
		player3.takeCard(Card.New(Suit.Spade, 2));
		player3.takeCard(Card.New(Suit.Club, 2));
		
		assertThat(player3.openCard(turn, viewer, declaration), IsEqual.equalTo(Card.Jorker));
	}

	@Test
	public void T02_最初にジョーカー請求出されたらジョーカーがない場合は普通に台札を出すこと() {
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
	public void T03_台札が設定されていて台札がないときはなんでもいいので1枚カードを出すこと() {
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
	public void T04_とりあえず誰も宣言してなかったらクラブの13で宣言する(){
		Player player = Player.New("hoge");
		assertThat(player.AskForDeclare(null, viewer), IsEqual.equalTo(Declaration.New(Suit.Club, 13)));
	}
}
