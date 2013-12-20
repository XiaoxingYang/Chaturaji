package com.Chess.engine;

import java.util.List;

import org.json.JSONObject;

import chaturaji.game.Color;

import com.Chess.board.Board;
import com.Chess.piece.Piece;
import com.Chess.ply.Ply;
import com.Chess.position.Position;

public interface Engine {

	public void startGame(Color AIColor);

	public void updateBoard(Position from, Position to);

	public void setAIColor(Color aiColor);

	public String generatePly();
	
	public boolean myTurn();
	
	public void playerOut(Color turn);
	
	public void boatTriumph(List<String> boats);

}