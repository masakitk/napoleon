import view.ConsoleView;
import model.Dealer;
import model.Director;
import model.GameContext;
import model.Player;
import model.Table;


public class Runner {
	public static void main(String[] args) {
		Player[] players = new Player[]{Player.New("1"), Player.New("2"), Player.New("3"), Player.New("4")};
		Director director = Director.New(Table.New(), players);
		director.serveCards();
		director.showSituationToConsole();
		director.defineNapoleon();
		
	}
}
