package model.rule;


public class TurnFactory {

	public static Turn[] Get12Turns() {
		return new Turn[] {
				Turn.New(1),	
				Turn.New(2),	
				Turn.New(3),	
				Turn.New(4),	
				Turn.New(5),	
				Turn.New(6),	
				Turn.New(7),	
				Turn.New(8),	
				Turn.New(9),	
				Turn.New(10),	
				Turn.New(11),	
				Turn.New(12),	
		};
	}

}
