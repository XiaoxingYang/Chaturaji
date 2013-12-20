package com.Chess.engine;

import java.util.ArrayList;
import java.util.List;

import chaturaji.game.Color;

import com.Chess.bitBoard.BitBoard;
import com.Chess.bitBoard.BitBoardImpl;
import com.Chess.board.Board;
import com.Chess.ply.*;
import com.Chess.position.*;


public class ChaturajiEngine implements Engine {
	
	private Board board;
	private Color AIColor;
	private Color colorTurn;
	private List<Color> out; 
	private byte _playerStatus;

	public ChaturajiEngine() {
		out = new ArrayList<Color>();
	}
	
	/* (non-Javadoc)
	 * @see com.Chess.engine.Engine#startGame()
	 */
	public void startGame(Color AIColor){
		board = new BitBoardImpl();
		colorTurn = Color.RED;
		this.AIColor = AIColor;
		_playerStatus = (byte) 15;
	}
	
	/* (non-Javadoc)
	 * @see com.Chess.engine.Engine#updateBoard(com.Chess.ply.Ply)
	 */
	public void updateBoard(Position from, Position to){
		String a;
		String b;
/*			a =(String)jsonmove.get("from_sq");
			b = (String)jsonmove.get("to_sq");
		
		char cs = a.charAt(0);
		char cd = b.charAt(0);
		int numbers = a.charAt(1);
		int numberd = b.charAt(1);
		int source = (8-numbers)*8+cs-'A';
		int destination = (8-numberd)*8+cd-'A';
		Position ps = new PositionImpl(source);
		Position pd = new PositionImpl(destination);*/
		Ply ply = new PlyImpl(from, to);
		board.doPly(ply);
		do {
			colorTurn = Color.getColorFromInt((colorTurn.getTurn()+1)%4);
		} while (out.contains(colorTurn));
	}
	
	/* (non-Javadoc)
	 * @see com.Chess.engine.Engine#setAIColor(byte)
	 */
	public void setAIColor(Color aiColor){
		AIColor = aiColor;
	}
	
	public boolean myTurn(){
		return AIColor == colorTurn;
	}
	
	public String generatePly(){
		ChessEngine engine = new ChessEngineImpl(board, AIColor);
		Ply ply=engine.computeBestPly(AIColor);
		return ply.toString();
	}

	@Override
	public void playerOut(Color turn) {
		out.add(turn);
		_playerStatus = (byte)(_playerStatus - Math.pow(2,turn.getTurn()));

	}

	@Override
	public void boatTriumph(List<String> boats) {
		for (String boat : boats) {
			board.setPiece(null, new PositionImpl(boat));
		}
	}
	
	
}
