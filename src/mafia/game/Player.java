package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;
import mafia.values.Status;

public class Player {

	private String name;
	private Team team;
	private Game game;
	private ArrayList<Action> actions;
	private Action selectedAction;
	private Player target;
	private ArrayList<Status> statuses;
	private Role role;
	private String dailyMessage;
	
	// When the player is created, get Team and available Actions from provided Role
	public Player(String playerName, Role role, Game game) {
		this.role = role;
		this.game = game;
		this.dailyMessage = "";
//		team = role.getTeam();  // TODO: Return team-related actions
//		team.addPlayer(this);
		name = playerName;
		actions = role.getActions();
		statuses = new ArrayList<Status>();
	}
	
	// Return the faction from the team to which this player belongs.
	public Faction getFaction() {
		return team.getFaction();
	}

	// Perform the action the player has selected.
	public void performAction() {
		selectedAction.execute(this,target);
	}
	
	// Set the next action for the player.
	public boolean chooseAction(Action selectedAction, Player target) {
		if (selectedAction.checkIfValid(target)) {
			this.selectedAction = selectedAction;
			this.target = target;
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Player> getValidTargets(Action potentialAction) {
		ArrayList<Player> returnedPlayerArrayList = new ArrayList<Player>();
		for (Player player : game.getPlayers()) {
			if (potentialAction.checkIfValid(player)) {
				returnedPlayerArrayList.add(player);
			}
		}
		return returnedPlayerArrayList;
	}
	
	// Return the daily message, and then reinitialize it.
	public String retrieveDailyMessage() {
		String retrievedMessage = dailyMessage;
		dailyMessage = "";
		return retrievedMessage;
	}
	
	
	// Status methods:
	
	public void addStatus(Status newStatus) {
		statuses.add(newStatus);
	}
	
	// Return true if status was present.
	public boolean removeStatus(Status remStatus) {
		return statuses.remove(remStatus);
	}
	
	public boolean hasStatus(Status chkStatus) {
		return statuses.contains(chkStatus);
	}
	
	
	// Getters:
	
	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public Team getTeam() {
		return team;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public Action getSelectedAction() {
		return selectedAction;
	}

	public Player getTarget() {
		return target;
	}

	public ArrayList<Status> getStatuses() {
		return statuses;
	}
	
	
	// Setters:
}