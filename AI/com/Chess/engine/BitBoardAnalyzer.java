package com.Chess.engine;

import chaturaji.game.Color;

import com.Chess.board.Board;
import com.Chess.ply.Ply;

public interface BitBoardAnalyzer {

	public abstract Color get_AIColor();

	public abstract void set_AIColor(Color aiColor);

	/**
	 * Check if is the aicolor
	 */
	public abstract boolean isAINextColor(Color color);

	/**
	 * Analyze the current board.
	 */
	public abstract short analyze(Board board,Board lastBoard,Ply lastPly, Color color,byte playerStatus);

	/**
	 * Compute the number of pieces * value of piece
	 */
	public abstract short analyzeOwnPieces(Board board, Color color);

	/**
	 * Compute position value
	 */
	public abstract short analyzePositionValue(Board board, Color color);

	public abstract short analyzePotentialPosition(Board board, Color color,byte playerStatus);
	
	public abstract short analyzeScoreObtain(Board board, Board lastboard, Ply lastPly, Color color);

}