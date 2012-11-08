package napoleon.model.card;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class CardSortTest {

	private Card card1;
	private Card card2;
	private boolean result;
	
	public CardSortTest(Card card1, Card card2, boolean result) {
		super();
		this.card1 = card1;
		this.card2 = card2;
		this.result = result;
	}

	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   new Object[]{ Card.Jorker, Card.New(Suit.Club, 2), false }, 
			   new Object[]{ Card.New(Suit.Club, 2), Card.Jorker, true }, 
			   new Object[]{ Card.New(Suit.Club, 2), Card.New(Suit.Dia, 2), false }, 
			   new Object[]{ Card.New(Suit.Dia, 2), Card.New(Suit.Club, 2), true}, 
			   new Object[]{ Card.New(Suit.Club, 2), Card.New(Suit.Club, 3), false }, 
			   new Object[]{ Card.New(Suit.Club, 3), Card.New(Suit.Club, 2), true}, 
			   new Object[]{ Card.New(Suit.Club, 2), Card.New(Suit.Club, 1), false }, 
			   new Object[]{ Card.New(Suit.Club, 1), Card.New(Suit.Club, 2), true}, 
			   };
	   return Arrays.asList(data);
	}
	
	@Test
	public void マークと数字で並び替え確認() {
		assertThat(card1.isUpperOrderByMarkAndStrength(card2), Is.is(result));
	}

}
