package mafia.game;

import java.util.ArrayList;
import java.util.Collections;

import mafia.game.Player.PlayerFactory;

public class Game {

	private ArrayList<Player> players;
	private ArrayList<Team> teams;
	private ArrayList<Role> roles;
	private String[] playerNames;
	private final PlayerFactory pf;
	
	public Game(final PlayerFactory pf) {
		players = new ArrayList<Player>();
		this.pf = pf;
	}

	// Create a new player for each name provided, selecting a random role for each.
	public void assignRoles() {
		Collections.shuffle(roles);
		for (int i = 0; i < playerNames.length; i++) {
			addPlayer(pf.createPlayer(playerNames[i], roles.get(i)));
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
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	
	// Private Methods:
	
	private void addPlayer(Player player) {
		players.add(player);
	}
	
}