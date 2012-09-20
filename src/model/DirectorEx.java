package model;

public class DirectorEx extends Director {

	public void setNapoleon(Napoleon napoleon) {
		this.napoleon = napoleon;
	}

	public void setIsNobodyDeclared(boolean isNobodyDeclared) {
		this.isNobodyDeclared = isNobodyDeclared;
	}

	public static DirectorEx NewEx(Table table, Dealer dealer, Player[] players) {
		DirectorEx instance = new DirectorEx();
		instance.table = table;
		instance.dealer = dealer;
		instance.players = players;
		return instance;
	}

	public void SetExtraCardChanged(boolean b) {
		extraCardChanged = b;
	}


}

