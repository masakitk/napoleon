package napoleon.model.player;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Table;
import napoleon.view.Viewer;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ManualNapoleonTest {

    @Mocked
	Viewer viewer;
	Declaration declaration = Declaration.New(Suit.Club, 13);
	@Mocked
	Table table;

	
	@Test
	public void T01_副官指定できること() {
		new Expectations() {
			{
				viewer.inputCardToAdjutant(); returns(Card.Yoromeki);
				viewer.inputCardToAdjutant(); returns(Card.New(Suit.Dia, 3));
				viewer.inputCardToAdjutant(); returns(Card.Joker);
			}
		};
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
		assertThat(napoleon.tellTheAdjutant(declaration, viewer), IsEqual.equalTo(Card.Yoromeki));
		assertThat(napoleon.tellTheAdjutant(declaration, viewer), IsEqual.equalTo(Card.New(Suit.Dia, 3)));
		assertThat(napoleon.tellTheAdjutant(declaration, viewer), IsEqual.equalTo(Card.Joker));
	}
	
	final List<Card> テーブル残カード = new ArrayList<Card>(){{
		add(Card.New(Suit.Spade, 1));
		add(Card.New(Suit.Club, 5));
		add(Card.New(Suit.Spade, 11));
	}};
	
	List<Card> 手札 = new ArrayList<Card>(){{
		add(Card.New(Suit.Club, 3));
		add(Card.New(Suit.Club, 4));
		add(Card.New(Suit.Club, 11));
		add(Card.Joker);
	}};
	
	@Test
	public void T02_テーブルに残ったカードをもらって不要な5枚を交換できること() {
		final ManualNapoleon napoleon = ManualNapoleon.New(ManualPlayer.New("hoge"));
        final List<Card> 不要カード = new ArrayList<Card>(){{
            add(Card.New(Suit.Club, 3));
            add(Card.New(Suit.Club, 4));
            add(Card.New(Suit.Club, 5));
        }};
        new Expectations() {
			{
				viewer.printPlayerHavingCards(napoleon);
				viewer.inputCardsToChange(declaration, table, 手札);
				returns(不要カード);
				table.getCards(); returns(テーブル残カード);
				table.removeCards(テーブル残カード);
				table.getNoUseCards();returns(new ArrayList<Card>());
			}
		};

        napoleon.takeCards(手札);
        napoleon.changeExtraCards(declaration, table, viewer);

		List<Card> 手に残すカード = new ArrayList<Card>(){{
			add(Card.New(Suit.Spade, 1));
			add(Card.New(Suit.Club, 11));
			add(Card.New(Suit.Spade, 11));
			add(Card.Joker);
		}};
		assertThat(napoleon.cards, Is.is(Matchers.containsInAnyOrder(手に残すカード.toArray(new Card[手に残すカード.size()]))));
	}

    @Test
    public void T03_副官GOの指示ができること(){
        new Expectations() {
            {
                viewer.getInputString("カードを入力して下さい(Ex. S1:♠A、H13:♥13 etc...)"); returns("GO");
                viewer.showMessage("副官GO!!");
                viewer.getInputString("カードを入力して下さい(Ex. S1:♠A、H13:♥13 etc...)"); returns("S3");
            }
        };
        GameContext.Init(Suit.Heart);

        Card card = ManualPlayerUtil.inputCard(viewer, true);
        assertThat(card, IsEqual.equalTo(Card.New(Suit.Spade, 3)));
        assertTrue(GameContext.getCurrent().hasCalledToGoAdjutant());

    }
}
