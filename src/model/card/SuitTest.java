package model.card;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class SuitTest {

	private Suit suit1;
	private Suit suit2;
	private boolean result;
	
	public SuitTest (Suit suit1, Suit suit2, boolean resultOfStronger){
		this.suit1 = (Suit)suit1;
		this.suit2 = (Suit)suit2;
		this.result = (boolean)resultOfStronger;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   new Object[]{ Suit.Spade, Suit.Spade, false }, 
			   new Object[]{ Suit.Spade, Suit.Heart, true }, 
			   new Object[]{ Suit.Spade, Suit.Dia, true }, 
			   new Object[]{ Suit.Spade, Suit.Club, true }, 
			   new Object[]{ Suit.Heart, Suit.Spade, false}, 
			   new Object[]{ Suit.Heart, Suit.Heart, false}, 
			   new Object[]{ Suit.Heart, Suit.Dia, true}, 
			   new Object[]{ Suit.Heart, Suit.Club, true}, 
			   new Object[]{ Suit.Dia, Suit.Spade, false}, 
			   new Object[]{ Suit.Dia, Suit.Heart, false}, 
			   new Object[]{ Suit.Dia, Suit.Dia, false}, 
			   new Object[]{ Suit.Dia, Suit.Club, true}, 
			   new Object[]{ Suit.Club, Suit.Spade, false}, 
			   new Object[]{ Suit.Club, Suit.Heart, false}, 
			   new Object[]{ Suit.Club, Suit.Dia, false}, 
			   new Object[]{ Suit.Club, Suit.Club, false}, 
			   };
	   return Arrays.asList(data);
	}	
	
	@Test
	public void T01_マークの強さ比較テスト() {
		System.out.println(String.format("left:{%s}, right:{%s}", suit1, suit2));
		assertThat(suit1.strongerThan(suit2), Is.is(result));
	}

}
