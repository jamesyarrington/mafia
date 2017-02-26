package mafia.game.subclasses.action;

import java.util.ArrayList;

import mafia.game.Action;
import mafia.game.Game;
import mafia.game.Player;
import mafia.values.Phase;
import mafia.values.Status;

public class Vote extends Action {

	public Vote(Status validTargetStatus, Status invalidTargetStatus, ArrayList<Phase> validPhases, Game game,
			String name) {
		super(validTargetStatus, invalidTargetStatus, validPhases, game, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Player actor, Player target) {
		target.applyVote();
	}
	
	public static VoteBuilder builder(Game game) {
		return new VoteBuilder(game);
	}
	public static class VoteBuilder extends Builder {

		protected VoteBuilder(Game game) {
			super(game);
		}
		@Override
		public Action build() {
			return new Vote(validTargetStatus, invalidTargetStatus, validPhases, game, name);
		}
		
		// Overridden Variable Setters
		@Override
		public VoteBuilder validTargetStatus(Status validTargetStatus) {
			this.validTargetStatus = validTargetStatus;
			return this;
		}
		@Override
		public VoteBuilder invalidTargetStatus(Status invalidTargetStatus) {
			this.invalidTargetStatus = invalidTargetStatus;
			return this;
		}
		@Override
		public VoteBuilder addValidPhase(Phase phase) {
			validPhases.add(phase);
			return this;
		}
		@Override
		public VoteBuilder name(String name) {
			this.name = name;
			return this;
		}
	}
}
