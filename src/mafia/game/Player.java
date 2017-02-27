package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;
import mafia.values.Status;

public class Player {

	private String name;
	private Faction faction;
	private Game game;
	private ArrayList<Action> actions;
	private Action selectedAction;
	private Player target;
	private ArrayList<Status> statuses;
	private Role role;
	private String dailyMessage;
	private int votes;
	
	// When the player is created, get Team and available Actions from provided Role
	public Player(String name, Role role, Game game) {
		this.role = role;
		this.game = game;
		this.dailyMessage = "";
		this.name = name;
		faction = role.getFaction();
		actions = role.getActions();
		statuses = new ArrayList<Status>();
		votes = 0;
	}
	public Player(String name) {
		this.name = name;
		statuses = new ArrayList<Status>();
	}
	
	// Return the faction from the team to which this player belongs.
	public Faction getFaction() {
		return faction;
	}

	// Perform the action the player has selected.
	public void performAction() {
		selectedAction.execute(this,target);
	}
	
	// Set the next action for the player.
	public boolean chooseAction(Action selectedAction, Player target) {
		if (selectedAction.checkIfValidTarget(target)) {
			this.selectedAction = selectedAction;
			this.target = target;
			return true;
		} else {
			return false;
		}
	}
	
	// Vote for this player.
	public void applyVote() {
		votes++;
	}
	
	// Clear votes.
	public void clearVotes() {
		votes = 0;
	}
	
	
	// Return a list of actions that are available for the current phase.
	public ArrayList<Action> getValidActions() {
		ArrayList<Action> validActions = new ArrayList<Action>();
		for (Action action : actions) {
			if (action.checkIfValidPhase()) {
				validActions.add(action);
			}
		}
		return validActions;
	}
	
	// Return the daily message, and then reinitialize it.
	public String retrieveDailyMessage() {
		String retrievedMessage = dailyMessage;
		dailyMessage = "";
		return retrievedMessage;
	}
	
	public String toString() {
		return name;
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

	public ArrayList<Action> getActions() {
		return actions;
	}

	public int getVotes() {
		return votes;
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