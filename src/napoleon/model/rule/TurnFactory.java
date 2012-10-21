package napoleon.model.rule;

import napoleon.model.card.Suit;


public class TurnFactory {

	public static Turn[] Get12Turns() {
		return new Turn[] {
				Turn.New(1, Suit.Club),	
				Turn.New(2, Suit.Club),	
				Turn.New(3, Suit.Club),	
				Turn.New(4, Suit.Club),	
				Turn.New(5, Suit.Club),	
				Turn.New(6, Suit.Club),	
				Turn.New(7, Suit.Club),	
				Turn.New(8, Suit.Club),	
				Turn.New(9, Suit.Club),	
				Turn.New(10, Suit.Club),	
				Turn.New(11, Suit.Club),	
				Turn.New(12, Suit.Club),	
		};
	}

}
