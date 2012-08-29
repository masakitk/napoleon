package model;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void testインスタンス生成時ターンが1より小さい場合はエラー() {
		Turn.New(new ArrayList<Card>(), 0);
	}

	@Test
	public void testインスタンス生成時ターンが12より大きい場合はエラー() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("ターン番号は1以上12以下である必要があります");
		Turn.New(new ArrayList<Card>(), 13);
	}
}
