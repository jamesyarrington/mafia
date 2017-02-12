package mafia.factory;

import java.util.ArrayList;

import mafia.game.Action;
import mafia.game.Game;
import mafia.game.NoAction;
import mafia.game.Role;
import mafia.values.Faction;
import mafia.values.RoleOption;

public class RoleFactory {
	
	public static Role createRole(RoleOption roleName, Game game) {
		ArrayList<Action> actions = new ArrayList<Action>();
		
		actions.add(new NoAction(game));
		
		switch (roleName) {
		case TOWNSPERSON:	return new Role(Faction.TOWN, actions, "Townsperson");
		case GOON:			return new Role(Faction.MAFIA, actions, "Goon");
		default:			return null;
		}
	}
}