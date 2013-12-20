package com.Chess.bitBoard;

import java.util.*;

import com.Chess.ply.Ply;
import com.Chess.position.Position;
import com.Chess.bitBoard.*;
import com.Chess.board.*;

public class BoardStackImpl implements BoardStack{
	
	class BoardStatus{
		/**
		 * The play that lead to this status
		 */
		Ply _ply;
		
		/**
		 * The current board
		 */
		Board _board;
		
		/**
		 * Get ply
		 */
		public Ply getPly(){
			return _ply;
		}
		
		/**
		 * Set ply
		 */
		public void setPly(Ply ply){
			_ply=ply;
		}
		
		/**
		 * Get board
		 */
		public Board getBoard(){
			return _board;
		}
		
		/**
		 * Set board
		 */
		public void setBoard(Board board){
			_board = board;
		}
		
		/**
		 * Constructor
		 */
		BoardStatus(Board board){
			_board = board;
			_ply = null;
		}
		
		/**
		 * Constructor
		 */
		BoardStatus(Ply ply, Board board){
			_board = board;
			_ply = ply;
		}
	}
	
	
	/**
	 * Board Status Stack
	 */
	private Stack _boardStack;
	
	
	public BoardStackImpl(Board board){
		_boardStack = new Stack();
		BoardStatus bs = new BoardStatus(board);
		_boardStack.push(bs);
	}
	
	// Methods
	
    /**
     * Reset the game.
     */
    public void reset(){
    		// Simply remove all the status instances from the game.
    		_boardStack.clear();
    }

    /**
     * Add a new ply to the game.
     *
     * @ply The new ply.
     */
    public void doPly( Ply ply){
    	
		_boardStack.push(getLastBoard()==null? null:new BoardStatus(ply,getLastBoard().getBoardAfterPly(ply)));
    	
    	
    }

    /**
     * Take the last ply back.
     */
    public void undoLastPly(){
    	_boardStack.pop();
    }

    /**
     * Get the last ply.
     *
     * @return The last ply.
     */
    public Ply getLastPly(){
    	BoardStatus boardStatus = getLastBoardStatus();
    	return getLastBoardStatus() == null ? null : getLastBoardStatus().getPly();
    }
    
    /**
     * Get the current board
     */
    public Board getLastBoard(){
    	BoardStatus boardStatus = getLastBoardStatus();
    	return getLastBoardStatus() == null ? null : getLastBoardStatus().getBoard();
    }
    
    /**
     * Get last BoardStatus
     * @return
     */
    public BoardStatus getLastBoardStatus() {
    	return _boardStack.empty() ? null : (BoardStatus)_boardStack.peek();
    	
    }
}
