
package com.Chess.engine;

import chaturaji.game.Type;
import chaturaji.game.Color;
import com.Chess.piece.*;
import com.Chess.position.*;
import com.Chess.bitBoard.*;
import com.Chess.board.Board;
import com.Chess.ply.*;

public class PlyGenerator {
	
    /**
     * A counter for the currently computed plies.
     */
    private int _plyCounter = 0;
    
	public int get_plyCounter() {
		return _plyCounter;
	}

	public void set_plyCounter(int plyCounter) {
		_plyCounter = plyCounter;
	}

	/**
     * An array to save the possible plies
     */
    private Ply[] _currentPlies = new Ply[46];


	public Ply[] get_currentPlies() {
		return _currentPlies;
	}
   
    
    /**
     * Get the possible move for pawn
     */
    public Ply[] getPliesForPawn(BitBoard board, Color color){
    	long pieceOfColor=board.getAllPiecesForColor(color);
    	for(int k=0;k<64;k++){
    		if((pieceOfColor&1)==1){
    			Position source = new PositionImpl(k);
    			Piece piece=board.getPiece(source);
    			if(piece.getType()== Type.PAWN){
    				int[][] _pawnPlyOffsetToEmpty;
        			int[][] _pawnPlyOffsetToEat;
        			if(piece.getColor() == Color.RED){
        				_pawnPlyOffsetToEat = new int[][]{ { -1, -1}, { 1, -1}};
        				_pawnPlyOffsetToEmpty = new int[][]{{ 0, -1}};
        			}
        			else if(piece.getColor() == Color.GREEN){
        				_pawnPlyOffsetToEat = new int[][]{ { -1, -1}, { -1, 1} };
        				_pawnPlyOffsetToEmpty = new int[][]{{ -1, 0}};
        			}
        			else if(piece.getColor() == Color.YELLOW){
        				_pawnPlyOffsetToEat = new int[][]{ { -1, 1}, { 1, 1} };
        				_pawnPlyOffsetToEmpty = new int[][]{{ 0, 1}};
        			}
        			else if(piece.getColor() == Color.BLACK){
        				_pawnPlyOffsetToEat = new int[][]{ { 1, -1}, { 1, 1}};
        				_pawnPlyOffsetToEmpty = new int[][]{{ 1, 0}};
        			}else{
        				_pawnPlyOffsetToEat = new int[][]{ { 0, 0}, { 0, 0}};
        				_pawnPlyOffsetToEmpty = new int[][]{{ 0, 0}};
        			}
        			long ownPiecePosition = board.getAllPiecesForColor(color);
        			long emptyPosition = board.getEmptySquares();
        			long notEmptyPosition = ~emptyPosition;
        			
        			int line = source.getLineIndex();
        			int row = source.getRowIndex();
        			for(int m = 0; m < 2; m++){
        				int newRow = row + _pawnPlyOffsetToEat[m][0];
        				int newLine = line + _pawnPlyOffsetToEat[m][1];
        				if(rowLineInRange(newRow, newLine)){
        					int destinationIndex = newRow * 8 + newLine ;
        					if((((notEmptyPosition >> destinationIndex) & 1)== 1 ) & (((ownPiecePosition>>destinationIndex)&1)==0 )){
        						Position destination = new PositionImpl(destinationIndex);
        						Ply ply=new PlyImpl(source, destination);
        						_currentPlies[_plyCounter]=ply;
        						_plyCounter++;
        					}
        				}
        			}
        			for(int m = 0; m < 1; m++){
        				int newRow = row + _pawnPlyOffsetToEmpty[m][0];
        				int newLine = line + _pawnPlyOffsetToEmpty[m][1];
        				if(rowLineInRange(newRow, newLine)){
        					int destinationIndex = newRow * 8 + newLine ;
        					if(((emptyPosition >> destinationIndex ) & 1)== 1){
        						Position destination = new PositionImpl(destinationIndex);
        						Ply ply=new PlyImpl(source, destination);
        						_currentPlies[_plyCounter]=ply;
        						_plyCounter++;
        					}
        				}
        			}
        			
    			}
    		}
        	pieceOfColor>>=1;
    	}
		return _currentPlies;
    }

    /**
     * Get the possible move for boat
     */
    public Ply[] getPliesForBoat(BitBoard board, Color color){
    	long pieceOfColor=board.getAllPiecesForColor(color);
    	for(int k=0;k<64;k++){
    		if((pieceOfColor&1)==1){
    			Position source = new PositionImpl(k);
    			Piece piece=board.getPiece(source);
    			if(piece.getType()==Type.BOAT){
    				int [] [] _boatPlyOffset = { { -2, -2}, { -2, 2}, { 2, -2}, { 2, 2} };
    	    	    long ownPiecePosition = board.getAllPiecesForColor(color);
    	    		int line = source.getLineIndex();
    	    		int row = source.getRowIndex();
    	    		for(int m = 0; m < 4; m++){
    	    			int newRow = row + _boatPlyOffset[m][0];
    	    			int newLine = line + _boatPlyOffset[m][1];
    	    			if(rowLineInRange(newRow, newLine)){
    	    				int destinationIndex = newRow * 8 + newLine ;
    	    				long positionMask = 1L << destinationIndex;
    	    				if((positionMask & ownPiecePosition)== 0){
    	    					Position destination = new PositionImpl(destinationIndex);
    	    					// put into hashtable
    	    					
    	    					Ply ply=new PlyImpl(source, destination);
    	    					_currentPlies[_plyCounter]=ply;
        						_plyCounter++;    	    				}
    	    			}
    	    		}
    			}
    		}
        	pieceOfColor>>=1;
    	}
    	return _currentPlies;
    }
    
    /**
     * Get for knight
     */
    public Ply[] getPliesForKnight(BitBoard board, Color color){
    	long pieceOfColor=board.getAllPiecesForColor(color);
    	for(int k=0;k<64;k++){
    		if((pieceOfColor&1)==1){
    			Position source = new PositionImpl(k);
    			Piece piece=board.getPiece(source);
    			if(piece.getType()==Type.KNIGHT){
    				int [] [] _knightPlyOffset = { { -2, -1}, { -2, 1}, { -1, -2}, { -1, 2}, { 1, -2}, { 1, 2}, { 2, -1}, { 2, 1}};    	    	    
    				long ownPiecePosition = board.getAllPiecesForColor(color);
    	    		int line = source.getLineIndex();
    	    		int row = source.getRowIndex();
    	    		for(int m = 0; m < 8; m++){
    	    			int newRow = row + _knightPlyOffset[m][0];
    	    			int newLine = line + _knightPlyOffset[m][1];
    	    			if(rowLineInRange(newRow, newLine)){
    	    				int destinationIndex = newRow * 8 + newLine ;
    	    				long positionMask = 1L << destinationIndex;
    	    				if((positionMask & ownPiecePosition)== 0){
    	    					Position destination = new PositionImpl(destinationIndex);
    	    					// put into hashtable
    	    					
    	    					Ply ply=new PlyImpl(source, destination);
    	    					_currentPlies[_plyCounter]=ply;
        						_plyCounter++;
    	    				}
    	    			}
    	    		}
    			}
    		}
        	pieceOfColor>>=1;
    	}
    	return _currentPlies;
    }

    /**
     * Get for elephant
     */
    public Ply[] getPliesForElephant(BitBoard board, Color color){
    	long pieceOfColor=board.getAllPiecesForColor(color);
    	for(int k=0;k<64;k++){
    		if((pieceOfColor&1)==1){
    			Position source = new PositionImpl(k);
    			Piece piece=board.getPiece(source);
    			if(piece.getType()==Type.ELEPHANT){
    				long ownPiecePosition = board.getAllPiecesForColor(piece.getColor());
    	        	long emptyPosition = board.getEmptySquares();
    	        	int line = source.getLineIndex();
    				int row = source.getRowIndex();
    				for(int newLine = 0; newLine < 8; newLine++){
    					int newIndex=row*8+newLine;
    					if(((ownPiecePosition>>newIndex)&1)==0){
    						boolean hasPieceBetweenLine=false;
    						for(int j=newLine+1;j<line;j++){
        						int newIndexBetween=row*8+j;
        						if(((emptyPosition>>newIndexBetween)&1)==0){
        							hasPieceBetweenLine=true;
        						}
        					}
    						for(int j=line+1;j<newLine;j++){
        						int newIndexBetween=row*8+j;
        						if(((emptyPosition>>newIndexBetween)&1)==0){
        							hasPieceBetweenLine=true;
        						}
        					}
    						if(!hasPieceBetweenLine){
    							Position destination=new PositionImpl(newIndex);
    							Ply ply=new PlyImpl(source,destination);
    							_currentPlies[_plyCounter]=ply;
        						_plyCounter++;
    						}
    						
    					}
    				}
    				for(int newRow = 0; newRow < 8; newRow++){
    					int newIndex=newRow*8+line;
    					if(((ownPiecePosition>>newIndex)&1)==0){
    						boolean hasPieceBetweenRow=false;
    						for(int j=newRow+1;j<row;j++){
        						int newIndexBetween=j*8+line;
        						if(((emptyPosition>>newIndexBetween)&1)==0){
        							hasPieceBetweenRow=true;
        						}
        					}
    						for(int j=row+1;j<newRow;j++){
        						int newIndexBetween=j*8+line;
        						if(((emptyPosition>>newIndexBetween)&1)==0){
        							hasPieceBetweenRow=true;
        						}
        					}
    						if(!hasPieceBetweenRow){
    							Position destination=new PositionImpl(newIndex);
    							Ply ply=new PlyImpl(source,destination);
    							_currentPlies[_plyCounter]=ply;
        						_plyCounter++;
    						}
    						
    					}
    				}
    			}
    			
    		}
    		pieceOfColor>>=1;
    	}
    	return _currentPlies;
    }
    
    /**
     * Get for king
     */
    public Ply[] getPliesForKing(BitBoard board, Color color){
    	long pieceOfColor=board.getAllPiecesForColor(color);
    	for(int k=0;k<64;k++){
    		if((pieceOfColor&1)==1){
    			Position source = new PositionImpl(k);
    			Piece piece=board.getPiece(source);
    			if(piece.getType()==Type.KING){
    				int [] [] _kingPlyOffset = { { -1, -1}, { -1, 1}, { 1, -1}, { 1, 1}, { -1, 0}, { 0, 1}, { 1, 0}, { 0, -1} };
    	    	    long ownPiecePosition = board.getAllPiecesForColor(color);
    	    		int line = source.getLineIndex();
    	    		int row = source.getRowIndex();
    	    		for(int m = 0; m < 8; m++){
    	    			int newRow = row + _kingPlyOffset[m][0];
    	    			int newLine = line + _kingPlyOffset[m][1];
    	    			if(rowLineInRange(newRow, newLine)){
    	    				int destinationIndex = newRow * 8 + newLine ;
    	    				long positionMask = 1L << destinationIndex;
    	    				if((positionMask & ownPiecePosition)== 0){
    	    					Position destination = new PositionImpl(destinationIndex);
    	    					// put into hashtable
    	    					
    	    					Ply ply=new PlyImpl(source, destination);
    	    					_currentPlies[_plyCounter]=ply;
        						_plyCounter++;    	    				
        						}
    	    			}
    	    		}
    			}
    		}
        	pieceOfColor>>=1;
    	}
    	return _currentPlies;
    }
    
    /**
     * find the possible move for each piece
     * 
     * @param piece
     * @return 
     */
    public Ply[] getPliesForColor(BitBoard board, Color color){
    	getPliesForPawn(board, color);
    	getPliesForBoat(board, color);
    	getPliesForKnight(board, color);
    	getPliesForElephant(board, color);
    	getPliesForKing(board, color);
    	Ply[] finalPlies = new Ply[_plyCounter];
    	for(int i = 0 ; i < _plyCounter ; i ++){
    		finalPlies[i] = _currentPlies[i];
    	}
    	return finalPlies;
    }
    
    /**
     * check if the position is in range
     * 
     */
    public boolean rowLineInRange(int row, int line){
    	if(row >= 0 & row <= 7 & line >= 0 & line <= 7){
    		return true;
    	}else{
    		return false;
    	}
    }


	

    
}
