package model;

public class NapoleonSelector {

	private final GameContext gameContext ;

	private NapoleonSelector(){
		this.gameContext = null;
	}
	
	private NapoleonSelector(GameContext gameContext) {
		this.gameContext  = gameContext;
	}

	public static NapoleonSelector New(GameContext gameContext) {
		return new NapoleonSelector(gameContext);
	}

	public void determineNapoleon() {
		gameContext.setNapoleon(gameContext.getPlayer(0));
	}

}
