package com.Chess.engine;


import chaturaji.game.Color;
import chaturaji.game.Type;

import com.Chess.*;
import com.Chess.bitBoard.*;
import com.Chess.board.Board;
import com.Chess.piece.*;
import com.Chess.ply.Ply;
import com.Chess.position.*;
import com.Chess.engine.BitBoardAnalyzer;


/**
 * The class implements the functionality to analyze
 * a game position, stored as a bitboard.
 */
public class BitBoardAnalyzerImpl implements BitBoardAnalyzer {
	
    // The position value of pawns on all the squares.
	// For the red color, if other color, turn this matrix
    static short [][] _pawnPositionalValue = {{  
    	  7,  6,  5,  0,  0,  0,  0,  0,
    	  8,  7,  6,  5,  0,  0,  0,  0,
    	  9,  8,  7,  6,  4,  0,  0,  0,
    	 10,  9,  8,  7,  6,  3,  0,  0,
    	 10,  9,  8,  7,  6,  4,  1,  0,
    	  9,  8,  7,  6,  5,  3,  1,  0,
    	  8,  7,  6,  5,  4,  3,  1,  0,
    	  7,  6,  5,  4,  3,  2,  1,  0 },
    	  {
		  7,  8,  9,  10,  10,  9,  8,  7,
	  	  6,  7,  8,  9,  9,  8,  7,  6,
	  	  5,  6,  7,  8,  8,  7,  6,  5,
	  	  4,  5,  6,  7,  7,  6,  5,  0,
	  	  3,  4,  5,  6,  6,  4,  0,  0,
	  	  2,  3,  3,  4,  3,  0,  0,  0,
	  	  1,  1,  1,  1,  0,  0,  0,  0,
	  	  0,  0,  0,  0,  0,  0,  0,  0 },
    	  {
  		  0,  1,  2,  3,  4,  5,  6,  7,
	  	  0,  1,  3,  4,  5,  6,  7,  8,
	  	  0,  1,  3,  5,  6,  7,  8,  9,
	  	  0,  1,  4,  6,  7,  8,  9,  10,
	  	  0,  0,  3,  6,  7,  8,  9,  10,
	  	  0,  0,  0,  4,  6,  7,  8,  9,
	  	  0,  0,  0,  0,  5,  6,  7,  8,
	  	  0,  0,  0,  0,  0,  5,  6,  7  
    	  },
    	  {
		  0,  0,  0,  0,  0,  0,  0,  0,
	  	  0,  0,  0,  0,  1,  1,  1,  1,
	  	  0,  0,  0,  3,  4,  3,  3,  2,
	  	  0,  0,  4,  6,  6,  5,  4,  3,
	  	  0,  5,  6,  7,  7,  6,  5,  4,
	  	  5,  6,  7,  8,  8,  7,  6,  5,
	  	  6,  7,  8,  9,  9,  8,  7,  6,
	  	  7,  8,  9,  10,  10,  9,  8,  7}};

    // The positional value of knights on the various squares.
    static short [] _knightPositionalValue = {  
    	1,  2,  3,  3,  3,  3,  2,  1,
    	2,  3,  5,  5,  5,  5,  3,  2,
    	3,  5,  6,  6,  6,  6,  5,  3,
		3,  5,  6,  6,  6,  6,  5,  3,
		3,  5,  6,  6,  6,  6,  5,  3,
		3,  5,  6,  6,  6,  6,  5,  3,
		2,  3,  5,  5,  5,  5,  3,  2,
    	1,  2,  3,  3,  3,  3,  2,  1 };

    // The positional values of bishops on the various squares.
    static short [] _boatPositionalValue = {
    	1,  0,  0,  0,  0,  0,  0,  1,
    	0,  4,  0,  0,  0,  0,  4,  0,
		0,  0,  5,  0,  0,  5,  0,  0,
		0,  0,  0,  6,  6,  0,  0,  0,
		0,  0,  0,  6,  6,  0,  0,  0,
		0,  0,  5,  0,  0,  5,  0,  0,
		0,  4,  0,  0,  0,  0,  4,  0,
    	1,  0,  0,  0,  0,  0,  0,  1 };


    // Instance variables
    
    /**
     * The color of ai
     */
    private Color _AIColor;
    

    // Constructors

    
    /* (non-Javadoc)
	 * @see com.Chess.engine.BitBoardAnalyzer#get_AIColor()
	 */
    public Color get_AIColor() {
		return _AIColor;
	}


	/* (non-Javadoc)
	 * @see com.Chess.engine.BitBoardAnalyzer#set_AIColor(chaturaji.game.Color)
	 */
	public void set_AIColor(Color aiColor) {
		_AIColor = aiColor;
	}

    /* (non-Javadoc)
	 * @see com.Chess.engine.BitBoardAnalyzer#isAIColor(chaturaji.game.Color)
	 */
    public final boolean isAINextColor(Color color){
    	return get_AIColor()==Color.getColorFromInt((color.getTurn()-1)%4);
    }

	/**
     * Create a new bitboard analyzer.
     *
     * @param plyGenerator A PlyGenerator instance to simulate moves.
     */
    public BitBoardAnalyzerImpl(Color AIColor) {
    	_AIColor = AIColor;
    }
  
    /* (non-Javadoc)
	 * @see com.Chess.engine.BitBoardAnalyzer#analyze(com.Chess.board.Board, chaturaji.game.Color)
	 */
    public final short analyze(Board board, Board lastBoard,Ply lastPly,Color color, byte playerStatus) {
    	short sum = 0;
    	//short piecesValue = analyzeOwnPieces(board, color);
    	//short positionValue = analyzePositionValue(board, color);
    	short potentialPositionValue = analyzePotentialPosition(board, color,playerStatus);
    	short scoreValue = analyzeScoreObtain(board,lastBoard,lastPly,color);
    	sum = (short) ( potentialPositionValue + 3*scoreValue);
    	return sum;
	}

    
    
    public final short analyzeOwnPieces(Board board, Color color){
    	short value = 0;
		long pieceOfColor=board.getAllPiecesForColor(get_AIColor());
		for(int i = 0; i < 64; i ++){
			if((pieceOfColor & 1) == 1){
				Piece piece = board.getPiece(new PositionImpl(i));
				switch(piece.getType()){
				case PAWN:
					value += 2;
					break;
				case BOAT:
				case KNIGHT:
					value += 3;
					break;
				case ELEPHANT:
					value += 5;
					break;
				case KING:
					value += 200;
				}
			}
			pieceOfColor >>= 1;
		}
    	if(!isAINextColor(color)){
    		value *= -1;
    	}
    	return value;
    }

    public final short analyzePositionValue(Board board, Color color){
    	short value = 0;
    	//compute knight and boat value
    	long pieceOfColor=board.getAllPiecesForColor(get_AIColor());
		for(int i = 0; i < 64; i ++){
			if((pieceOfColor & 1) == 1){
				Piece piece = board.getPiece(new PositionImpl(i));
				switch(piece.getType()){
				case PAWN:
					value += _pawnPositionalValue[piece.getColor().getTurn()][i];
					break;
				case BOAT:
					value += _boatPositionalValue[i];
					break;
				case KNIGHT:
					value += _knightPositionalValue[i];
					break;
				}
			}
			pieceOfColor >>= 1;
		}
		if(!isAINextColor(color)){
    		value *= -1;
    	}
    	return value;
    }

    public final short analyzePotentialPosition(Board board, Color color,byte playerStatus){
    	short value=0;
    	PlyGenerator pg = new PlyGenerator();
    	Ply[] plies = pg.getPliesForColor((BitBoard) board,get_AIColor());
    	for(int i = 0; i < plies.length; i ++){
    		Position d = plies[i].getDestination();
    		if(board.getPiece(d)!=null){
    			Piece piece = board.getPiece(d);
    			switch(piece.getType()){
    			case PAWN:
    				value += 2;
    				break;
    			case BOAT:
    				value += 5;
    				break;
    			case KNIGHT:
    				value += 5;
    				break;
    			case ELEPHANT:
    				value += 8;
    				break;
    			case KING:
    				value += 30;
    				break;
    			}
    		}
    	}
    	//compute the threaten by other players
    	for(int j =0; j<3;j++){
    		int playerTurn = (color.getTurn()+j)%4;
    		if(((playerStatus>>playerTurn)&1)==1){
    			PlyGenerator pg2 = new PlyGenerator();
            	Ply[] plies2 = pg2.getPliesForColor((BitBoard) board,Color.getColorFromInt(playerTurn));
            	for(int i = 0; i < plies2.length; i ++){
            		Position d = plies2[i].getDestination();
            		if(board.getPiece(d)!=null){
            			Piece piece = board.getPiece(d);
            			if(piece.getColor()==get_AIColor()){
            				switch(piece.getType()){
                			case PAWN:
                				value -= 2;
                				break;
                			case BOAT:
                				value -= 5;
                				break;
                			case KNIGHT:
                				value -= 5;
                				break;
                			case ELEPHANT:
                				value -= 8;
                				break;
                			case KING:
                				value -= 150;
                				break;
                			}
            			}
            		}
            	}
    		}
    		
    	}
    	if(!isAINextColor(color)){
    		value *= -1;
    	}
    	return value;
    }

    public final short analyzeScoreObtain(Board board, Board lastboard, Ply lastPly, Color color){
    	short value = 0;
    	Position moveto = lastPly.getDestination();
    	if(lastboard.getPiece(moveto)!=null){
    		Piece piece = lastboard.getPiece(moveto);
    		switch(piece.getType()){
			case PAWN:
				value += 3;
				break;
			case BOAT:
				value += 6;
				break;
			case KNIGHT:
				value += 6;
				break;
			case ELEPHANT:
				value += 9;
				break;
			case KING:
				value += 30;
				break;
			}
    		
    	}
    	if(!isAINextColor(color)){
    		value *= -1;
    	}
    	return value;
    }
    
}
