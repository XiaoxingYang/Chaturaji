package chaturaji.AI.unit;

import junit.framework.TestCase;
import chaturaji.game.Color;

import com.Chess.hashtable.*;
import com.Chess.bitBoard.*;
import com.Chess.board.*;
import com.Chess.ply.*;
import com.Chess.position.*;

public class TestHashtable extends TestCase {
	public void testHashtable(){
		PlyHashtable ph=new PlyHashtableImpl(10000);
		Board board = new BitBoardImpl();
		Position source1 = new PositionImpl(20);
		Position destination1 = new PositionImpl(10);
		Position source2 = new PositionImpl(20);
		Position destination2 = new PositionImpl(10);
		Ply ply1 = new PlyImpl(source1, destination1);
		Ply ply2 = new PlyImpl(source2, destination2);
		PlyHashtableEntry phe1 = new PlyHashtableEntryImpl(board,ply1,Color.RED);
		PlyHashtableEntry phe2 = new PlyHashtableEntryImpl(board,ply2,Color.RED);
		ph.pushEntry(phe1);
		ph.pushEntry(phe2);
		assertEquals(ph.getPly(board, (byte)0),ph.getPly(board, (byte)0));
//		System.out.println(ph.getPly(board, (byte)0));
//		System.out.println(ph.getPly(board, (byte)0));	
		
	}
}
