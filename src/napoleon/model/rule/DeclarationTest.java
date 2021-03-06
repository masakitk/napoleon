package napoleon.model.rule;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import napoleon.model.card.Suit;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class DeclarationTest {

	private Declaration dec1;
	private Declaration dec2;
	private boolean result;
	
	public DeclarationTest (Declaration dec1, Declaration dec2, boolean resultOfStronger){
		this.dec1 = (Declaration)dec1;
		this.dec2 = (Declaration)dec2;
		this.result = (boolean)resultOfStronger;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   new Object[]{ Declaration.New(Suit.Club, 13), Declaration.New(Suit.Spade, 12), true }, 
			   new Object[]{ Declaration.New(Suit.Club, 13), Declaration.New(Suit.Club, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Club, 13), Declaration.New(Suit.Dia, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Club, 13), Declaration.New(Suit.Heart, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Club, 13), Declaration.New(Suit.Spade, 13), false }, 

			   new Object[]{ Declaration.New(Suit.Dia, 13), Declaration.New(Suit.Spade, 12), true }, 
			   new Object[]{ Declaration.New(Suit.Dia, 13), Declaration.New(Suit.Club, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Dia, 13), Declaration.New(Suit.Dia, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Dia, 13), Declaration.New(Suit.Heart, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Dia, 13), Declaration.New(Suit.Spade, 13), false }, 

			   new Object[]{ Declaration.New(Suit.Heart, 13), Declaration.New(Suit.Spade, 12), true }, 
			   new Object[]{ Declaration.New(Suit.Heart, 13), Declaration.New(Suit.Club, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Heart, 13), Declaration.New(Suit.Dia, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Heart, 13), Declaration.New(Suit.Heart, 13), false }, 
			   new Object[]{ Declaration.New(Suit.Heart, 13), Declaration.New(Suit.Spade, 13), false }, 

			   new Object[]{ Declaration.New(Suit.Spade, 13), Declaration.New(Suit.Spade, 12), true }, 
			   new Object[]{ Declaration.New(Suit.Spade, 13), Declaration.New(Suit.Club, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Spade, 13), Declaration.New(Suit.Dia, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Spade, 13), Declaration.New(Suit.Heart, 13), true }, 
			   new Object[]{ Declaration.New(Suit.Spade, 13), Declaration.New(Suit.Spade, 13), false }, 
	   };
	   return Arrays.asList(data);
	}
	@Test
	public void T01_宣言の強さ比較テスト() {
		assertThat(dec1.strongerThan(dec2), Is.is(result));
	}

}
