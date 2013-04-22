package napoleon.model.role;

import mockit.Expectations;
import mockit.Mocked;
import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.Napoleon;
import napoleon.model.player.Player;
import napoleon.model.rule.*;
import napoleon.view.Viewer;
import org.apache.commons.collections15.CollectionUtils;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class DirectorTest {
	
	@Mocked Table table;
	@Mocked Dealer dealer;
	@Mocked Player player1;
	@Mocked Player player2;
	@Mocked Player player3;
	@Mocked Player player4;
	@Mocked Napoleon napoleon;
    @Mocked Viewer viewer;
	private DirectorTestable director;
	private Declaration declarationOfClub13 = Declaration.New(Suit.Club, 13);
	private Declaration declarationOfSpade13 = Declaration.New(Suit.Spade, 13);
	private Declaration declarationOfHeart14 = Declaration.New(Suit.Heart, 14);
	
	@Before
	public void setUp() {
		director = DirectorTestable.NewEx(
                table, dealer, new Player[]{player1, player2, player3, player4}, viewer);
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
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.askForDeclare(null, (Viewer)any); returns(declarationOfClub13);
				player2.askForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player3.askForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player4.askForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
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
	public void T02a_ナポレオンを決める_3人が宣言しほか3人がパスしたら決定(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.askForDeclare(null, (Viewer)any); returns(declarationOfClub13);
				player2.askForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player3.askForDeclare(declarationOfClub13, (Viewer)any); returns(Declaration.Pass);
				player4.askForDeclare(declarationOfClub13, (Viewer)any); returns(declarationOfSpade13);
				player1.askForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player2.askForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player3.askForDeclare(declarationOfSpade13, (Viewer)any); returns(declarationOfHeart14);
				player4.askForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				player1.askForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				player2.askForDeclare(declarationOfHeart14, (Viewer)any); returns(Declaration.Pass);
				TurnFactory.Get12Turns(Suit.Heart); returns(any);
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
	public void T03_ナポレオンを決める_全員パスしたら流れる(){
		 new Expectations() {
			{
				dealer.hasServed(); returns(true); 
				player1.askForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player2.askForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player3.askForDeclare(null, (Viewer)any); returns(Declaration.Pass);
				player4.askForDeclare(null, (Viewer)any); returns(Declaration.Pass);
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
				player1.askForDeclare(null, (Viewer)any); returns(declarationOfSpade13);
				player2.askForDeclare(declarationOfSpade13, (Viewer)any); returns(declarationOfClub13);
				player2.askForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player3.askForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
				player4.askForDeclare(declarationOfSpade13, (Viewer)any); returns(Declaration.Pass);
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
	public void T05_ナポレオンが場に残った5枚のカードをとって交換する(){
		new Expectations() {
			{
				 dealer.hasServed(); returns(true);
				 napoleon.tellTheAdjutant((Declaration)any, viewer); returns (Card.Mighty);
				 player1.cardsHaving(); returns(new ArrayList<Card>(){{add(Card.Mighty);}});
				 player1.setIsAdjutant(true);
				 napoleon.changeExtraCards((Declaration)any, (Table)any, viewer);
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
	public void T06_1ターン目の進行を行う(){
		new Expectations() {
			{
				 dealer.hasServed(); returns(true);
				 napoleon.getName(); returns("1");
				 player1.getName(); returns("1");
				 player1.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 3));
				 player2.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 8));
				 player3.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Heart, 9));
				 player4.openCard((Turn) any, (Viewer) any, (Declaration) any); returns(Card.New(Suit.Spade, 5));
				 player2.gainCards((Collection<Card>) any);
				 player2.getName(); returns(any);
			 			 }
		};
		director.SetExtraCardChanged(true);
		director.setNapoleon(napoleon);
		director.setIsNobodyDeclared(false);
		director.setTurns(TurnFactory.Get12Turns(Suit.Club));
		
		assertThat(director.getGameState(), equalTo(Status.ExtraCardsChanged));
		assertThat(director.getCurrentTurnNo(), equalTo(1));
		assertThat(director.getCurrentTurnStatus(), equalTo(TurnStatus.HasNotYetBegan));
		director.beginTurn(1);
		assertThat(director.getCurrentTurnNo(), equalTo(2));
		assertThat(director.getCurrentTurnStatus(), equalTo(TurnStatus.HasNotYetBegan));
	}

	@Test
	public void T07_ナポレオン軍が絵札を宣言枚数取得したらナポレオン軍の勝ち(){
		new Expectations() {
			{
				viewer.showGainedCardsForEachPlayer(napoleon, director.players);
				viewer.showExchangedCards(table);
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				player1.isAdjutant(); returns(true);
				player1.getName(); returns("1");
				viewer.showMessage("副官は 1 です");
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				viewer.showMessage("ナポレオン軍の獲得カード 15, 勝者は ナポレオン軍 です");
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 15);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.NapoleonTeam));
	}

	@Test
	public void T07_ナポレオン軍一人立ちでも絵札を宣言枚数取得したら勝ち(){
		new Expectations() {
			{
				viewer.showGainedCardsForEachPlayer(napoleon, director.players);
				viewer.showExchangedCards(table);
				player1.isAdjutant(); returns(false);
				player2.isAdjutant(); returns(false);
				player3.isAdjutant(); returns(false);
				player4.isAdjutant(); returns(false);
				napoleon.getGainedCardCount(); returns(15);
				player1.isAdjutant(); returns(false);
				player2.isAdjutant(); returns(false);
				player3.isAdjutant(); returns(false);
				player4.isAdjutant(); returns(false);
				viewer.showMessage("副官は いません。独り立ち です");
				player1.isAdjutant(); returns(false);
				player2.isAdjutant(); returns(false);
				player3.isAdjutant(); returns(false);
				player4.isAdjutant(); returns(false);
				napoleon.getGainedCardCount(); returns(15);
				viewer.showMessage("ナポレオン軍の獲得カード 15, 勝者は ナポレオン軍 です");
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 15);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.NapoleonTeam));
	}

	@Test
	public void T08_ナポレオン軍が絵札を宣言枚数取得できなかったら連合軍の勝ち(){
		new Expectations() {
			{
				viewer.showGainedCardsForEachPlayer(napoleon, director.players);
				viewer.showExchangedCards(table);
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				player1.isAdjutant(); returns(true);
				player1.getName(); returns("1");
				viewer.showMessage("副官は 1 です");
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(10);
				player1.getGainedCardCount(); returns(5);
				viewer.showMessage("ナポレオン軍の獲得カード 15, 勝者は 連合軍 です");
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 16);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.AlliedForcesTeam));
	}
	
	@Test
	public void T08_2全とりは連合軍の勝ち() {
		new Expectations() {
			{
				viewer.showGainedCardsForEachPlayer(napoleon, director.players);
				viewer.showExchangedCards(table);
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(15);
				player1.getGainedCardCount(); returns(5);
				player1.isAdjutant(); returns(true);
				player1.getName(); returns("1");
				viewer.showMessage("副官は 1 です");
				player1.isAdjutant(); returns(true);
				napoleon.getGainedCardCount(); returns(15);
				player1.getGainedCardCount(); returns(5);
				viewer.showMessage("ナポレオン軍の獲得カード 20, 勝者は 連合軍 です");
			 }
		};
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 16);
		assertThat(director.JudgeWinnerTeam(), equalTo(Team.AlliedForcesTeam));
	}
	
	@Test
	public void T09_1ターン目はナポレオンから始まること(){
		new Expectations() {
			{
				napoleon.getName(); returns("4");
				player1.getName(); returns("1");
				player2.getName(); returns("2");
				player3.getName(); returns("3");
				player4.getName(); returns("4");
			}
		};
		
		director.napoleon = napoleon;
		director.fixedDeclaration = Declaration.New(Suit.Heart, 15);
		
		assertThat((Player)CollectionUtils.get(director.getPlayersForTurn(1), 0), equalTo(player4));
	}
}
