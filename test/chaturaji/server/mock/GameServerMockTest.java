package chaturaji.server.mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import chaturaji.communication.Message;
import chaturaji.communication.Protocol;
import chaturaji.communication.WSCommunication;
import chaturaji.game.GameFactory;
import chaturaji.game.GameServer;
import chaturaji.game.Player;

@PowerMockIgnore("javax.management.MBeanServer")
@RunWith(PowerMockRunner.class)
@PrepareForTest(GameServer.class)
public class GameServerMockTest {
	
	private static final GameFactory factory = GameFactory.getInstance();
	
	private GameServer server;
	private Protocol protocol;
	private List<Player> players;
	
	@Before
	public void setup() {
		server = new GameServer();
		server.test = true;
		players = new ArrayList<Player>();
		for (int i = 0; i < 4; ++i) {
			protocol = mock(Protocol.class);
			players.add(server.createPlayer(protocol));
		}
	}
	
	@Test
	public void testSendMessageOnGameStarted() throws Exception {
		JSONObject message = Message.startGame(players);
		PowerMockito.verifyPrivate(server).invoke("sendToAll", message);
	}

}
