/**
 * 
 */
package mafia.game.test.java;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import mafia.game.*;
import mafia.values.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
	private ArrayList<Phase> validPhases = new ArrayList<Phase>();
	private String testedName = "TheNameOfTheAction";
	
	
	@Mock
	private Player mockedActor;
	@Mock
	private Player mockedTarget;
	@Mock
	private Player mockedTarget2;
	@Mock
	private Player mockedTarget3;
	@Mock
	private Player mockedTarget4;
	@Mock
	private Game mockedGame;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		validPhases.add(testedValidPhase);
		testedAction = new Action(Status.tobeKILLED, Status.DEAD, testedValidStatus, testedInvalidStatus, validPhases, mockedGame, testedName);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	@Test @Ignore
	public void test_execute() {
		// execute() does nothing in base class.
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedActor).addStatus(Status.tobeKILLED);
		verify(mockedTarget).addStatus(Status.DEAD);
	}
	
	@Test
	public void test_checkIfValidTarget() {
		
		// True case, Missing Valid Case, Has Invalid Case, Both Bad Case
		when(mockedTarget.hasStatus(testedValidStatus)).thenReturn(true, false, true, false);
		when(mockedTarget.hasStatus(testedInvalidStatus)).thenReturn(false, false, true, true);
		assertTrue("When target has valid status and not invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has neither valid status nor invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has valid status and invalid status", testedAction.checkIfValidTarget(mockedTarget));
		assertFalse("When target has invalid status and not valid status", testedAction.checkIfValidTarget(mockedTarget));
	}
	
	@Test
	public void test_checkValidPhase() {
		when(mockedGame.getPhase()).thenReturn(testedValidPhase, testedInvalidPhase);
		assertTrue("When game has the correct phase", testedAction.checkIfValidPhase());
		assertFalse("When game has the incorrect phase", testedAction.checkIfValidPhase());
	}
	
	@Test
	public void test_getValidTargets() {
		when(mockedGame.getPlayers()).thenReturn(new ArrayList<Player>(Arrays.asList(mockedTarget, mockedTarget2, mockedTarget3, mockedTarget4)));
		when(mockedTarget.hasStatus(testedValidStatus)).thenReturn(true);
		when(mockedTarget.hasStatus(testedInvalidStatus)).thenReturn(true);
		when(mockedTarget2.hasStatus(testedValidStatus)).thenReturn(true);
		when(mockedTarget2.hasStatus(testedInvalidStatus)).thenReturn(false);
		when(mockedTarget3.hasStatus(testedValidStatus)).thenReturn(false);
		when(mockedTarget3.hasStatus(testedInvalidStatus)).thenReturn(false);
		when(mockedTarget4.hasStatus(testedValidStatus)).thenReturn(true);
		when(mockedTarget4.hasStatus(testedInvalidStatus)).thenReturn(false);
		ArrayList<Player> actualValidTargets = testedAction.getValidTargets();
		ArrayList<Player> expectedValidTargets = new ArrayList<Player>();
		expectedValidTargets.add(mockedTarget2);
		expectedValidTargets.add(mockedTarget4);
		assertTrue(expectedValidTargets.containsAll(actualValidTargets));
		assertTrue(actualValidTargets.containsAll(expectedValidTargets));
	}
	
	@Test
	// Ensure properly overloaded.
	public void test_toString() {
		assertEquals(testedName, testedAction.toString());
	}
}
