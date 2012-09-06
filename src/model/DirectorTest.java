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
	private Declaration declarationOfClub13 = Declaration.New(Suit.Club, 13);
	private Declaration declarationOfSpade13 = Declaration.New(Suit.Spade, 13);
	private Declaration declarationOfHeart14 = Declaration.New(Suit.Heart, 14);
	
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
	public void T02_カード配る前に宣言確認はできない() {
		
	}
	
	@Test
	public void T02_ナポレオンを決める_一人が宣言しほか3人がパスしたら決定(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfClub13); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfClub13); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfClub13); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		assertThat(director.getNapoleon(), IsEqual.equalTo(player1));
		assertThat(director.getDeclaration(), IsEqual.equalTo(declarationOfClub13));
	}
	
	@Test
	public void T02a_ナポレオンを決める_3人が宣言しほか3人がパスしたら決定(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfClub13); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfClub13); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfClub13); returns(declarationOfSpade13);
				player1.AskForDeclare(declarationOfSpade13); returns(Declaration.Pass);
				player2.AskForDeclare(declarationOfSpade13); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfSpade13); returns(declarationOfHeart14);
				player4.AskForDeclare(declarationOfHeart14); returns(Declaration.Pass);
				player1.AskForDeclare(declarationOfHeart14); returns(Declaration.Pass);
				player2.AskForDeclare(declarationOfHeart14); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		assertThat(director.getNapoleon(), IsEqual.equalTo(player3));
		assertThat(director.getDeclaration(), IsEqual.equalTo(declarationOfHeart14));
	}

	@Test
	public void T03_ナポレオンを決める_全員パスしたら流れる(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null); returns(Declaration.Pass);
				player2.AskForDeclare(null); returns(Declaration.Pass);
				player3.AskForDeclare(null); returns(Declaration.Pass);
				player4.AskForDeclare(null); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.GameEnded));
	}
	
	@Test
	public void T04_今より低い宣言は認めず再度宣言するか聞く(){
		 new Expectations() {
			{
				player1.AskForDeclare(null); returns(declarationOfSpade13);
				player2.AskForDeclare(declarationOfSpade13); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfSpade13); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfSpade13); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfSpade13); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		 };
		 director.defineNapoleon();
		 assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		 assertThat(director.getNapoleon(), IsEqual.equalTo(player1));
		 assertThat(director.getDeclaration(), IsEqual.equalTo(declarationOfSpade13));
	}
}
