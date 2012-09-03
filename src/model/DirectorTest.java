package model;

import static org.junit.Assert.assertThat;

import mockit.Expectations;
import mockit.Mocked;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class DirectorTest {
	
	@Mocked Table table;
	@Mocked Dealer dealer;
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;
	private Director director;
	Declaration firstDeclaration;
	
	@Before
	public void setUp() {
		director = Director.New(table, dealer, new Player[]{player1, player2, player3, player4});
	}
	
	@Test
	public void T01_はじめにカードを配る(){
		new Expectations() {
			{
				dealer.hasServed(); returns(false); 
				dealer.Serve();
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.Initial));
		director.serveCards();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
	}
	
	@Test
	public void T02_ナポレオンを決める_一人が宣言しほか3人がパスしたら決定(){
		 firstDeclaration = Declaration.New(Suit.Club, 13);
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null); returns(firstDeclaration);
				player2.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				player3.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				player4.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		assertThat(director.getNapoleon(), IsEqual.equalTo(player1));
		assertThat(director.getDeclaration(), IsEqual.equalTo(firstDeclaration));
	}

	@Test
	public void T03_ナポレオンを決める_全員パスしたら流れる(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null); returns(Declaration.Pass);
				player2.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				player3.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				player4.AskForDeclare(firstDeclaration); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.GameEnded));
	}
}
