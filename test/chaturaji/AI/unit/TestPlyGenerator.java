package chaturaji.AI.unit;

import junit.framework.TestCase;
import chaturaji.game.Color;

import com.Chess.piece.*;
import com.Chess.ply.Ply;
import com.Chess.position.*;
import com.Chess.board.*;
import com.Chess.bitBoard.*;
import com.Chess.engine.*;

public class TestPlyGenerator extends TestCase {
	public void test(){
		Board board = (Board) new BitBoardImpl();
		PlyGenerator pg=new PlyGenerator();
		Ply[] plies = pg.getPliesForColor((BitBoard) board,Color.YELLOW);
		
//		System.out.println(plies[0].toString());
		assertTrue(plies.length==9);
	}
	
}
