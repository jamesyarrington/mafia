package mafia.game;

import java.util.ArrayList;

import mafia.values.Faction;
import mafia.values.RoleOption;

public class RoleFactory {
	
	public static Role createRole(RoleOption roleName) {
		ArrayList<Action> actions = new ArrayList<Action>();
		
		switch (roleName) {
		case TOWNSPERSON:	return new Role(Faction.TOWN, actions);
		case GOON:			return new Role(Faction.MAFIA, actions);
		default:			return null;
		}
	}
}
