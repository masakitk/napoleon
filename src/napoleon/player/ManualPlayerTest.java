package napoleon.player;

import static org.hamcrest.CoreMatchers.*;
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
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Turn;
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
	public void T01_�W�����͂�����͂��󂯕t���邱��(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("D3");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(player.inputCard(viewer, turn), IsEqual.equalTo(Card.New(Suit.Dia, 3)));
	}

	@Test
	public void T01a_�X�[�g���͕s���͂�������(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("A3"); 
				viewer.showMessage("1�����ڂ�S,H,D,C�̂����ꂩ�ɂ��ĉ������B");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(player.inputCard(viewer, turn), IsNull.nullValue());
	}

	@Test
	public void T01a_Jorker�͏o����(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("Jorker"); 
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.Jorker);
		
		assertThat(player.inputCard(viewer, turn), equalTo(Card.Jorker));
	}

	@Test
	public void T01b_���l���͕s���͂�������(){
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("H0"); 
				viewer.showMessage("������1����13�͈̔͂ɂ���܂���B");
				
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("H14"); 
				viewer.showMessage("������1����13�͈̔͂ɂ���܂���B");
				
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("Habc"); 
				viewer.showMessage("2�����ڈȍ~��1�`13�̐�������͂��ĉ������B");
			}
		};
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(player.inputCard(viewer, turn), IsNull.nullValue());
		assertThat(player.inputCard(viewer, turn), IsNull.nullValue());
		assertThat(player.inputCard(viewer, turn), IsNull.nullValue());
	}

	@Test
	public void T02_�����ĂȂ��J�[�h���w�肵����ē���(){
		new Expectations() {
			{
				@SuppressWarnings("serial")
				final List<Card> anyCards = new ArrayList<Card>(){{add(Card.New(Suit.Heart, 12)); add(Card.New(Suit.Dia, 3)); }};
				turn.getCards(); returns(new ArrayList<Card>());
				turn.getTrump(); returns(Suit.Spade);
				viewer.showMessage("this turn opened [], trump is Spade");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("S3");
				viewer.showMessage("���̃J�[�h�͎����Ă��܂���B");

				turn.getCards(); returns(new ArrayList<Card>());
				turn.getTrump(); returns(Suit.Spade);
				viewer.showMessage("this turn opened [], trump is Spade");
				viewer.sortCardsToView(anyCards); returns(anyCards);
				viewer.showMessage("You have [[Heart:12], [Dia:3]]");
				new Scanner((BufferedInputStream)any); returns(any);
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
	public void T03_��D������ꍇ�ɑ�D���o���Ȃ���΂Ȃ�Ȃ�(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(false);
				turn.isLeadSuitDefined(); returns(true);
				turn.getLeadSuit(); returns(Suit.Dia);
				turn.getLeadSuit(); returns(Suit.Dia);
				viewer.showMessage("��D������ꍇ�́A��D�������Ȃ���΂Ȃ�܂���B");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(player.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer), IsNull.nullValue());
	}

	@Test
	public void T03_�؂�D�������ꂽ�ꍇ�؂�D���o���Ȃ���΂Ȃ�Ȃ�(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(true);
				turn.getTrump(); returns(Suit.Dia);
				viewer.showMessage("�؂�D�������ꂽ�ꍇ�́A�؂�D�������Ȃ���΂Ȃ�܂���B");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		
		assertThat(player.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer), IsNull.nullValue());
	}

	@Test
	public void T03_�W���[�J�[�������ꂽ�ꍇ�W���[�J�[���o���Ȃ���΂Ȃ�Ȃ�(){
		new Expectations() {
			{
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
				viewer.showMessage("�W���[�J�[�������ꂽ�ꍇ�́A�W���[�J�[�������Ȃ���΂Ȃ�܂���B");
				turn.isJorkerOpenedFirst(); returns(false);
				turn.isRequireJorkerOpenedFirst(); returns(true);
				viewer.showMessage("�W���[�J�[�������ꂽ�ꍇ�́A�W���[�J�[�������Ȃ���΂Ȃ�܂���B");
			}
		};
		
		final ManualPlayer player = ManualPlayer.New("hoge");
		player.takeCard(Card.New(Suit.Heart, 12));
		player.takeCard(Card.New(Suit.Dia, 3));
		player.takeCard(Card.Jorker);
		
		assertThat(player.rejectInvalidCard(Card.New(Suit.Heart, 12), turn, viewer), IsNull.nullValue());
		assertThat(player.rejectInvalidCard(Card.New(Suit.Dia, 3), turn, viewer), IsNull.nullValue());
	}
	
}
