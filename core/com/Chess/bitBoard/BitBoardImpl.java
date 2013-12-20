package com.Chess.bitBoard;

import chaturaji.game.Color;
import chaturaji.game.Type;

import com.Chess.board.*;
import com.Chess.piece.*;
import com.Chess.position.*;
import com.Chess.ply.*;

public class BitBoardImpl implements BitBoard {


    // Instance variables

    /**
     * Store the board in 5 64 bit layers.
     * Layer 0-1 holds the color, layer 2-4 hold the piece code or 0, 
     * if the square is empty.
     */
    long [] _boardLayer = new long[5];


    // Constructors

    /**
     * Create a new instance of a chess board with pieces on their
     * initial positions.
     */
    public BitBoardImpl() {
	initialPosition();
    }


    // Methods

    /**
     * Create a clone of this board.
     *
     * @return A clone of this board.
     */
    protected final Object clone() {
		BitBoardImpl clone = new BitBoardImpl();
		for( int i = 0; i < 5; i++) {
		    clone._boardLayer[i] = _boardLayer[i];
		}
		return clone;
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#emptyBoard()
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#emptyBoard()
	 */
    public final void emptyBoard() {
	for( int i = 0; i < 5; i++) {
	    _boardLayer[i] = 0L;
	}
    }

    /**
     * Set all the pieces to their initial positions.
     */
    public final void initialPosition() {
		for( int i = 0; i < 4; i++) {
		    setPiece( new PieceImpl( Color.RED, Type.PAWN), new PositionImpl( 38 + 8*i));
		}
		for( int i = 0; i < 4; i++) {
		    setPiece( new PieceImpl( Color.BLACK, Type.PAWN), new PositionImpl( 12 + i));
		}
		for( int i = 0; i < 4; i++) {
		    setPiece( new PieceImpl( Color.YELLOW, Type.PAWN), new PositionImpl( 1 + 8*i));
		}
		for( int i = 0; i < 4; i++) {
		    setPiece( new PieceImpl( Color.GREEN, Type.PAWN), new PositionImpl( 48 + i));
		}
		
		setPiece( new PieceImpl( Color.RED, Type.BOAT), new PositionImpl( 63 ));
		setPiece( new PieceImpl( Color.RED, Type.KNIGHT), new PositionImpl( 55 ));
		setPiece( new PieceImpl( Color.RED, Type.ELEPHANT), new PositionImpl( 47 ));
		setPiece( new PieceImpl( Color.RED, Type.KING), new PositionImpl( 39 ));
		
		setPiece( new PieceImpl( Color.BLACK, Type.BOAT), new PositionImpl( 7 ));
		setPiece( new PieceImpl( Color.BLACK, Type.KNIGHT), new PositionImpl( 6 ));
		setPiece( new PieceImpl( Color.BLACK, Type.ELEPHANT), new PositionImpl( 5 ));
		setPiece( new PieceImpl( Color.BLACK, Type.KING), new PositionImpl( 4 ));

		setPiece( new PieceImpl( Color.YELLOW, Type.BOAT), new PositionImpl( 0 ));
		setPiece( new PieceImpl( Color.YELLOW, Type.KNIGHT), new PositionImpl( 8 ));
		setPiece( new PieceImpl( Color.YELLOW, Type.ELEPHANT), new PositionImpl( 16 ));
		setPiece( new PieceImpl( Color.YELLOW, Type.KING), new PositionImpl( 24 ));

		setPiece( new PieceImpl( Color.GREEN, Type.BOAT), new PositionImpl( 56 ));
		setPiece( new PieceImpl( Color.GREEN, Type.KNIGHT), new PositionImpl( 57 ));
		setPiece( new PieceImpl( Color.GREEN, Type.ELEPHANT), new PositionImpl( 58 ));
		setPiece( new PieceImpl( Color.GREEN, Type.KING), new PositionImpl( 59 ));
		
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getPiece(com.Chess.position.Position)
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getPiece(com.Chess.position.Position)
	 */
    public final Piece getPiece( Position position) {
		int bitpos = position.getSquareIndex();
	
		int pieceType = (((int)(_boardLayer[2] >> bitpos) & 1)
				 | (((int)(_boardLayer[3] >> bitpos) & 1) << 1)
				 | (((int)(_boardLayer[4] >> bitpos) & 1) << 2));
		int pieceColor = (int) (((_boardLayer[0]>>bitpos)&1)|(((_boardLayer[1]>>bitpos)&1)<<1));
//		return  new PieceImpl(Color.getColorFromInt(pieceColor),Type.getTypeFromInt(pieceType));

		return (pieceType == 0) ? null : new PieceImpl(Color.getColorFromInt(pieceColor),Type.getTypeFromInt(pieceType));
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#setPiece(com.Chess.piece.Piece, com.Chess.position.Position)
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#setPiece(com.Chess.piece.Piece, com.Chess.position.Position)
	 */
    public final void setPiece( Piece piece, Position position) {
	byte pieceTypeColor = (byte) ((piece == null) ? 0 : (byte)(piece.getColor().getTurn())|((byte)(Type.getIntFromType(piece.getType())))<<2);
	long bitmask = 1L <<  position.getSquareIndex();
	long bitFilter = ~bitmask;
	
	for( int i = 0; i < 5; i++) {
	    if( ( pieceTypeColor & 1) != 0) {
	    	_boardLayer[i] |= bitmask;  // Set this bit to 1.
	    } else {
	    	_boardLayer[i] &= bitFilter;  // Set this bit to 0.
	    }
	    pieceTypeColor >>= 1;
	}
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#doPly(com.Chess.ply.Ply)
	 */
    public final Piece doPly( Ply ply) {
		Piece movedPiece = getPiece( ply.getSource());
		Piece taken = getPiece(ply.getDestination());
		setPiece( movedPiece, ply.getDestination());
		setPiece( null, ply.getSource());
		return taken;
		// If a pawn has just reached the last row
		/*
		 * 
		 * to be continued!!!!
		 * when there is less than X pawns and this type of thing is token
		 * 
		 * 
		 * 
		 */
//		if(ply instanceof TransformationPly) {

//			byte movedPieceColor=movedPiece.getColor();
//			// Set a piece of the new type on the destination square according to the color and destination
//			if(movedPieceColor==1){
//				if(ply.getDestination().getSquareIndex()==0 | ply.getDestination().getSquareIndex()==56)
//					setPiece(new PieceImpl((byte)2,(byte)0),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==8 | ply.getDestination().getSquareIndex()==48)
//					setPiece(new PieceImpl((byte)3,(byte)0),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==16 | ply.getDestination().getSquareIndex()==40)
//					setPiece(new PieceImpl((byte)4,(byte)0),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==24 | ply.getDestination().getSquareIndex()==32)
//					setPiece(new PieceImpl((byte)5,(byte)0),ply.getDestination());
//			
//			}else if(movedPieceColor==0){
//				if(ply.getDestination().getSquareIndex()==56 | ply.getDestination().getSquareIndex()==63)
//					setPiece(new PieceImpl((byte)2,(byte)1),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==57 | ply.getDestination().getSquareIndex()==62)
//					setPiece(new PieceImpl((byte)3,(byte)1),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==58 | ply.getDestination().getSquareIndex()==61)
//					setPiece(new PieceImpl((byte)4,(byte)1),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==59 | ply.getDestination().getSquareIndex()==60)
//					setPiece(new PieceImpl((byte)5,(byte)1),ply.getDestination());
//			}else if(movedPieceColor==0){
//				if(ply.getDestination().getSquareIndex()==7 | ply.getDestination().getSquareIndex()==63)
//					setPiece(new PieceImpl((byte)2,(byte)2),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==15 | ply.getDestination().getSquareIndex()==55)
//					setPiece(new PieceImpl((byte)3,(byte)2),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==23 | ply.getDestination().getSquareIndex()==47)
//					setPiece(new PieceImpl((byte)4,(byte)2),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==31 | ply.getDestination().getSquareIndex()==39)
//					setPiece(new PieceImpl((byte)5,(byte)2),ply.getDestination());
//			}
//			else{
//				if(ply.getDestination().getSquareIndex()==0 | ply.getDestination().getSquareIndex()==7)
//					setPiece(new PieceImpl((byte)2,(byte)3),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==1 | ply.getDestination().getSquareIndex()==6)
//					setPiece(new PieceImpl((byte)3,(byte)3),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==2 | ply.getDestination().getSquareIndex()==5)
//					setPiece(new PieceImpl((byte)4,(byte)3),ply.getDestination());
//				if(ply.getDestination().getSquareIndex()==3 | ply.getDestination().getSquareIndex()==4)
//					setPiece(new PieceImpl((byte)5,(byte)3),ply.getDestination());
//			}
//	
//		} else {
//			// Copy the piece from source square to destination square.
//			setPiece( movedPiece, ply.getDestination());
//		}
		
		
    }
    
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getBoardAfterPly(com.Chess.ply.Ply)
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getBoardAfterPly(com.Chess.ply.Ply)
	 */
    public final Board getBoardAfterPly( Ply ply) {
		BitBoard newBoard = (BitBoard)clone();
		newBoard.doPly( ply);
		return  newBoard;
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getPositionOfPieces(com.Chess.piece.Piece)
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getPositionOfPieces(com.Chess.piece.Piece)
	 */
    public final long getPositionOfPieces( Piece piece) {
    	byte pieceTypeColor = (byte) ((byte)(piece.getColor().getTurn())|(byte)(Type.getIntFromType(piece.getType())));

		return (((pieceTypeColor & 1) != 0 ? _boardLayer[0] : ~_boardLayer[0])
			& ((pieceTypeColor & 2) != 0 ? _boardLayer[1] : ~_boardLayer[1])
			& ((pieceTypeColor & 4) != 0 ? _boardLayer[2] : ~_boardLayer[2])
			& ((pieceTypeColor & 8) != 0 ? _boardLayer[3] : ~_boardLayer[3])
			& ((pieceTypeColor & 16) != 0 ? _boardLayer[4] : ~_boardLayer[4]));
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getEmptySquares()
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getEmptySquares()
	 */
    public final long getEmptySquares() {
	return ~( _boardLayer[2] | _boardLayer[3] | _boardLayer[4]);
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getAllPiecesForColor(chaturaji.game.Color)
	 */
    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getAllPiecesForColor(chaturaji.game.Color)
	 */
    public final long getAllPiecesForColor(Color Color) {
    	byte color = (byte) Color.getTurn();
		return ( ( _boardLayer[2] | _boardLayer[3] | _boardLayer[4]) 
			 & ( (color & 1) == 1 ? _boardLayer[0] : ~_boardLayer[0])
			 & ( ((color >> 1) & 1) == 1 ? _boardLayer[1] : ~_boardLayer[1]));
    }

    /* (non-Javadoc)
	 * @see com.Chess.bitBoard.BitBoard#getBytes()
	 */
    public final byte [] getBytes() {

	        // A buffer for the bytes.
	        byte [] buffer = new byte[ 40];
		int bufferIndex = 0;
	
		for( int layer = 0; layer < 5; layer++) {
	
		    // Get the current layer.
		    long currentLayer = _boardLayer[ layer];
	
		    // Now shift the layer in the buffer.
		    for( int bytePos = 0; bytePos < 8; bytePos++) {
		        buffer[ bufferIndex++] = (byte)( (int)currentLayer & 0xFF);
			currentLayer >>= 8;
		    }
		}
	
		return buffer;  // Return the buffer with the bytes.
    }

    
    
	
}