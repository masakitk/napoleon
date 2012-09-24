package model.rule;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Mocked;
import model.card.Card;
import model.card.Suit;
import model.player.Player;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class TurnTest {
	@Mocked Player player1;

	@Rule 
	public ExpectedException exception = ExpectedException.none();
	
	@Test(expected = IllegalArgumentException.class)
	public void T01_インスタンス生成時ターンが1より小さい場合はエラー() {
		Turn.New(0);
	}

	@Test
	public void T02_インスタンス生成時ターンが12より大きい場合はエラー() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("ターン番号は1以上12以下である必要があります");
		Turn.New(13);
	}
	
	@Test
	public void T03_最初に出されたスートが台札になること() {
		Turn turn = Turn.New(1);
		assertThat(turn.getLeadSuit(), equalTo(null));	
		turn.addCard(player1, Card.New(Suit.Spade, 2));
		assertThat(turn.getLeadSuit(), equalTo(Suit.Spade));	
	}
	
	@Test
	public void T04_カードが4枚出されている場合に特殊カード考慮抜きで勝者を判断できる() {
		Turn turn = Turn.New(1);
		throw new NotImplementedException();
	}
	
}

