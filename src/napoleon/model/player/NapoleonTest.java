package napoleon.model.player;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class NapoleonTest {

	@Test
	public void T01_切り札で自分が持っていない特殊カード考慮しないで一番強いカードを返す() {
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 1));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.SomeTrumpCard(declaration), equalTo(Card.New(Suit.Club, 13)));

		napoleon.takeCard(Card.New(Suit.Club, 13));
		napoleon.takeCard(Card.New(Suit.Club, 11));
		assertThat(napoleon.SomeTrumpCard(declaration), equalTo(Card.New(Suit.Club, 12)));
	}
	
	@Test
	public void T02_副官指定マイティ() {
		Player player = Player.New("hoge");
		player.takeCard(Card.New(Suit.Club, 1));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.Mighty));
	}
	
	@Test
	public void T03_副官指定正J() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(GameContext.getRightBower(Suit.Club)));
	}

	@Test
	public void T04_副官指定裏J() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(GameContext.getLeftBower(Suit.Club)));
	}

	@Test
	public void T05_副官指定よろめき() {
		Player player = Player.New("hoge");
		player.takeCard(Card.Mighty);
		player.takeCard(GameContext.getRightBower(Suit.Club));
		player.takeCard(GameContext.getLeftBower(Suit.Club));
		Napoleon napoleon = Napoleon.New(player);
		
		Declaration declaration = Declaration.New(Suit.Club, 14);
		assertThat(napoleon.tellTheAdjutant(declaration), equalTo(Card.Yoromeki));
	}
}
