package mafia.game;

import java.util.ArrayList;

public class NoAction extends Action {

	public NoAction(Game game) {
		super(game);
	}

	@Override
	public void execute(Player actor, Player target) {
		// Do nothing.
	}
	
	@Override
	public ArrayList<Player> getValidTargets() {
		ArrayList<Player> no_choices =  new ArrayList<Player>();
		no_choices.add(new NoPlayer());
		return no_choices;
	}
	
	@Override
	public boolean checkIfValidPhase() {
		// "No action"  Should always be an option.
		return true;
	}
	
	@Override
	public String toString() {
		return "Do nothing.";
	}
}
