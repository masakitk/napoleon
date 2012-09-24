import view.ConsoleView;
import model.player.Player;
import model.role.Dealer;
import model.role.Director;
import model.rule.GameContext;
import model.rule.Table;


public class Runner {
	public static void main(String[] args) {
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		Director director = Director.New(Table.New(), players);
		director.serveCards();
		director.showSituationToConsole();
		director.defineNapoleon();
		
	}
}
