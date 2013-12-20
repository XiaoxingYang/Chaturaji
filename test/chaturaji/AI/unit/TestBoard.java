package chaturaji.AI.unit;

import junit.framework.TestCase;
import chaturaji.game.Color;
import chaturaji.game.Type;

import com.Chess.board.*;
import com.Chess.bitBoard.*;
import com.Chess.ply.*;
import com.Chess.position.*;
import com.Chess.piece.*;
import junit.framework.*;

public class TestBoard extends TestCase {
	public void test(){
		Board board= new BitBoardImpl();
//		byte[] boardcode = ((BitBoardImpl) board).getBytes();
//		for(int i =0; i < boardcode.length; i ++){
//			for (int j =0;j<8;j++){
//				System.out.print((boardcode[i]>>j)&1);
//			}
//			System.out.println();
//		}

		Position s=new PositionImpl(1);
		Piece a = board.getPiece(s);

		assertEquals(a.getColor(),Color.YELLOW);
		assertEquals(a.getType(),Type.PAWN);

		Position p=new PositionImpl(3);
		Piece b = board.getPiece(p);
//		assertEquals(b,null);
		
		Ply ply = new PlyImpl(s,p);
		board.doPly(ply);
//		byte[] boardcode = ((BitBoardImpl) board).getBytes();
//		for(int i =0; i < boardcode.length; i ++){
//			for (int j =0;j<8;j++){
//				System.out.print((boardcode[i]>>j)&1);
//			}
//			System.out.println();
//		}
//		
		Piece c = board.getPiece(s);
//		assertEquals(c,null);
		
		Piece d = board.getPiece(p);
		assertEquals(d.getColor(),Color.YELLOW);
		assertEquals(d.getType(),Type.PAWN);
		
	}
	
//	public long testPieceTypeForColor(Board board, Color color){
//		long pieceForColor=((BitBoard)board).getAllPiecesForColor(color);
//		return pieceForColor;
//	}
//	public void testEmptySquare(Board board){
//		long emptySquare=((BitBoard) board).getEmptySquares();
//		for(int i=0;i<64;i++){
//			System.out.print((emptySquare>>i)&1);
//		}
//	}
//	public long testGetPositionOfPieces(Board board,Piece piece){
//		long positionOfPiece=((BitBoard) board).getPositionOfPieces(piece);
//		return positionOfPiece;
//	}


}
