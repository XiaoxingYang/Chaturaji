package chaturaji.server.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import chaturaji.communication.Protocol;
import chaturaji.game.GameFactory;
import chaturaji.game.GameServer;

public class GameFactoryTest {

	private static GameFactory factory = GameFactory.getInstance();
	private static final String name = "Default0";
	
	@Test
	public void testSingleton() {
		assertNotNull("Testing: Getting the instance of the factory", factory);
		assertEquals("Testing: Singleton", factory, GameFactory.getInstance());
	}
	

	@Test
	public void testFindGame() {
		Protocol p = mock(Protocol.class);
		GameServer server = factory.findGame("");
		server.test = true;
		assertNotNull("Testing: Always returns an instance of game", server);
		assertEquals("Testing: Server in pending mode", factory.pendingGames().size(), 1);
		for (int i = 0; i < 4; ++i) {
			server.createPlayer(p);
			factory.findGame("");
		}
		assertNotNull("Testing: Always returns an instance of game", server);
		assertEquals("Testing: Only one game pending as the other one is full", factory.pendingGames().size(), 1);
		assertEquals("Testing: One game was created", factory.createdGames().size(), 1);
	}

	@Test
	public void testCreateGame() {
		Protocol p = mock(Protocol.class);
		GameServer server = factory.createGame("test");
		server.test = true;
		assertNotNull("Testing: Creation of the game", server);
		assertEquals("Testing: Server is empty", server.numberOfPlayers(), 0);
		assertEquals("Testing: Name of the server", "test", server.getName());
		assertEquals("Testing: Server in pending mode", factory.pendingGames().size(), 2);
	}
	
}
