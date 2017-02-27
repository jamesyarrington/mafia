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
	
	protected Action testedAction;

	protected Status testedValidStatus = Status.BLOCKED;
	protected Status testedInvalidStatus = Status.tobeKILLED;
	protected Phase testedValidPhase = Phase.NIGHT;
	protected Phase testedInvalidPhase = Phase.DAY;
	protected ArrayList<Phase> validPhases = new ArrayList<Phase>();
	protected String testedName = "TheNameOfTheAction";
	
	
	@Mock
	protected Player mockedActor;
	@Mock
	protected Player mockedTarget;
	@Mock
	protected Player mockedTarget2;
	@Mock
	protected Player mockedTarget3;
	@Mock
	protected Player mockedTarget4;
	@Mock
	protected Game mockedGame;

	@Before
	public void setUp() {
		validPhases.add(testedValidPhase);
		testedAction = Action.builder(mockedGame)
				.invalidTargetStatus(testedInvalidStatus)
				.validTargetStatus(testedValidStatus)
				.name(testedName)
				.addValidPhase(testedValidPhase)
				.build();
	}

	@After
	public void tearDown() {
		
	}

	@Test
	public void test_execute() {
		// execute() does nothing in base class.
		testedAction.execute(mockedActor, mockedTarget);
		verifyZeroInteractions(mockedActor);
		verifyZeroInteractions(mockedTarget);
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
