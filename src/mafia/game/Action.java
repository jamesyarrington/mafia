package mafia.game;

import mafia.values.Phase;
import mafia.values.Status;

public class Action {
	private String name;
	private Status validTargetStatus;
	private Status invalidTargetStatus;
	private Phase validPhase;
	private Status targetStatus;
	private Status actorStatus;
	private Game game;
	
	public Action(Status actorStatus, Status targetStatus, Status validTargetStatus, Status invalidTargetStatus, Phase validPhase, Game game) {
		this.actorStatus = actorStatus;
		this.targetStatus = targetStatus;
		this.validTargetStatus = validTargetStatus;
		this.invalidTargetStatus = invalidTargetStatus;
		this.validPhase = validPhase;
		this.game = game;
	}
	
	
	// Apply the appropriate status to the player performing the action, and that player's target.
	public void execute(Player actor, Player target) {
		actor.addStatus(actorStatus);
		target.addStatus(targetStatus);
	}
	
	// Return true if the player is a valid target for this action.
	public boolean checkIfValid(Player target) {
		boolean chkValidStatus;
		boolean chkInvalidStatus;
		boolean chkValidPhase;
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
		chkValidPhase = game.getPhase() == validPhase;
		return chkValidStatus && chkInvalidStatus && chkValidPhase;
	}


	public String getName() {
		return name;
	}
}