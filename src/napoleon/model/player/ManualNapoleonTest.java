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

	@Test
	public void T01_テーブルに残ったカードをもらって不要な5枚を交換できること() {
		final List<Card> テーブル残カード = new ArrayList<Card>(){{
			add(Card.New(Suit.Spade, 1));
			add(Card.New(Suit.Club, 5));
			add(Card.New(Suit.Spade, 11));
		}};
		new Expectations() {
			{
				new Scanner((BufferedInputStream)any); returns(any);
				scanner.nextLine(); returns("C3,C4,C5");
				table.getCards(); returns(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};
		
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		List<Card> 手札 = new ArrayList<Card>(){{
			add(Card.New(Suit.Club, 3));
			add(Card.New(Suit.Club, 4));
			add(Card.New(Suit.Club, 11));
		}};
		
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
		}};
		assertThat(napoleon.cards, Is.is(Matchers.containsInAnyOrder(手に残すカード.toArray(new Card[0]))));
	}
}
