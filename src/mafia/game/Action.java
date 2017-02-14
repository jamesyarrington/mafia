package mafia.game;

import java.util.ArrayList;

import mafia.values.Phase;
import mafia.values.Status;

public class Action {
	protected String name;
	protected Status validTargetStatus;
	protected Status invalidTargetStatus;
	protected ArrayList<Phase> validPhases;
	protected Status targetStatus;
	protected Status actorStatus;
	protected Game game;
	
	public Action(Status actorStatus, Status targetStatus, Status validTargetStatus, Status invalidTargetStatus, ArrayList<Phase> validPhases, Game game, String name) {
		this.actorStatus = actorStatus;
		this.targetStatus = targetStatus;
		this.validTargetStatus = validTargetStatus;
		this.invalidTargetStatus = invalidTargetStatus;
		this.validPhases = validPhases;
		this.game = game;
		this.name = name;
	}
	public Action(Game game) {
		this.game = game;
	}
	
	
	// To be overwritten.
	public void execute(Player actor, Player target) {
		// Do nothing.  This is overwritten in subclasses.
	}
	
	// Return true if the player is a valid target for this action.
	public boolean checkIfValidTarget(Player target) {
		boolean chkValidStatus;
		boolean chkInvalidStatus;
		if (validTargetStatus == null) {
			chkValidStatus = true;
		} else {
			chkValidStatus = target.hasStatus(validTargetStatus);
		}
		if (invalidTargetStatus == null) {
			chkInvalidStatus = true;
		} else {
			chkInvalidStatus = !target.hasStatus(invalidTargetStatus);
		}
		return chkValidStatus && chkInvalidStatus;
	}
	
	// Return an ArrayList of players that can be a valid target of this action.
	public ArrayList<Player> getValidTargets() {
		ArrayList<Player> returnedPlayerArrayList = new ArrayList<Player>();
		for (Player player : game.getPlayers()) {
			if (checkIfValidTarget(player)) {
				returnedPlayerArrayList.add(player);
			}
		}
		return returnedPlayerArrayList;
	}
	
	// Return true if the game's phase is a valid phase for this action.
	public boolean checkIfValidPhase() {
		return validPhases.contains(game.getPhase());
	}

	@Override
	public String toString() {
		return name;
	}
}