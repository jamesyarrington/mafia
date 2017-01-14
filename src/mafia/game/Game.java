package mafia.game;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private ArrayList<Player> players;
	private ArrayList<Team> teams;
	private ArrayList<Role> roles;
	private String[] playerNames;
	
	public Game() {
	}

	// Create a new player for each name provided, selecting a random role for each.
	public void assignRoles() {
		Collections.shuffle(roles);
		for (int i = 0; i < playerNames.length; i++) {
			addPlayer(new Player(playerNames[i], roles.get(i)));
		}
	}
	
	// Iterate through all players, performing their selected actions.
	public void performActions() {
		for (Player player : players)
			player.performAction();
	}
	
	
	// Setters:

	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}

	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
	
	public void setPlayerNames(String[] names) {
		this.playerNames = names;
	}
	
	
	// Getters:
	
	public ArrayList<Team> getTeams() {
		return teams;
	}

	public ArrayList<Role> getRoles() {
		return roles;
	}

	public String[] getPlayerNames() {
		return playerNames;
	}
	
	
	// Private Methods:
	
	private void addPlayer(Player player) {
		players.add(player);
	}
	
}