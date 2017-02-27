package mafia.game.subclasses.action.test.java;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import mafia.game.Action;
import mafia.game.subclasses.action.Vote;
import mafia.game.test.java.ActionTest;

@RunWith(MockitoJUnitRunner.class)
public class VoteTest extends ActionTest {
	
	@Before @Override
	public void setUp() {
		validPhases.add(testedValidPhase);
		testedAction = Vote.builder(mockedGame)
				.invalidTargetStatus(testedInvalidStatus)
				.validTargetStatus(testedValidStatus)
				.name(testedName)
				.addValidPhase(testedValidPhase)
				.build();
	}
	
	@Test
	public void test_execute() {
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedTarget, times(1)).applyVote();
		verify(mockedActor, times(0)).applyVote();
	}
}
