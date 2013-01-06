package napoleon.model.rule;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;


public class GameContext {

    private Declaration declaration;

    private GameContext() {
    }

    public static GameContext New(Declaration declaration) {
        GameContext gameContext = new GameContext();
        gameContext.setDeclaration(declaration);
        return gameContext;
    }

    private void setDeclaration(Declaration declaration) {
        this.declaration = declaration;
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
		return GameContext.getRightBower(declaration.getSuit());
	}

	public Card getLeftBower() {
		return GameContext.getLeftBower(declaration.getSuit());
	}
}
