/*
  PlyHashtableEntryImpl - Implements a hashtable entry for plies.

*/ 

package com.Chess.hashtable;

import chaturaji.game.Color;
import chaturaji.game.Type;

import com.Chess.bitBoard.BitBoard;
import com.Chess.board.*;
import com.Chess.piece.*;
import com.Chess.ply.*;
import com.Chess.position.*;


import java.io.ObjectInputStream.GetField;
import java.util.zip.CRC32;


/**
 * The class implements the functionality to store
 * chess plies in a hashtable.
 */
public class PlyHashtableEntryImpl implements PlyHashtableEntry {

    // Instance variables

    /**
     * The board before this ply.
     */
    Board _board;

    /**
     * The ply to store.
     */
    Ply _ply;
    
    /**
     * The engine color 
     */
    Color _color;
    
    // Constructors

    /**
     * Create a new hashtable entry.
     *
     * @param board The board before the ply.
     * @param ply The ply to store.
     */
    public PlyHashtableEntryImpl( Board board, Ply ply, Color color) {
	setBoard( board);
	setPly( ply);
	setColor(color);
    }


    // Methods

    /**
     * Get the board of this entry.
     *
     * @return The board of this entry.
     */
    public final Board getBoard() {
	return _board;
    }

    /**
     * Set a new board for this entry.
     *
     * @param board The new board.
     */
    public final void setBoard( Board board) {
	_board = board;
    }

    /**
     * Get the ply.
     *
     * @return The ply.
     */
    public final Ply getPly() {
	return _ply;
    }

    /**
     * Set the ply.
     *
     * @param ply The ply to set.
     */
    public final void setPly( Ply ply) {
	_ply = ply;
    }
    
    /**
     * Get the engine color.
     */
    public final Color getColor(){
    	return _color;
    }
    
    /**
     * Set the engine color;
     */
    public final void setColor(Color color){
    	_color=color;
    }

    /**
     * Check, if it's a move with AI pieces.
     *
     * @return true, if it's a move with AI pieces.
     */
//    public final boolean isAIMove() {
//	return getBoard().getPiece( getPly().getSource()).getColor() == getColor();
//    }
    
    /**
     * Get the hashcode for this ply.
     *
     * @return A hashcode for this ply.
     */
    public final long hashKey() {
	return hashKey( getBoard(),(byte) getColor().getTurn());
    }

    /**
     * Get a hashcode for a given board and piece color.
     *
     * @param board The current board.
     * @param white true, if it's a ply with the white pieces.
     *
     * @return A hashcode for the given board and color.
     */
    public static long hashKey( Board board, byte color) {
	
	// I use a CRC32 code as the hash code.
	CRC32 crc32 = new CRC32();
	
	if( board instanceof BitBoard) {

	    // Serialize the board as bytes, if it's a bitboard.
	    crc32.update( ( (BitBoard)board).getBytes());
	} else {

	    // Reuse the same position object for each square.
	    Position curPosition = new PositionImpl( 0);

	    for( int square = 0; square < 64; square++) {
		curPosition.setSquareIndex( square);
		Piece p = board.getPiece( curPosition);
		
		crc32.update( p != null ? Type.getIntFromType(p.getType()) : 0);
	    }
	}
	
	// Change the code, depending on the color of the moved
	// piece, so we store the best move for four colors
	// for the _same_ board in the hashtable.
	switch(color){
	case 0:
		crc32.update(127);
		break;
	case 1:
		crc32.update(95);
		break;
	case 2:
		crc32.update(63);
		break;
	case 3:
		crc32.update(31);
	}
	    
	// Return the lower 32 bits of the current crc32 value.
	return crc32.getValue();
    }


	
}