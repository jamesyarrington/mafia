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
	public Action(Status validTargetStatus, Status invalidTargetStatus, ArrayList<Phase> validPhases, Game game, String name) {
		this.validTargetStatus = validTargetStatus;
		this.invalidTargetStatus = invalidTargetStatus;
		this.validPhases = validPhases;
		this.game = game;
		this.name = name;
	}
	
	public static Builder builder(Game game) {
		return new Builder(game);
	}
	public static class Builder {
		protected Status actorStatus;
		protected Status targetStatus;
		protected Status validTargetStatus;
		protected Status invalidTargetStatus;
		protected ArrayList<Phase> validPhases;
		protected String name;
		protected Game game;
		
		protected Builder(Game game) {
			this.game = game;
			actorStatus = Status.NONE;
			targetStatus = Status.NONE;
			validTargetStatus = Status.NONE;
			invalidTargetStatus = Status.NONE;
			
			name = "Generic Action";
			
			validPhases = new ArrayList<Phase>();
		}
		
		public Action build() {
			return new Action(actorStatus, targetStatus, validTargetStatus, invalidTargetStatus, validPhases, game, name);
		}
		
		// Variable setters
		public Builder validTargetStatus(Status validTargetStatus) {
			this.validTargetStatus = validTargetStatus;
			return this;
		}
		public Builder invalidTargetStatus(Status invalidTargetStatus) {
			this.invalidTargetStatus = invalidTargetStatus;
			return this;
		}
		public Builder addValidPhase(Phase phase) {
			validPhases.add(phase);
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
	}
	
	
	// To be overwritten.
	public void execute(Player actor, Player target) {
		// Do nothing.  This is overwritten in subclasses.
	}
	
	// Return true if the player is a valid target for this action.
	public boolean checkIfValidTarget(Player target) {
		boolean chkValidStatus;
		boolean chkInvalidStatus;
		if (validTargetStatus == Status.NONE) {
			chkValidStatus = true;
		} else {
			chkValidStatus = target.hasStatus(validTargetStatus);
		}
		if (invalidTargetStatus == Status.NONE) {
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