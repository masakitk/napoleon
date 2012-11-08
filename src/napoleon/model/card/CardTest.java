package napoleon.model.card;

import static org.junit.Assert.*;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class CardTest {

	@Test
	public void カード作成時数字0はエラー() {
		try{
			Card.New(Suit.Heart, 0);
			fail("チェックできてない");
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), IsEqual.equalTo("数字が1から13の範囲にありません。"));
		}
	}

	@Test
	public void カード作成時数字14はエラー() {
		try{
			Card.New(Suit.Spade, 14);
			fail("チェックできてない");
		}catch(IllegalArgumentException e) {
			assertThat(e.getMessage(), IsEqual.equalTo("数字が1から13の範囲にありません。"));
		}
	}

}
