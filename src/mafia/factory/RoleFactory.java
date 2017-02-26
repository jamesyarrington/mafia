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
		actions.add(Vote.builder(game)
				.name("Vote")
				.invalidTargetStatus(Status.DEAD)
				.addValidPhase(Phase.DAY)
				.build());
		
		switch (roleName) {
		case TOWNSPERSON:
			name = "Townsperson";
			faction = Faction.TOWN;
			break;
		case GOON:
			name = "Goon";
			faction = Faction.MAFIA;
			actions.add(SetStatus.builder(game)
					.name("Kill")
					.invalidTargetStatus(Status.DEAD)
					.addValidPhase(Phase.NIGHT)
					.targetStatus(Status.tobeKILLED)
					.build());
			break;
		default:
		}
		return new Role(faction, actions, name);
	}
}