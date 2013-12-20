package com.Chess.bitBoard;

import com.Chess.bitBoard.BoardStackImpl.BoardStatus;
import com.Chess.board.Board;
import com.Chess.ply.Ply;

public interface BoardStack {
	// Methods

    /**
     * Reset the game.
     */
    public void reset();

    /**
     * Add a new ply to the game.
     *
     * @ply The new ply.
     */
    public void doPly( Ply ply);

    /**
     * Take the last ply back.
     */
    public void undoLastPly();


    /**
     * Get the last ply.
     *
     * @return The last ply.
     */
    public Ply getLastPly();
    
    /**
     * Get the board in the stack peek, the board before new ply operate
     * @return
     */
    public Board getLastBoard();
    
    /**
     * Get last BoardStatus...(Board and ply)
     * @return
     */
    public BoardStatus getLastBoardStatus();
}
