package com.Chess.engine;

import chaturaji.game.Color;

import com.Chess.bitBoard.BitBoard;
import com.Chess.bitBoard.BoardStack;
import com.Chess.board.Board;
import com.Chess.hashtable.PlyHashtable;
import com.Chess.ply.Ply;

public interface ChessEngine {

	public abstract int get_maxSearchTime();

	public abstract void set_maxSearchTime(int maxSearchTime);

	public abstract BoardStack get_boardStack();

	public abstract void set_boardStack(BoardStack boardStack);

	public abstract Board get_board();

	public abstract void set_board(BitBoard board);

	public abstract Color get_AIColor();

	public abstract void set_AIColor(Color aIColor);

//	public abstract Color get_color();

//	public abstract void set_color(Color color);

	public abstract PlyGenerator get_plyGenerator();

	public abstract void set_plyGenerator(PlyGenerator plyGenerator);

	public abstract PlyHashtable get_hashtable();

	public abstract void set_hashtable(PlyHashtable hashtable);

	/**
	 * Get the color of this engine.
	 *
	 * @param A color true, if the engine operates with the A pieces.
	 */
	public abstract boolean isAIColor(Color color);

	/**
	 * Start a new Thread to search for a ply
	 */
	public abstract void start();

	/**
	 * Compute the best ply for the current position.
	 *
	 * @return The best known ply for the current position.
	 */
	public abstract Ply computeBestPly(Color color);

	/**
	 * The main method of the search thread.
	 */
	public abstract void run();

}