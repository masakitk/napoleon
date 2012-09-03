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
	public void T01_�͂��߂ɃJ�[�h��z��(){
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
	public void T02_�i�|���I�������߂�_��l���錾���ق�3�l���p�X�����猈��(){
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
	public void T03_�i�|���I�������߂�_�S���p�X�����痬���(){
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
