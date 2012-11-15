package napoleon;
import napoleon.model.player.ManualPlayer;
import napoleon.model.player.Player;
import napoleon.model.role.Director;
import napoleon.model.role.Team;
import napoleon.model.rule.Table;
import napoleon.view.ConsoleViewer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Runner {
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Runner.class);
		logger.error("err..");
		logger.debug("begin..");
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), ManualPlayer.New("4")};
		Director director = Director.New(Table.New(), players, ConsoleViewer.GetInstance());
		director.serveCards();
		director.showSituation();
		director.defineNapoleon();
		
		director.askForAdjutant();
		director.letNapoleonChangeExtraCards();
		
		for(int i = 1; i <= 12; i++ ) {
			director.beginTurn(i);
//			System.out.println(String.format("��turn[%d], winner[%s]", i, director.getTurnWinner(i)));
		}

		director.JudgeWinnerTeam();
	}
}
