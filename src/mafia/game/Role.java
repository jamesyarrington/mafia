package mafia.game;

import java.util.ArrayList;

public class Role {
	private Team team;
	private ArrayList<Action> actions;
	
	public Role(Team startTeam, ArrayList<Action> startActions) {
		team = startTeam;
		actions = startActions;
	}

	
	// Getters:
	
	public Team getTeam() {
		return team;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}
}
