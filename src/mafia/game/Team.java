package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;

public class Team {

	private ArrayList<Player> players;
	private Faction faction;
	
	
	public Team(Faction faction) {
		this.faction = faction;
	}
	
	// Add a Player to this team.
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	
	// Getters:
	
	public Faction getFaction() {
		return faction;
	}
	
}