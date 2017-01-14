package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;
import mafia.values.Status;

public class Player {
	
	private String name;
	private Team team;
	private ArrayList<Action> actions;
	private Action selectedAction;
	private Player target;
	private ArrayList<Status> statuses;
	
	
	// When the player is created, get Team and available Actions from provided Role
	public Player(String playerName, Role assignedRole) {
		team = assignedRole.getTeam();
		name = playerName;
		actions = assignedRole.getActions();
		team.addPlayer(this);
	}
	
	// Return the faction from the team to which this player belongs.
	public Faction getFaction() {
		return team.getFaction();
	}

	// Perform the action the player has selected.
	public void performAction() {
		selectedAction.execute(this,target);
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

	public Team getTeam() {
		return team;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}

	public Action getSelectedAction() {
		return selectedAction;
	}

	public Player getSelectedPlayer() {
		return target;
	}

	public ArrayList<Status> getStatuses() {
		return statuses;
	}
	
	
	// Setters:

	public void setTarget(Player target) {
		this.target = target;
	}
}