package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;

public class Role {
	private Faction faction;
	private ArrayList<Action> actions;
	
	public Role(Faction faction, ArrayList<Action> actions) {
		this.faction = faction;
		this.actions = actions;
	}
	
	// To be overwritten by the invalid Role.
	public boolean isValid() {
		return true;
	}

	
	// Getters:
	
	public Faction getFaction() {
		return faction;
	}

	public ArrayList<Action> getActions() {
		return actions;
	}
}
