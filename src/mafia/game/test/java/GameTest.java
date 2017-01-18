package mafia.game.test.java;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import mafia.game.*;
import mafia.game.Player.PlayerFactory;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
	
	private Game testedGame;
	private String[] expectedPlayerNames = {"player 1", "player 2", "player 3"};
	private ArrayList<String> expectedPlayerNamesList;
	private ArrayList<String> actualPlayerNames;
	private ArrayList<Role> actualRoles;
	private ArgumentCaptor<String> capturePlayerNames = ArgumentCaptor.forClass(String.class);
	private ArgumentCaptor<Role> captureRoles = ArgumentCaptor.forClass(Role.class);
	
	
	@Mock
	private Role role_1;
	@Mock
	private Role role_2;
	@Mock
	private Role role_3;
	@Mock
	private PlayerFactory pf;
	@Mock
	private Player player_1;
	@Mock
	private Player player_2;
	@Mock
	private Player player_3;
	
	private ArrayList<Role> expectedRoles;

	@Before
	public void setUp() throws Exception {
		expectedRoles = new ArrayList<Role>();
		actualRoles = new ArrayList<Role>();
		actualPlayerNames = new ArrayList<String>();
		when(pf.createPlayer(ArgumentMatchers.anyString(), ArgumentMatchers.any(Role.class))).thenReturn(player_1, player_2, player_3);
		expectedRoles.add(role_1);
		expectedRoles.add(role_2);
		expectedRoles.add(role_3);
		testedGame = new Game(pf);
		testedGame.setPlayerNames(expectedPlayerNames);
		testedGame.setRoles(expectedRoles);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_assignRoles() {
		testedGame.assignRoles();
		verify(pf, times(3)).createPlayer(capturePlayerNames.capture(), captureRoles.capture());
		actualPlayerNames = new ArrayList<String>(capturePlayerNames.getAllValues());
		actualRoles = new ArrayList<Role>(captureRoles.getAllValues());
		expectedPlayerNamesList = new ArrayList<String>(Arrays.asList(expectedPlayerNames));
		assertTrue(expectedPlayerNamesList.containsAll(actualPlayerNames));
		assertTrue(actualPlayerNames.containsAll(expectedPlayerNamesList));
		assertTrue(actualRoles.containsAll(expectedRoles));
		assertTrue(expectedRoles.containsAll(actualRoles));
	}
	
	@Test
	public void test_performActions() {
		testedGame.assignRoles();
		testedGame.performActions();
		for ( Player player : testedGame.getPlayers()) {
			verify(player, times(1)).performAction();
		}
	}
}
