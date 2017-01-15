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
import mafia.values.Faction;

@RunWith(MockitoJUnitRunner.class)
public class TeamTest {
	
	@Mock
	private Player mockPlayer_1;
	@Mock
	private Player mockPlayer_2;
	
	private Team testedTeam;
	private ArrayList<Player> expectedPlayers;

	@Before
	public void setUp() throws Exception {
		testedTeam = new Team(Faction.TOWN);
		expectedPlayers = new ArrayList<Player>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void team_constructor() {
		assertEquals(Faction.TOWN, testedTeam.getFaction());
	}
	
	@Test
	public void test_addPlayer() {
		expectedPlayers.add(mockPlayer_1);
		expectedPlayers.add(mockPlayer_2);
		testedTeam.addPlayer(mockPlayer_1);
		testedTeam.addPlayer(mockPlayer_2);
		assertEquals(expectedPlayers, testedTeam.getPlayers());
	}
}
