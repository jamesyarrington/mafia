package mafia.game.test.java;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import mafia.game.*;

@RunWith(MockitoJUnitRunner.class)
public class RoleTest {
	
	private Role testedRole;
	
	@Mock
	private Team mockedTeam;
	@Mock
	private ArrayList<Action> mockedActions;

	@Before
	public void setUp() throws Exception {
		testedRole = new Role(mockedTeam, mockedActions);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void role_constructor() {
		assertEquals(mockedActions, testedRole.getActions());
		assertEquals(mockedTeam, testedRole.getTeam());
	}

}
