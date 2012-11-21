package napoleon.model.player;

public class TakahashiPlayer extends Player {

	public TakahashiPlayer(String name) {
		super(name);
	}
	
	public static Player New(String name) {
		return new TakahashiPlayer(name);
	}

	

}
