package napoleon.model.player;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
import napoleon.view.ConsoleViewer;
import napoleon.view.Viewer;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.Test;

public class ManualPlayerTest {

	@Mocked
	Scanner scanner;
	@Mocked
	Turn turn;
	@Mocked
	Viewer viewer;
	Declaration declaration = Declaration.New(Suit.Club, 13);

	@Test
	public void T01_標準入力からカード入力を受け付けること(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("S3");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), IsEqual.equalTo(Card.New(Suit.Spade, 3)));
	}

	@Test
	public void T01a_スート入力不備はおこられる(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("A3"); 
				viewer.showMessage("1文字目はS,H,D,Cのいずれかのスートにして下さい。（Jorkerの場合を除く）");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), IsNull.nullValue());
	}

	@Test
	public void T01a_Jorkerは出せる(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("Jorker"); 
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.Jorker);
		
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), equalTo(Card.Jorker));
	}

	@Test
	public void T01b_数値入力不備はおこられる(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("H0"); 
				viewer.showMessage("数字が1から13の範囲にありません。");
				
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("H14"); 
				viewer.showMessage("数字が1から13の範囲にありません。");
				
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("Habc"); 
				viewer.showMessage("2文字目以降は1～13の数字を入力して下さい。");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), IsNull.nullValue());
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), IsNull.nullValue());
		assertThat(ManualPlayerUtil.inputCard(viewer, turn), IsNull.nullValue());
	}

	@Test
	public void T02_持ってないカードを指定したら再入力(){
		new Expectations() {
			{
				@SuppressWarnings("serial")
				final List<Card> anyCards = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 12)); add(Card.New(Suit.Dia, 3)); }};
				turn.getCardHash(); returns(new LinkedHashMap<Player, Card>());
				viewer.showMessage("this turn opened [], declaration is Club:13");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[♥:12], [◆:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("S3");
				viewer.showMessage("そのカードは持っていません。");

				turn.getCardHash(); returns(new LinkedHashMap<Player, Card>());
				viewer.showMessage("this turn opened [], declaration is Club:13");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[♥:12], [◆:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input card(Ex. S1:♠A、H13:♥13 etc..");
				scanner.nextLine(); returns("D3");
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
			}
		};
		
		final Player player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		player.openCard(turn, viewer, declaration);
		assertThat(player.cardCount(), IsEqual.equalTo(1));
	}

	@Test
	public void T03_台札がある場合に台札を出さなければならない(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
				viewer.showMessage("台札がある場合は、台札をださなければなりません。");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer, player.cards), IsNull.nullValue());
	}

	@Test
	public void T03_台札がない場合はなんでもOK(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		final Card club3 = Card.New(Suit.Club, 3);
		player.takeCard(club3);
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(club3, turn, viewer, player.cards), equalTo(club3));
	}
	
	@Test
	public void T03_切り札請求された場合切り札を出さなければならない(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(true);
				turn.getTrump(); returns(Suit.Dia);
				viewer.showMessage("切り札請求された場合は、切り札をださなければなりません。");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer, player.cards), IsNull.nullValue());
	}

	@Test
	public void T03_切り札請求された場合切り札を出さなければならないけどないときはOK(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(true);
				turn.getTrump(); returns(Suit.Dia);
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		final Card heart13 = Card.New(Suit.Heart, 12);
		player.takeCard(heart13);
		player.takeCard(Card.New(Suit.Club, 3));
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(heart13, turn, viewer, player.cards), equalTo(heart13));
	}

	
	@Test
	public void T03_ジョーカー請求された場合ジョーカーを出さなければならない(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
				viewer.showMessage("ジョーカー請求された場合は、ジョーカーをださなければなりません。");
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
				viewer.showMessage("ジョーカー請求された場合は、ジョーカーをださなければなりません。");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		player.takeCard(Card.Jorker);
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer, player.cards), IsNull.nullValue());
		assertThat(ManualPlayerUtil.rejectInvalidCard(Card.New(Suit.Dia, 3), turn, viewer, player.cards), IsNull.nullValue());
	}

	@Test
	public void T03_ジョーカー請求された場合ジョーカーを出さなければならないがないときはOK(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		final Card heart12 = Card.New(Suit.Heart, 12);
		player.takeCard(heart12);
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(ManualPlayerUtil.rejectInvalidCard(heart12, turn, viewer, player.cards), equalTo(heart12));
	}
	
	@Test
	public void T11_宣言を標準入力から受け付けること() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("H13");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		assertThat(player.AskForDeclare(null, ConsoleViewer.GetInstance()),
				equalTo(Declaration.New(Suit.Heart, 13)));
		
	}

	@Test
	public void T12_宣言時にパスができること() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("pass");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		assertThat(player.AskForDeclare(null, ConsoleViewer.GetInstance()),
				equalTo(Declaration.Pass));
		
	}

}
