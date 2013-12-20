package chaturaji.AI.unit;

import junit.framework.TestCase;
import chaturaji.game.Color;
import chaturaji.game.Type;

import com.Chess.piece.*;
public class TestPiece extends TestCase {
	public void testPieceColor(){
		Piece piece=new PieceImpl(Color.RED,Type.BOAT);
		assertEquals(piece.getColor(),Color.RED);
		assertEquals(piece.getType(),Type.BOAT);
		Piece piece2=new PieceImpl(Color.RED,Type.BOAT);
//		assertEquals(piece,piece2);
	}
//	public void testPieceType(){
//		Piece piece=new PieceImpl((byte)7);
//		assertEquals(piece.getType(),(byte)1);
//	}
}
