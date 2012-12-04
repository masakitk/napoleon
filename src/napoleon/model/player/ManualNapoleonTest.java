package napoleon.model.player;

import static org.junit.Assert.*;

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
				viewer.inputCardsToChange(declaration, table, 手札); 
				returns(new ArrayList<Card>(){{
					add(Card.New(Suit.Club, 3));
					add(Card.New(Suit.Club, 4));
					add(Card.New(Suit.Club, 5));}});
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
}
