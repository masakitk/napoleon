package napoleon.model.rule;

import napoleon.model.card.Suit;


public class TurnFactory {

	public static Turn[] Get12Turns(Suit trump) {
		return new Turn[] {
				Turn.New(1, trump),	
				Turn.New(2, trump),	
				Turn.New(3, trump),	
				Turn.New(4, trump),	
				Turn.New(5, trump),	
				Turn.New(6, trump),	
				Turn.New(7, trump),	
				Turn.New(8, trump),	
				Turn.New(9, trump),	
				Turn.New(10, trump),	
				Turn.New(11, trump),	
				Turn.New(12, trump),	
		};
	}

}
