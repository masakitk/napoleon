package napoleon.model.role;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import java.util.List;

import napoleon.model.card.Card;
import napoleon.model.player.Player;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Table;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class DealerTest {
	
	private Table table;
	private Player[] players;
	private Dealer dealer;

	@Before
	public void SetUp(){
		prepare();
	}
	
	private void prepare() {
		table = Table.New();
		players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		dealer = Dealer.New(table, players);
	}
	
	@Test
	public void T01_ディーラーは53枚カードを持っている() {

		assertThat(dealer.cardCount(), equalTo(53));
	}
	
	@Test
	public void T02_配り終わると4人のプレーヤーが12枚ずつ保持し＿テーブルに5枚のこる() {
		dealer.Serve();
		
		assertThat(table.cardCount(), equalTo(5));
		assertThat(players[0].cardCount(), equalTo(12));
	}

	@Test
	public void T03_毎回違うカードがテーブルに5枚残る(){
		dealer.Serve();
		List<Card> first = table.getCards();
		prepare();
		dealer.Serve();
		List<Card> second = table.getCards();
		assertThat(first.containsAll(second), IsEqual.equalTo(false));
	}
}
