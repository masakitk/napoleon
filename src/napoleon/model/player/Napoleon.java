package napoleon.model.player;

import napoleon.model.card.Card;
import napoleon.model.card.Suit;
import napoleon.model.rule.Declaration;
import napoleon.model.rule.GameContext;
import napoleon.model.rule.Table;

public class Napoleon extends Player{
	private Napoleon(Player player) {
		super(player.getName());
		cards.addAll(player.cards);
	}

	public void changeExtraCards(Declaration fixedDeclaration, Table table) {
		table.getNoUseCards().addAll(table.getCards());
	}

	public Card tellTheAdjutant(Declaration fixedDeclaration) {
		if(!cards.contains(Card.Mighty)) return Card.Mighty;
		Suit trump = fixedDeclaration.getSuit();
		Card rightBower = GameContext.getRightBower(trump);
		if(!cards.contains(rightBower)) return rightBower;
		Card leftBower = GameContext.getLeftBower(trump);
		if(!cards.contains(leftBower)) return leftBower;
		if(!cards.contains(Card.Yoromeki)) return Card.Yoromeki;
		Card uraSame2 = Card.New(GameContext.getSameColorAnotherSuit(trump), 2);
		if(!cards.contains(uraSame2)) return uraSame2;
		if(cards.contains(Card.Jorker) && !cards.contains(Card.RequireJorker)) return Card.RequireJorker;
		return SomeTrumpCard(fixedDeclaration);
	}

	Card SomeTrumpCard(Declaration fixedDeclaration) {
		Card ace = Card.New(fixedDeclaration.getSuit(), 1);
		if(!cards.contains(ace)) return ace;
		for(int i = 13; 1 < i; i--) {
			Card some = Card.New(fixedDeclaration.getSuit(), i);
			if(!cards.contains(some)) return some;
		}
		throw new IllegalStateException("ŽèŽ‚¿12–‡‚ÅØ‚èŽD13–‡AŽèŽ‚¿‚É‚È‚¢Ø‚èŽD‚ª‚È‚¢‚±‚Æ‚Í‚ ‚è‚¦‚È‚¢");
	}

	public static Napoleon New(Player player) {
		return new Napoleon(player);
	}
}
