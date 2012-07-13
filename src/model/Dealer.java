package model;

import java.util.List;

public class Dealer {

	private static final int Rounds = 12;
	private List<Card> cards;
	private final GameContext gameContext;

	public Dealer(GameContext gameContext) {
		this.gameContext = gameContext;
		cards = CardSet.New().AllCards();
	}

	public void Serve() {
		Player[] players = gameContext.getPlayers();
		for (int i = 0; i < Rounds; i++) {
			players[0].cards.add(getRandomCard());
			players[1].cards.add(getRandomCard());
			players[2].cards.add(getRandomCard());
			players[3].cards.add(getRandomCard());
		}
		gameContext.getTable().cards.addAll(cards);
	}

	private Card getRandomCard() {
		Card card = cards.get((int)(Math.random() * cards.size()));
		cards.remove(card);
		return card;
	}

	public Integer cardCount() {
		return cards.size();
	}

	public static Dealer New(GameContext gameContext) {
		return new Dealer(gameContext);
	}
}
