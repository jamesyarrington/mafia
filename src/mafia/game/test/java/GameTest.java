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

import mafia.factory.PlayerFactory;
import mafia.game.*;
import mafia.values.Faction;
import mafia.values.Phase;
import mafia.values.Status;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
	
	private Game testedGame;
	private String[] expectedPlayerNames = {"player 1", "player 2", "player 3"};
	private ArrayList<String> expectedPlayerNamesList;
	private ArrayList<String> actualPlayerNames;
	private ArrayList<Role> actualRoles;
	private ArgumentCaptor<String> capturePlayerNames = ArgumentCaptor.forClass(String.class);
	private ArgumentCaptor<Role> captureRoles = ArgumentCaptor.forClass(Role.class);
	private ArgumentCaptor<Game> captureGame = ArgumentCaptor.forClass(Game.class);
		
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
		when(pf.createPlayer(ArgumentMatchers.anyString(), ArgumentMatchers.any(Role.class), ArgumentMatchers.any(Game.class))).thenReturn(player_1, player_2, player_3);
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
		verify(pf, times(3)).createPlayer(capturePlayerNames.capture(), captureRoles.capture(), captureGame.capture());
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
	
	@Test
	public void test_clearVotes() {
		testedGame.assignRoles();
		testedGame.clearVotes();
		for ( Player player : testedGame.getPlayers()) {
			verify(player, times(1)).clearVotes();
		}
	}
	
	@Test
	public void test_advance() {
		assertEquals("Starts at 0", 0, testedGame.getTurn());
		assertEquals("Starts with NIGHT", Phase.NIGHT, testedGame.getPhase());
		testedGame.advance();
		assertEquals("After one advance", 1, testedGame.getTurn());
		assertEquals("After one advance", Phase.DAY, testedGame.getPhase());
		for (int i=0; i<5; i++) {
			testedGame.advance();
		}
		assertEquals("After six advances", 3, testedGame.getTurn());
		assertEquals("After six advances", Phase.NIGHT, testedGame.getPhase());
	}
	
	@Test
	public void test_killPlayers() {
		testedGame.assignRoles();
		when(player_1.removeStatus(Status.tobeKILLED)).thenReturn(true);
		when(player_2.removeStatus(Status.tobeKILLED)).thenReturn(false);
		when(player_3.removeStatus(Status.tobeKILLED)).thenReturn(true);
		assertEquals(new ArrayList<Player>(Arrays.asList(player_1, player_3)), testedGame.killPlayers());
		verify(player_1).addStatus(Status.DEAD);
		verify(player_3).addStatus(Status.DEAD);
		verify(player_1).removeStatus(Status.tobeKILLED);
		verify(player_3).removeStatus(Status.tobeKILLED);
		verify(player_2, never()).addStatus(Status.DEAD);
	}
	
	@Test
	public void test_getTopVoted() {
		testedGame.assignRoles();
		when(player_1.getVotes()).thenReturn(0, 0, 2);
		when(player_2.getVotes()).thenReturn(3, 0, 1);
		when(player_3.getVotes()).thenReturn(1, 0, 2);
		// One winner:
		assertEquals(player_2, testedGame.getTopVoted());
		// No Votes:
		assertTrue(testedGame.getTopVoted() instanceof NoPlayer);
		// Tie:
		assertTrue(testedGame.getTopVoted() instanceof NoPlayer);
	}	
	
	@Test
	public void test_killPlayers_VOTED() {
		testedGame.assignRoles();
		when(player_1.getVotes()).thenReturn(0, 0, 2);
		when(player_2.getVotes()).thenReturn(3, 0, 1);
		when(player_3.getVotes()).thenReturn(1, 0, 2);
		// One winner:
		assertEquals(new ArrayList<Player>(Arrays.asList(player_2)), testedGame.killPlayers());
		verify(player_2).addStatus(Status.DEAD);
		verify(player_2).removeStatus(Status.tobeKILLED);
		// No Votes:
		assertTrue(testedGame.killPlayers().isEmpty());
		// Tie:
		assertTrue(testedGame.killPlayers().isEmpty());
	}
	
	@Test
	public void test_getLivingPlayers() {
		testedGame.assignRoles();
		when(player_1.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_2.hasStatus(Status.DEAD)).thenReturn(true);
		when(player_3.hasStatus(Status.DEAD)).thenReturn(false);
		assertEquals(new ArrayList<Player>(Arrays.asList(player_1, player_3)), testedGame.getLivingPlayers());
	}
	
	@Test
	public void test_getPlayerCount_Faction() {
		testedGame.assignRoles();
		when(player_1.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_2.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_3.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_1.getFaction()).thenReturn(Faction.MAFIA);
		when(player_2.getFaction()).thenReturn(Faction.TOWN);
		when(player_3.getFaction()).thenReturn(Faction.MAFIA);
		assertEquals(2, testedGame.getPlayerCount(Faction.MAFIA));
		assertEquals(1, testedGame.getPlayerCount(Faction.TOWN));
	}
	
	@Test
	public void test_checkForWinner() {
		testedGame.assignRoles();
		when(player_1.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_2.hasStatus(Status.DEAD)).thenReturn(false);
		when(player_3.hasStatus(Status.DEAD)).thenReturn(false);
		
		// Town wins:
		when(player_1.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		when(player_2.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		when(player_3.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		assertEquals("3T - 0M, town wins", Faction.TOWN, testedGame.checkForWinner());
		
		// Mafia wins:
		when(player_1.getFaction()).thenReturn(Faction.MAFIA, Faction.MAFIA);
		when(player_2.getFaction()).thenReturn(Faction.MAFIA, Faction.MAFIA);
		when(player_3.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		assertEquals("1T - 2M, mafia wins", Faction.MAFIA, testedGame.checkForWinner());
		
		// No winners yet:
		when(player_1.getFaction()).thenReturn(Faction.MAFIA, Faction.MAFIA);
		when(player_2.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		when(player_3.getFaction()).thenReturn(Faction.TOWN, Faction.TOWN);
		assertEquals("2T - 1M, no winners", null, testedGame.checkForWinner());
		
		// Everyone dead:
		when(player_1.hasStatus(Status.DEAD)).thenReturn(true);
		when(player_2.hasStatus(Status.DEAD)).thenReturn(true);
		when(player_3.hasStatus(Status.DEAD)).thenReturn(true);
		assertEquals("0T - 0M, no winners", Faction.NONE, testedGame.checkForWinner());
	}
}
