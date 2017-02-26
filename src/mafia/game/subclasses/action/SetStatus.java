package mafia.game.subclasses.action;

import java.util.ArrayList;

import mafia.game.Action;
import mafia.game.Game;
import mafia.game.Player;
import mafia.values.Phase;
import mafia.values.Status;

public class SetStatus extends Action {

	public SetStatus(Status actorStatus, Status targetStatus, Status validTargetStatus, Status invalidTargetStatus,
			ArrayList<Phase> validPhases, Game game, String name) {
		super(actorStatus, targetStatus, validTargetStatus, invalidTargetStatus, validPhases, game, name);
	}
	
	@Override
	public void execute(Player actor, Player target) {
		target.addStatus(targetStatus);
		actor.addStatus(actorStatus);
	}
	public static StatusSetterBuilder builder(Game game) {
		return new StatusSetterBuilder(game);
	}
	public static class StatusSetterBuilder extends Builder {

		protected StatusSetterBuilder(Game game) {
			super(game);
		}
		@Override
		public Action build() {
			return new SetStatus(actorStatus, targetStatus, validTargetStatus, invalidTargetStatus, validPhases, game, name);
		}
		
		// New Variable Setters
		public StatusSetterBuilder actorStatus(Status actorStatus) {
			this.actorStatus = actorStatus;
			return this;
		}
		public StatusSetterBuilder targetStatus(Status targetStatus) {
			this.targetStatus = targetStatus;
			return this;
		}
		
		// Overridden Variable Setters
		@Override
		public StatusSetterBuilder validTargetStatus(Status validTargetStatus) {
			this.validTargetStatus = validTargetStatus;
			return this;
		}
		@Override
		public StatusSetterBuilder invalidTargetStatus(Status invalidTargetStatus) {
			this.invalidTargetStatus = invalidTargetStatus;
			return this;
		}
		@Override
		public StatusSetterBuilder addValidPhase(Phase phase) {
			validPhases.add(phase);
			return this;
		}
		@Override
		public StatusSetterBuilder name(String name) {
			this.name = name;
			return this;
		}
	}
	
}
