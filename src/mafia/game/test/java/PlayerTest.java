package mafia.game.test.java;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

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
	
	@Mock
	private Role mockedRole;
	@Mock
	private Team mockedTeam;
	@Mock
	private ArrayList<Action> mockedActions;
	@Mock
	private Action mockedSelectedAction;
	@Mock
	private Player targetPlayer;
	
	@Before
	public void setUp() throws Exception {
		expectedStatus = new ArrayList<Status>();
		when(mockedRole.getTeam()).thenReturn(mockedTeam);
		when(mockedRole.getActions()).thenReturn(mockedActions);
		when(mockedTeam.getFaction()).thenReturn(Faction.TOWN);
		testedPlayer = new Player("testName", mockedRole);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void player_constructor() {
		// Verify appropriate methods were called:
		verify(mockedRole).getTeam();
		verify(mockedRole).getActions();
		verify(mockedTeam).addPlayer(testedPlayer);
		
		// Verify properties set properly:
		assertEquals(mockedTeam, testedPlayer.getTeam());
		assertEquals("testName", testedPlayer.getName());
		assertEquals(mockedActions, testedPlayer.getActions());

	}
	
	@Test
	public void test_getFaction() {
		assertEquals(Faction.TOWN, testedPlayer.getFaction());
		verify(mockedTeam).getFaction();
	}
	
	@Test
	public void test_performAction() {
		testedPlayer.setTarget(targetPlayer);
		testedPlayer.setSelectedAction(mockedSelectedAction);
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

}
