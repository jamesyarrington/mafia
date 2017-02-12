/**
 * 
 */
package mafia.game.test.java;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import mafia.game.*;
import mafia.values.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class ActionTest {
	
	private Action testedAction;

	private Status testedValidStatus = Status.BLOCKED;
	private Status testedInvalidStatus = Status.tobeKILLED;
	private Phase testedValidPhase = Phase.NIGHT;
	private Phase testedInvalidPhase = Phase.DAY;
	
	
	@Mock
	private Player mockedActor;
	@Mock
	private Player mockedTarget;
	@Mock
	private Game mockedGame;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testedAction = new Action(Status.tobeKILLED, Status.DEAD, testedValidStatus, testedInvalidStatus, testedValidPhase, mockedGame);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test_execute() {
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedActor).addStatus(Status.tobeKILLED);
		verify(mockedTarget).addStatus(Status.DEAD);
	}
	
	@Test
	public void test_checkIfValid() {
		
		// True case, Missing Valid Case, Has Invalid Case, Both Bad Case
		when(mockedTarget.hasStatus(testedValidStatus)).thenReturn(true, false, true, false, true);
		when(mockedTarget.hasStatus(testedInvalidStatus)).thenReturn(false, false, true, true, false);
		when(mockedGame.getPhase()).thenReturn(testedValidPhase);
		assertTrue("When target has valid status and not invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has neither valid status nor invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has valid status and invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has invalid status and not valid status", testedAction.checkIfValidTarget(mockedTarget));
		when(mockedGame.getPhase()).thenReturn(testedInvalidPhase);
		assertFalse("When game is in invalid phase for action", testedAction.checkIfValidTarget(mockedTarget));
		
	}

}
