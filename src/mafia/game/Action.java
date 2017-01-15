package mafia.game;

import mafia.values.Status;

public class Action {
	private Status targetStatus;
	private Status actorStatus;
	
	public Action(Status startActorStatus, Status startTargetStatus) {
		targetStatus = startTargetStatus;
		actorStatus = startActorStatus;
	}
	
	
	// Apply the appropriate status to the player performing the action, and that player's target.
	public void execute(Player actor, Player target) {
		actor.addStatus(actorStatus);
		target.addStatus(targetStatus);
	}
}