package com.Chess.bitBoard;

import chaturaji.game.Color;

import com.Chess.board.Board;
import com.Chess.piece.Piece;
import com.Chess.ply.Ply;
import com.Chess.position.Position;

public interface BitBoard extends Board{

	/**
	 * Remove all the pieces from the board.
	 */
	public abstract void emptyBoard();

	/**
	 * Get the piece of a given position, or null of the square is empty.
	 *
	 * @param position The position of the piece.
	 * 
	 * @return The piece of the square or null, of the square is empty.
	 */
	public abstract Piece getPiece(Position position);

	/**
	 * Set a piece on a given square.
	 *
	 * @param piece The piece to set, or null to empty the square.
	 * @param position The position of the square.
	 */
	public abstract void setPiece(Piece piece, Position position);

	/**
	 * Move a piece from one square to another.
	 *
	 * @param ply The ply to perform.
	 */
	public abstract Piece doPly(Ply ply);

	/**
	 * Return a new board, that results from a given ply.
	 *
	 * @param ply The ply to perform.
	 *
	 * @return A new board with the game position after the ply.
	 */
	public abstract Board getBoardAfterPly(Ply ply);

	/**
	 * Get the positions of some pieces as a long (64 bit wide) bitmask.
	 *
	 * @param pieceTypeColor The color and type of the pieces.
	 */
	public abstract long getPositionOfPieces(Piece piece);

	/**
	 * Get a bitmask with all the free squares.
	 *
	 * @return A bitmask with all the empty squares marked by a 1 bit.
	 */
	public abstract long getEmptySquares();

	/**
	 * Get all pieces in required color.
	 *
	 * @param white true, if the white pieces are requested, 
	 *              false for the black pieces.
	 */
	public abstract long getAllPiecesForColor(Color Color);

	/**
	 * Get the board as a byte stream.
	 *
	 * @return The board as a array of bytes.
	 */
	public abstract byte[] getBytes();

}