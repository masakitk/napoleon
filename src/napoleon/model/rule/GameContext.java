package napoleon.model.rule;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.player.NapoleonCall;


public class GameContext {

    private Suit trump;
    private Options options;
    private static GameContext current = null;
    private NapoleonCall _napoleonCall = NapoleonCall.None;

    private GameContext() {
    }

    public static void Init(Suit trump) {
        GameContext gameContext = new GameContext();
        gameContext.setTrump(trump);
        gameContext.options = (current == null) ? Options.DEFAULT : current.options;
        current = gameContext;
    }

    private void setTrump(Suit trump) {
        this.trump = trump;
    }

    public static Card getLeftBower(Suit leadSuit) {
		return Card.New(getSameColorAnotherSuit(leadSuit), 11);
	}

	public static Card getRightBower(Suit trump) {
		return Card.New(trump, 11);
	}

	public static Suit getSameColorAnotherSuit(Suit suit) {
		switch (suit) {
		case Spade:
			return Suit.Club;
		case Heart:
			return Suit.Dia;
		case Dia:
			return Suit.Heart;
		case Club:
			return Suit.Spade;
		default:
			throw new IllegalArgumentException(String.format("%s:未対応のスートです", suit));
		}
	}

    public Card getRightBower() {
		return GameContext.getRightBower(trump);
	}

	public Card getLeftBower() {
		return GameContext.getLeftBower(trump);
	}

    public static GameContext getCurrent() {
        return current;
    }

    public Options getOptions() {
        return options;
    }

    public boolean canHideMighty() {
        return options.canHideMighty;
    }

    public boolean callsToGoMighty() {
        return _napoleonCall == NapoleonCall.MightyGo;
    }
}
