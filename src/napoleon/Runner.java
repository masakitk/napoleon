package napoleon;
import napoleon.model.player.Player;
import napoleon.model.role.Director;
import napoleon.model.role.Team;
import napoleon.model.rule.Table;
import napoleon.player.ManualPlayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Runner {
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Runner.class);
		logger.error("err..");
		logger.debug("begin..");
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		Director director = Director.New(Table.New(), players);
		director.serveCards();
		director.showSituationToConsole();
		director.defineNapoleon();
		
		director.askForAdjutant();
		director.letNapoleonChangeExtraCards();
		
		for(int i = 1; i <= 12; i++ ) {
			director.beginTurn(i);
			logger.debug(String.format("turn[%d], winner[%s]", i, director.getTurnWinner(i)));
			System.out.println(String.format("turn[%d], winner[%s]", i, director.getTurnWinner(i)));
		}

		logger.debug(String.format("napoleon gained %s", director.getNapoleon().cardsGained()));
		for (Player p : players) {
			logger.debug(String.format("player %s gained %s", p.getName(), p.cardsGained()));
		}

		Team winner = director.JudgeWinnerTeam();
		logger.info(String.format("adjutant is %s", director.getAdjutantName()));
		logger.info(String.format("winner is %s", winner));
	}
}
