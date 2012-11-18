package napoleon.model.role;

import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Adjutant;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.Status;
import napoleon.model.rule.Table;
import napoleon.model.rule.Turn;
import napoleon.model.rule.TurnStatus;
import napoleon.view.Viewer;

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
	@Mocked Napoleon napoleon;
	@Mocked Adjutant adjutant;
	private DirectorEx director;
	private Declaration declarationOfClub13 = Declaration.New(Suit.Club, 13);
	private Declaration declarationOfSpade13 = Declaration.New(Suit.Spade, 13);
	private Declaration declarationOfHeart14 = Declaration.New(Suit.Heart, 14);
	
	@Before
	public void setUp() {
		director = (DirectorEx)DirectorEx.NewEx(table, dealer, new Player[]{player1, player2, player3, player4});
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
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null, (Viewer)any); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				Napoleon.New(player1); returns(napoleon);
				napoleon.getName(); returns(any);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		assertThat(director.getNapoleon(), IsEqual.equalTo(napoleon));
		assertThat(director.getDeclaration(), IsEqual.equalTo(declarationOfClub13));
	}
	
	@Test
	public void T02a_�i�|���I�������߂�_3�l���錾���ق�3�l���p�X�����猈��(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null, (Viewer)any); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfClub13, (Viewer)any); returns(declarationOfSpade13);
				player1.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player2.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(declarationOfHeart14);
				player4.AskForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				player1.AskForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				player2.AskForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				Napoleon.New(player3); returns(napoleon);
				napoleon.getName(); returns(any);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.NapoleonDefined));
		assertThat(director.getNapoleon(), IsEqual.equalTo(napoleon));
		assertThat(director.getDeclaration(), IsEqual.equalTo(declarationOfHeart14));
	}

	@Test
	public void T03_�i�|���I�������߂�_�S���p�X�����痬���(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.AskForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player2.AskForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player3.AskForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player4.AskForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				dealer.hasServed(); returns(true); 
			}
		};
		assertThat(director.getGameState(), IsEqual.equalTo(Status.CardServed));
		director.defineNapoleon();
		assertThat(director.getGameState(), IsEqual.equalTo(Status.GameEnded));
	}
	
	@Test
	public void T04_�����Ⴂ�錾�͔F�߂��ēx�錾���邩����(){
		 new Expectations() {
			{
				player1.AskForDeclare(null, (Viewer)any); returns(declarationOfSpade13);
				player2.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(declarationOfClub13);
				player2.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player3.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player4.AskForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				Napoleon.New(player1); returns(napoleon);
				napoleon.getName(); returns(any);
				dealer.hasServed(); returns(true); 
			}
		 };
		 director.defineNapoleon();
		 assertThat(director.getGameState(), equalTo(Status.NapoleonDefined));
		 assertThat(director.getNapoleon(), equalTo(napoleon));
		 assertThat(director.getDeclaration(), equalTo(declarationOfSpade13));
	}
	
	@SuppressWarnings("serial")
	@Test
	public void T05_�i�|���I������Ɏc����5���̃J�[�h���Ƃ��Č�������(){
		new Expectations() {
			{
				 dealer.hasServed(); returns(true);
				 napoleon.tellTheAdjutant((Declaration)any); returns (Card.Mighty);
				 player1.cardsHaving(); returns(new ArrayList<Card>(){{add(Card.Mighty);}});
				 player1.setIsAdjutant(true);
				 napoleon.changeExtraCards((Declaration)any, (Table)any);
				 dealer.hasServed(); returns(true);
			 }
		};
		director.setNapoleon(napoleon);
		director.setIsNobodyDeclared(false);
		
		assertThat(director.getGameState(), equalTo(Status.NapoleonDefined));
		director.askForAdjutant();
		director.letNapoleonChangeExtraCards();
		assertThat(director.getGameState(), equalTo(Status.ExtraCardsChanged));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void T06_1�^�[���ڂ̐i�s���s��(){
		new Expectations() {
			{
				 dealer.hasServed(); returns(true);
				 player1.getName(); returns("1");
				 player1.cardsGained(); returns(any);
				 player1.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 3));
				 player2.getName(); returns("2");
				 player2.cardsGained(); returns(any);
				 player2.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 8));
				 player3.getName(); returns("3");
				 player3.cardsGained(); returns(any);
				 player3.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Heart, 9));
				 player4.getName(); returns("4");
				 player4.cardsGained(); returns(any);
				 player4.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 5));
				 player2.takeCards((Collection<Card>) any);
				 player2.getName(); returns("2");
			 }
		};
		director.SetExtraCardChanged(true);
		director.setNapoleon(napoleon);
		director.setIsNobodyDeclared(false);
		
		assertThat(director.getGameState(), equalTo(Status.ExtraCardsChanged));
		assertThat(director.getCurrentTurnNo(), equalTo(1));
		assertThat(director.getCurrentTurnStatus(), equalTo(TurnStatus.HasNotYetBegan));
		director.beginTurn(1);
		assertThat(director.getCurrentTurnNo(), equalTo(2));
		assertThat(director.getCurrentTurnStatus(), equalTo(TurnStatus.HasNotYetBegan));
	}

	@Test
	public void T07_�i�|���I���R���G�D��錾�����擾������i�|���I���R�̏���(){
		new Expectations() {
			{
				napoleon.cardsGained(); returns(any);
				player1.getName(); returns(any);
				player1.cardsGained(); returns(any);
				player2.getName(); returns(any);
				player2.cardsGained(); returns(any);
				player3.getName(); returns(any);
				player3.cardsGained(); returns(any);
				player4.getName(); returns(any);
				player4.cardsGained(); returns(any);
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				player1.isAdjutant(); returns(true);
				player1.getName(); returns(any);
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 15);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.NapoleonTeam));
	}

	@Test
	public void T08_�i�|���I���R���G�D��錾�����擾�ł��Ȃ�������A���R�̏���(){
		new Expectations() {
			{
				napoleon.cardsGained(); returns(any);
				player1.getName(); returns(any);
				player1.cardsGained(); returns(any);
				player2.getName(); returns(any);
				player2.cardsGained(); returns(any);
				player3.getName(); returns(any);
				player3.cardsGained(); returns(any);
				player4.getName(); returns(any);
				player4.cardsGained(); returns(any);
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				player1.isAdjutant(); returns(true);
				player1.getName(); returns(any);
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 16);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.AlliedForcesTeam));
	}
}
