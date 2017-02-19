package mafia.game.subclasses.action;

import java.util.ArrayList;

import mafia.game.Action;
import mafia.game.Game;
import mafia.game.NoPlayer;
import mafia.game.Player;
import mafia.values.Phase;

public class NoAction extends Action {

	public NoAction(Game game) {
		super(game);
		name = "Do nothing.";
		validPhases = new ArrayList<Phase>();
		validPhases.add(Phase.DAY);
		validPhases.add(Phase.NIGHT);
	}
	
	@Override
	public ArrayList<Player> getValidTargets() {
		ArrayList<Player> no_choices =  new ArrayList<Player>();
		no_choices.add(new NoPlayer("No Target"));
		return no_choices;
	}
	
	@Override
	public boolean checkIfValidTarget(Player player) {
		return true;
	}
}