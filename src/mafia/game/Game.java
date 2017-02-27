package mafia.game;

import java.util.ArrayList;
import java.util.Collections;

import mafia.factory.PlayerFactory;
import mafia.values.Faction;
import mafia.values.Phase;
import mafia.values.Status;

public class Game {

	private ArrayList<Player> players;
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
			addPlayer(pf.createPlayer(playerNames[i], roles.get(i), this));
		}
	}
	
	// Check the factions of the living players to see if there are any winners.
	public Faction checkForWinner() {
		// Determine Current Composition
		int numMafia = getPlayerCount(Faction.MAFIA);
		int numTown = getPlayerCount(Faction.TOWN);
		if (numMafia == 0 && numTown > 0) {
			return Faction.TOWN;
		} else if (numMafia >= numTown && numMafia > 0) {
			return Faction.MAFIA;
		} else if (numMafia == 0 && numTown == 0) {
			return Faction.NONE;
		} else {
			return null;
		}
	}
	
	// Get the number of living players that belong to a given faction.
	public int getPlayerCount(Faction faction) {
		int count = 0;
		for (Player player : getLivingPlayers()) {
			if (player.getFaction() == faction) {
				count++;
			}
		}
		return count;
	}
	
	// Return an ArrayList of players that are not dead.
	public ArrayList<Player> getLivingPlayers() {
		ArrayList<Player> livingPlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (!player.hasStatus(Status.DEAD)) {
				livingPlayers.add(player);
			}
		}
		return livingPlayers;
	}

	// Iterate through all players, performing their selected actions.
	public void performActions() {
		for (Player player : players)
			player.performAction();
	}

	// Iterate through all players, clearing any votes that were applied.
	public void clearVotes() {
		for (Player player : players)
			player.clearVotes();
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
	
	// Kill all players that are "tobeKILLED" (or top vote getter), and return that list.
	public ArrayList<Player> killPlayers() {
		ArrayList<Player> killedPlayers = new ArrayList<Player>();
		for (Player player : players) {
			if (player.removeStatus(Status.tobeKILLED)) {
				killedPlayers.add(player);
			}
		}
		Player topVotedPlayer = getTopVoted();
		if (!(topVotedPlayer instanceof NoPlayer)) {
			killedPlayers.add(topVotedPlayer);
		}
		for (Player player : killedPlayers) {
			player.addStatus(Status.DEAD);
		}
		return killedPlayers;
	}
	
	// Return the player with the highest vote count, or return NoPlayer if there is a tie.
	public Player getTopVoted() {
		ArrayList<Player> topVotedPlayers = new ArrayList<Player>();
		int mostVotes = 0;
		int playersVotes;
		// Go through list of living players to find a list of players with the most votes.
		for (Player player : getLivingPlayers()) {
			playersVotes = player.getVotes();
			if (playersVotes >= mostVotes) {
				// Add the player to the list if it has the most votes.
				if (playersVotes > mostVotes) {
					// Start a new list if there is a new most votes count
					topVotedPlayers = new ArrayList<Player>();
					mostVotes = playersVotes;
				}
				topVotedPlayers.add(player);
			}
		}
		// Only return a (real) player if there is a single top vote getter.
		if (topVotedPlayers.size() == 1) {
			return topVotedPlayers.get(0);
		} else {
			return new NoPlayer();
		}
	}
	
	// Setters:

	public void setRoles(ArrayList<Role> roles) {
		this.roles = roles;
	}
	
	public void setPlayerNames(String[] names) {
		this.playerNames = names;
	}
	
	
	// Getters:

	public ArrayList<Role> getRoles() {
		return roles;
	}

	public String[] getPlayerNames() {
		return playerNames;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Phase getPhase() {
		return phase;
	}

	public int getTurn() {
		return turn;
	}
	
	// Private Methods:
	
	private void addPlayer(Player player) {
		players.add(player);
	}
}