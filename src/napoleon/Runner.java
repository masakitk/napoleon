package napoleon;
import napoleon.model.player.ManualPlayer;
import napoleon.model.player.Player;
import napoleon.model.player.TakahashiPlayer;
import napoleon.model.role.Director;
import napoleon.model.role.Team;
import napoleon.model.rule.Table;
import napoleon.view.ConsoleViewer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Runner {
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Runner.class);
		logger.debug("begin..");
		Player[] players = getPlayers();
		Director director = Director.New(Table.New(), players, ConsoleViewer.GetInstance());
		director.serveCards();
		director.showSituation();
		director.defineNapoleon();
		
		director.askForAdjutant();
		director.letNapoleonChangeExtraCards();
		
		for(int i = 1; i <= 12; i++ ) {
			director.beginTurn(i);
		}

		director.JudgeWinnerTeam();
	}

	protected static Player[] getPlayers() {
		return new Player[]{TakahashiPlayer.New("T1"), Player.New("2"), Player.New("3"), ManualPlayer.New("4")};
	}
}
