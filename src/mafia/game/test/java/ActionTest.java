/**
 * 
 */
package mafia.game.test.java;

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
	
	@Mock
	private Player mockedActor;
	@Mock
	private Player mockedTarget;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testedAction = new Action(Status.tobeKILLED, Status.DEAD);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void actor_status_updated_after_execute() throws Exception{
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedActor).addStatus(Status.tobeKILLED);
	}
	
	@Test
	public void target_status_updated_after_execute() throws Exception {
		testedAction.execute(mockedActor, mockedTarget);
		verify(mockedTarget).addStatus(Status.DEAD);
	}

}
