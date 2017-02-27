package mafia.game.test.java;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import mafia.game.*;
import mafia.values.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerTest {
	
	private Player testedPlayer;
	private ArrayList<Status> expectedStatus;
	private ArrayList<Player> expectedValidTargets;
	private ArrayList<Player> actualValidTargets;
	private ArrayList<Action> mockedActions;
	
	@Mock
	private Role mockedRole;	
	@Mock
	private Game mockedGame;
	@Mock
	private Action mockedSelectedAction;
	@Mock
	private Player targetPlayer;
	@Mock
	private Player potentialTarget_1;
	@Mock
	private Player potentialTarget_2;
	@Mock
	private Player potentialTarget_3;
	@Mock
	private Action action1;
	@Mock
	private Action action2;
	@Mock
	private Action action3;
	
	@Before
	public void setUp() throws Exception {
		expectedStatus = new ArrayList<Status>();
		mockedActions = new ArrayList<Action>();
		mockedActions.add(action1);
		mockedActions.add(action2);
		mockedActions.add(action3);
		when(mockedRole.getActions()).thenReturn(mockedActions);
		testedPlayer = new Player("testName", mockedRole, mockedGame);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void player_constructor() {
		verify(mockedRole).getActions();
		assertEquals("testName", testedPlayer.getName());
		assertEquals(mockedActions, testedPlayer.getActions());

	}
	
	@Test
	public void test_performAction() {
		when(mockedSelectedAction.checkIfValidTarget(targetPlayer)).thenReturn(true);
		testedPlayer.chooseAction(mockedSelectedAction, targetPlayer);
		testedPlayer.performAction();
		verify(mockedSelectedAction).execute(testedPlayer, targetPlayer);
	}
	
	@Test
	public void test_status_methods() {
		
		expectedStatus.add(Status.NONE);
		expectedStatus.add(Status.tobeKILLED);
		testedPlayer.addStatus(Status.NONE);
		testedPlayer.addStatus(Status.tobeKILLED);
		assertEquals(expectedStatus, testedPlayer.getStatuses());
		
		assertTrue(testedPlayer.removeStatus(Status.NONE));
		expectedStatus.remove(Status.NONE);
		assertFalse(testedPlayer.removeStatus(Status.BLOCKED));
		assertEquals(expectedStatus, testedPlayer.getStatuses());
		
		assertTrue(testedPlayer.hasStatus(Status.tobeKILLED));
		assertFalse(testedPlayer.hasStatus(Status.NONE));
	}
	
	@Test
	public void test_chooseAction() {
		when(mockedSelectedAction.checkIfValidTarget(targetPlayer)).thenReturn(false, true);
		assertFalse("When action reports invalid", testedPlayer.chooseAction(mockedSelectedAction, targetPlayer));
		assertEquals("When action reports invalid", testedPlayer.getSelectedAction(), null);
		assertEquals("When action reports invalid", testedPlayer.getTarget(), null);
		assertTrue("When action reports valid", testedPlayer.chooseAction(mockedSelectedAction, targetPlayer));
		assertEquals("When action reports valid", testedPlayer.getSelectedAction(), mockedSelectedAction);
		assertEquals("When action reports valid", testedPlayer.getTarget(), targetPlayer);
	}
	
	@Test
	public void test_getValidActions() {
		when(action1.checkIfValidPhase()).thenReturn(false);
		when(action2.checkIfValidPhase()).thenReturn(true);
		when(action3.checkIfValidPhase()).thenReturn(true);
		ArrayList<Action> expectedValidActions = new ArrayList<Action>();
		expectedValidActions.add(action2);
		expectedValidActions.add(action3);
		ArrayList<Action> actualValidActions = testedPlayer.getValidActions();
		assertTrue(expectedValidActions.containsAll(actualValidActions));
		assertTrue(actualValidActions.containsAll(expectedValidActions));
	}
	
	@Test
	public void test_votes() {
		assertEquals(testedPlayer.getVotes(), 0);
		testedPlayer.applyVote();
		assertEquals(testedPlayer.getVotes(), 1);
		testedPlayer.applyVote();
		testedPlayer.applyVote();
		assertEquals(testedPlayer.getVotes(), 3);
		testedPlayer.clearVotes();
		assertEquals(testedPlayer.getVotes(), 0);
	}
}
