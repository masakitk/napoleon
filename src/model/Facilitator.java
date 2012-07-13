package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Facilitator {

	private static final int LastRoundNo = 12;
	private final GameContext gameContext;
	private Status status = Status.NotPrepared;
	private int roundNo = 0;

	public Facilitator(GameContext gameContext) {
		this.gameContext = gameContext;
	}

	public void setContext(GameContext gameContext) {
		// TODO Auto-generated method stub
		
	}

	public Player askForAll() {
		throw new NotImplementedException();
//		return null;
		// TODO Auto-generated method stub
		
	}

	public static Facilitator New(GameContext gameContext) {
		return new Facilitator(gameContext);
	}

	public void ServeCards() {
		Dealer dealer = Dealer.New(gameContext);
		dealer.Serve();
		status = Status.CardServed;
	}

	public GameContext getContext() {
		return gameContext;
	}

	public Status getStatus() {
		return status;
	}

	public void determineNapoleon() {
		NapoleonSelector.New(gameContext).determineNapoleon();
		status = Status.GameStarted;
	}

	public void StartRounds() {
		roundNo  += 1;
		
		if(IsLastRound()) {
			status = Status.GameEnded;
		}
	}

	private boolean IsLastRound() {
		return LastRoundNo == roundNo;
	}

	public Integer getCurrentRoundNo() {
		return roundNo;
	}

}
