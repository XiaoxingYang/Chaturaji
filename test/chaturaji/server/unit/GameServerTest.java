package chaturaji.server.unit;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import chaturaji.communication.Protocol;
import chaturaji.game.Color;
import chaturaji.game.GameServer;
import chaturaji.game.Player;

public class GameServerTest {

	private GameServer server;
	private Protocol protocol;
	
	@Test
	public void testCreateGame() {
		server = new GameServer();
		server.test = true;
		protocol = mock(Protocol.class);
		assertFalse("Testing: Game server not in playing mode", server.isPlaying());
		assertNotNull("Testing: Can create a player", server.createPlayer(protocol));
		assertEquals("Testing: Number of players is 1", server.numberOfPlayers(), 1);
		assertTrue("Testing: Default name generated", server.getName().contains("Default"));
	}

	@Test
	public void testCreateGameWithName() {
		String name = "TestingGameServer";
		server = new GameServer(name);
		server.test = true;
		protocol = mock(Protocol.class);
		assertFalse("Testing: Game server not in playing mode", server.isPlaying());
		assertNotNull("Testing: Can create a player", server.createPlayer(protocol));
		assertEquals("Testing: Number of players is 1", server.numberOfPlayers(), 1);
		assertTrue("Testing: Game server given the right name", server.getName().equals(name));
	}
	
	@Test
	public void testGetPlayer() {
		server = new GameServer();
		server.test = true;
		protocol = mock(Protocol.class);
		Player p = server.createPlayer(protocol);
		assertNotNull("Testing: Player who's color is red exists", server.getPlayer(Color.RED));
		assertEquals("Testing: Player retrieved is the same as the one created", server.getPlayer(Color.RED), p);
		assertNull("Testing: Player GREEN hasn't joined yet", server.getPlayer(Color.GREEN));
		assertNull("Testing: Player YELLOW hasn't joined yet", server.getPlayer(Color.YELLOW));
		assertNull("Testing: Player BLACK hasn't joined yet", server.getPlayer(Color.BLACK));
	}
	
	@Test
	public void testStartGame() {
		server = new GameServer();
		server.test = true;
		for (int i = 0; i < 4; ++i) {
			protocol = mock(Protocol.class);
			assertNotNull("Testing: Creation of player number" + i, server.createPlayer(protocol));
			assertEquals("Testing: Number of players grows", server.numberOfPlayers(), i+1);
		}
		assertEquals("Testing: Player 1 attached to the server", server, server.getPlayer(Color.RED).attachedServer());
		assertEquals("Testing: Player 2 attached to the server", server, server.getPlayer(Color.GREEN).attachedServer());
		assertEquals("Testing: Player 3 attached to the server", server, server.getPlayer(Color.YELLOW).attachedServer());
		assertEquals("Testing: Player 4 attached to the server", server, server.getPlayer(Color.BLACK).attachedServer());
		assertEquals("Testing: Server is full", server.numberOfPlayers(), 4);
		assertTrue("Testing: Server is in playing mode", server.isPlaying());
		assertNull("Testing: Can not add a new player", server.createPlayer(protocol));
		Player p = server.currentPlayer();
		assertTrue("Testing: First player is red", p.getColor().equals(Color.RED));
	}
	
	@Test
	public void testDeletePlayer() {
		server = new GameServer();
		server.test = true;
		protocol = mock(Protocol.class);
		Player p = server.createPlayer(protocol);
		assertTrue("Testing: Delete existing player", server.deletePlayer(p));
		assertFalse("Testing: Can't delete non existing player", server.deletePlayer(p));
		assertEquals(server.numberOfPlayers(), 0);
	}
	
	@Test
	public void testChangeName() {
		server = new GameServer();
		server.test = true;
		server.setName("TestName");
		assertEquals(server.getName(), "TestName");
	}

}
