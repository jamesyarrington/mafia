package mafia.game.subclasses.action.test.java;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import mafia.game.subclasses.action.SetStatus;
import mafia.game.subclasses.action.Vote;
import mafia.game.test.java.ActionTest;
import mafia.values.Status;

@RunWith(MockitoJUnitRunner.class)
public class SetStatusTest extends ActionTest {
	protected Status testedActorStatus = Status.BLOCKED;
	protected Status testedTargetStatus = Status.tobeKILLED;
	
	@Before @Override
	public void setUp() {
		validPhases.add(testedValidPhase);
		testedAction = SetStatus.builder(mockedGame)
				.actorStatus(testedActorStatus)
				.targetStatus(testedTargetStatus)
				.invalidTargetStatus(testedInvalidStatus)
				.validTargetStatus(testedValidStatus)
				.name(testedName)
				.addValidPhase(testedValidPhase)
				.build();
	}
	
	@Test @Override
	public void test_execute() {
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedTarget).addStatus(testedTargetStatus);
		verify(mockedActor).addStatus(testedActorStatus);
	}

}
