package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;

public class Role {
	private Faction faction;
	private ArrayList<Action> actions;
	private String name;
	
	public Role(Faction faction, ArrayList<Action> actions, String name) {
		this.faction = faction;
		this.actions = actions;
		this.name = name;
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
	
	@Override
	public String toString() {
		return name;
	}
}
