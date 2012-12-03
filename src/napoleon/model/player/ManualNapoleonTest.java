package napoleon.model.player;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.view.Viewer;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;

public class ManualNapoleonTest {

	@Mocked
	Scanner scanner;
	@Mocked
	Turn turn;
	@Mocked
	Viewer viewer;
	Declaration declaration = Declaration.New(Suit.Club, 13);
	@Mocked
	Table table;

	final List<Card> テーブル残カード = new ArrayList<Card>(){{
		add(Card.New(Suit.Spade, 1));
		add(Card.New(Suit.Club, 5));
		add(Card.New(Suit.Spade, 11));
	}};
	
	List<Card> 手札 = new ArrayList<Card>(){{
		add(Card.New(Suit.Club, 3));
		add(Card.New(Suit.Club, 4));
		add(Card.New(Suit.Club, 11));
		add(Card.Jorker);
	}};
	
	@Test
	public void T01_テーブルに残ったカードをもらって不要な5枚を交換できること() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
				table.removeCards(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};
		
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		
		napoleon.takeCards(手札);
		napoleon.changeExtraCards(declaration, table, viewer);
		
		List<Card> 不要カード = new ArrayList<Card>(){{
			add(Card.New(Suit.Club, 3));
			add(Card.New(Suit.Club, 4));
			add(Card.New(Suit.Club, 5));
		}};
		List<Card> 手に残すカード = new ArrayList<Card>(){{
			add(Card.New(Suit.Spade, 1));
			add(Card.New(Suit.Club, 11));
			add(Card.New(Suit.Spade, 11));
			add(Card.Jorker);
		}};
		assertThat(napoleon.cards, Is.is(Matchers.containsInAnyOrder(手に残すカード.toArray(new Card[0]))));
	}
	
	@Test
	public void T02_テーブルに残ったカードを交換する際もらった枚数返さないとだめ() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4");
				table.getCards(); returns(テーブル残カード);
				viewer.showMessage("select 3 unuse cards");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4,C5,S1");
				table.getCards(); returns(テーブル残カード);
				viewer.showMessage("select 3 unuse cards");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
				table.removeCards(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};
		
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		napoleon.takeCards(手札);
		napoleon.changeExtraCards(declaration, table, viewer);
	}

	@Test
	public void T03_テーブルに残ったカードを交換する際手持ちにないカードはだめ() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [H3,C4,C5...]");
				scanner.nextLine(); returns("C3,H2,H1");
				table.getCards(); returns(テーブル残カード);
				viewer.showMessage("you don't have [[♥:2], [♥:1]]");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
				table.removeCards(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};
		
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		napoleon.takeCards(手札);
		napoleon.changeExtraCards(declaration, table, viewer);
	}

	@Test
	public void T03_テーブルに残ったカードを交換する際カード以外の入力はNG() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [H3,C4,C5...]");
				scanner.nextLine(); returns("C3,H2,Joker");
				new Scanner((BufferedInputStream)any); returns(any);
				viewer.showMessage("input cards to unuse, as [C3,C4,C5...]");
				scanner.nextLine(); returns("C3,C4,Jorker");
				table.getCards(); returns(テーブル残カード);
				table.removeCards(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};
		
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		napoleon.takeCards(手札);
		napoleon.changeExtraCards(declaration, table, viewer);
	}
}
