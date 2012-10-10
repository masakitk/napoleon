package model.player;

public class Napoleon extends Player{
	private Napoleon(Player player) {
		super(player.getName());
	}

	public void changeExtraCards() {
		// TODO Auto-generated method stub
		
	}

	public Adjutant tellTheAdjutant() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Napoleon New(Player player) {
		return new Napoleon(player);
	}

	
}
