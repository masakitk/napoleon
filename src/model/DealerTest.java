package model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.hamcrest.collection.IsIn.*;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIn;
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
	@Test
	public void T01_�f�B�[���[��53���J�[�h�������Ă���() {

		assertThat(dealer.cardCount(), equalTo(53));
	}
	
	@Test
	public void T02_�z��I����4�l�̃v���[���[��12�����ێ����Q�e�[�u����5���̂���() {
		dealer.Serve();
		
		assertThat(table.cardCount(), equalTo(5));
		assertThat(players[0].cardCount(), equalTo(12));
	}

	private void prepare() {
		table = Table.New();
		players = new Player[]{new Player(), new Player(), new Player(), new Player()};
		dealer = Dealer.New(GameContext.New(table, players));
	}
	
	@Test
	public void T03_����Ⴄ�J�[�h���e�[�u����5���c��(){
		dealer.Serve();
		List<Card> first = table.cards;
		prepare();
		dealer.Serve();
		List<Card> second = table.cards;
		assertThat(first.containsAll(second), IsEqual.equalTo(false));
		
	}
}
