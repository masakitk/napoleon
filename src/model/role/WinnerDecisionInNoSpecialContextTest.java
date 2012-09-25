package model.role;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import model.card.Card;
import model.card.Suit;
import model.player.Player;
import model.rule.Turn;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class WinnerDecisionInNoSpecialContextTest {

	static Player player1 = Player.New("p1");
	static Player player2 = Player.New("p2");
	static Player player3 = Player.New("p3");
	static Player player4 = Player.New("p4");

	private Map.Entry<Player, Card> opened1;
	private Map.Entry<Player, Card> opened2;
	private Map.Entry<Player, Card> opened3;
	private Map.Entry<Player, Card> opened4;
	private Card winnerCard;
	
	public WinnerDecisionInNoSpecialContextTest (
			Map.Entry<Player, Card> opened1, 
			Map.Entry<Player, Card> opened2, 
			Map.Entry<Player, Card> opened3, 
			Map.Entry<Player, Card> opened4, 
			Card winner){
		this.opened1 = (Map.Entry<Player, Card>)opened1;
		this.opened2 = (Map.Entry<Player, Card>)opened2;
		this.opened3 = (Map.Entry<Player, Card>)opened3;
		this.opened4 = (Map.Entry<Player, Card>)opened4;
		this.winnerCard = winner;
	}
	
	@Parameters
	public static Collection<Object[]> data() {
	   Object[][] data = new Object[][] { 
			   new Object[]{ 
					   new AbstractMap.SimpleEntry<Player, Card>(player1, Card.New(Suit.Spade, 2)),
					   new AbstractMap.SimpleEntry<Player, Card>(player2, Card.New(Suit.Spade, 5)),
					   new AbstractMap.SimpleEntry<Player, Card>(player3, Card.New(Suit.Spade, 4)),
					   new AbstractMap.SimpleEntry<Player, Card>(player4, Card.New(Suit.Spade, 3)),
					   Card.New(Suit.Spade, 5)
			   }, 
	   };		
	   return Arrays.asList(data);
	}
	
	@Test
	public void T01_ê‚ÌŸÒ”»’fƒeƒXƒg() {
		System.out.println(String.format("1st:{%s}, 2nd:{%s}, 3rd:{%s}, 4th:{%s}", opened1, opened2, opened3, opened4));
		Turn turn = Turn.New(1);
		turn.addCard(opened1.getKey(), opened1.getValue());
		turn.addCard(opened2.getKey(), opened2.getValue());
		turn.addCard(opened3.getKey(), opened3.getValue());
		turn.addCard(opened4.getKey(), opened4.getValue());
		
		assertThat(turn.getWinnerCard(), Is.is(winnerCard));
	}
	
}
