package mafia.game;

import java.util.ArrayList;
import java.util.Collections;

import mafia.game.Player.PlayerFactory;
import mafia.values.Phase;

public class Game {

	private ArrayList<Player> players;
	private ArrayList<Team> teams;
	private ArrayList<Role> roles;
	private String[] playerNames;
	private final PlayerFactory pf;
	private int turn;
	private Phase phase;
	
	public Game(final PlayerFactory pf) {
		players = new ArrayList<Player>();
		this.pf = pf;
		turn = 0;
		phase = Phase.NIGHT;
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
	
	// Advance phase, advancing the turn if going NIGHT -> DAY
	public void advance() {
		if (phase == Phase.DAY) {
			phase = Phase.NIGHT;
		} else {
			turn++;
			phase = Phase.DAY;
		}
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

	public Phase getPhase() {
		return phase;
	}

	public int getTurn() {
		return turn;
	}
	
}