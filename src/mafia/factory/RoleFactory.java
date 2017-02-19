package mafia.factory;

import java.util.ArrayList;

import mafia.game.Action;
import mafia.game.Game;
import mafia.game.Role;
import mafia.game.subclasses.action.*;
import mafia.values.*;

public class RoleFactory {
	static String name;
	static Faction faction;
	static ArrayList<Action> actions;
	
	public static Role createRole(RoleOption roleName, Game game) {
		actions = new ArrayList<Action>();
		actions.add(new NoAction(game));
		
		switch (roleName) {
		case TOWNSPERSON:
			name = "Townsperson";
			faction = Faction.TOWN;
			break;
		case GOON:
			name = "Goon";
			faction = Faction.MAFIA;
			actions.add(StatusSetter.builder(game).
					name("Kill").
					targetStatus(Status.tobeKILLED).
					invalidTargetStatus(Status.DEAD).
					addValidPhase(Phase.NIGHT).build());
			break;
		default:
		}
		return new Role(faction, actions, name);
	}
}