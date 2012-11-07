package napoleon.player;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Player;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class ManualPlayerTest {

	@Mocked
	Scanner scanner;
	@Mocked
	Turn turn;
	@Mocked
	Viewer viewer;

	@Test
	public void T01_�W�����͂�����͂��󂯕t���邱��(){
		new Expectations() {
			{
				@SuppressWarnings("serial")
				final List<Card> anyCards = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 12)); add(Card.New(Suit.Dia, 3)); }};
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("D3");
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
			}
		};
		final Player player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		player.openCard(turn, viewer);
		assertThat(player.cardCount(), IsEqual.equalTo(1));
	}
	
	@Test
	public void T02_�����ĂȂ��J�[�h���w�肵����G���[(){
		new Expectations() {
			{
				@SuppressWarnings("serial")
				final List<Card> anyCards = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 12)); add(Card.New(Suit.Dia, 3)); }};
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("S3");
				viewer.showMessage("���̃J�[�h�͎����Ă��܂���B");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("D3");
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
			}
		};
		
		final Player player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		player.openCard(turn, viewer);
		assertThat(player.cardCount(), IsEqual.equalTo(1));
	}

	@Test
	public void T03_��D������ꍇ�ɑ�D�ȊO���w�肵����G���[(){
		new Expectations() {
			{
				@SuppressWarnings("serial")
				final List<Card> anyCards = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 12)); add(Card.New(Suit.Dia, 3)); }};
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("H12");
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
				viewer.showMessage("��D������ꍇ�́A��D�������Ȃ���΂Ȃ�܂���B");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("D3");
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
			}
		};
		
		final Player player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		player.openCard(turn, viewer);
		assertThat(player.cardCount(), IsEqual.equalTo(1));
	}
	
}
