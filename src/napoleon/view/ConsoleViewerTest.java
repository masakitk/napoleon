package napoleon.view;

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

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;

public class ConsoleViewerTest {

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
	public void T_テーブルに残ったカードを交換する際もらった枚数返さないとだめ() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4");
				table.getCards(); returns(テーブル残カード);
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4,C5,S1");
				table.getCards(); returns(テーブル残カード);
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
			}
		};
		
		final Viewer viewer = ConsoleViewer.GetInstance();
		assertThat(viewer.inputCardsToChange(declaration, table, 手札), Is.is(Matchers.containsInAnyOrder(
				new Card[]{
					Card.New(Suit.Club, 3),
					Card.New(Suit.Club, 4),
					Card.New(Suit.Club, 5),
				})));
	}

	@Test
	public void T_テーブルに残ったカードを交換する際手持ちにないカードはだめ() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,H2,H1");
				table.getCards(); returns(テーブル残カード);
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
			}
		};
		
		final Viewer viewer = ConsoleViewer.GetInstance();
		assertThat(viewer.inputCardsToChange(declaration, table, 手札), Is.is(Matchers.containsInAnyOrder(
				new Card[]{
					Card.New(Suit.Club, 3),
					Card.New(Suit.Club, 4),
					Card.New(Suit.Club, 5),
				})));
	}

	@Test
	public void Tテーブルに残ったカードを交換する際カード以外の入力はNG() {
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,H2,Joker");
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4,Jorker");
				table.getCards(); returns(テーブル残カード);
			}
		};
		
		final Viewer viewer = ConsoleViewer.GetInstance();
		assertThat(viewer.inputCardsToChange(declaration, table, 手札), Is.is(Matchers.containsInAnyOrder(
				new Card[]{
					Card.New(Suit.Club, 3),
					Card.New(Suit.Club, 4),
					Card.Jorker,
				})));
	}
}
