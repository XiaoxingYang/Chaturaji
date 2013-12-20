package com.Chess.board;

import chaturaji.game.Color;

import com.Chess.piece.Piece;
import com.Chess.ply.Ply;
import com.Chess.position.Position;

public interface Board extends Cloneable {
    
    /**
     * Get the piece of a given position.
     *
     * @param position The position of the piece.
     *
     * @return The piece on that position, or null if the 
     * square is empty.
     */
    Piece getPiece( Position position);

    /**
     * Remove all the pieces from the board.
     */
    void emptyBoard();

    /**
     * Set the pieces to their initial positions.
     */
    void initialPosition();

    /**
     * Set a piece on a given position.
     *
     * @param piece The piece to set.
     * @param position The position to set the piece on.
     */
    void setPiece( Piece piece, Position position);

    /**
     * Move a piece from one square to another.
     *
     * @param ply The move to perform.
     */
    Piece doPly( Ply ply);

    /**
     * Return a new board, that results from a given ply.
     *
     * @param ply The ply to perform.
     *
     * @return A new board with the game position after the ply.
     */
    Board getBoardAfterPly( Ply ply);
    
    /**
     * Get all white or black pieces.
     *
     * @param white true, if the white pieces are requested, 
     *              false for the black pieces.
     */
    long getAllPiecesForColor( Color color);
    
    
    
}