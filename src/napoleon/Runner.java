package napoleon;
import napoleon.model.player.Player;
import napoleon.model.role.Director;
import napoleon.model.rule.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class Runner {
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(Runner.class);
		logger.error("err..");
		logger.trace("begin..");
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		Director director = Director.New(Table.New(), players);
		director.serveCards();
		director.showSituationToConsole();
		director.defineNapoleon();
		
		director.askForAdjutant();
		director.letNapoleonChangeExtraCards();
		
		for(int i = 1; i <= 12; i++ ) {
			director.beginTurn(i);
		}
		
	}
}
