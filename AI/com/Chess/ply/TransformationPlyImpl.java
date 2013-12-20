package com.Chess.ply;


import com.Chess.position.*;


/**
 * This class is for pawn plies, that reach the last row
 * and transform into a new piece type.
 */
public class TransformationPlyImpl extends PlyImpl implements TransformationPly {


    // Instance variables.

    /**
     * The new piece type after the transformation.
     */
    byte _newPieceType;

    
    // Constructors

    /**
     * Create a new transformation ply instance.
     *
     * @param source The source square.
     * @param destination The destination square.
     * @param pieceType The piece type after the transformation.
     */
    public TransformationPlyImpl( Position source, Position destination, byte pieceType) {
		super( source, destination);
		setTypeAfterTransformation( pieceType);
    }


    // Methods

    /**
     * Get the new type of the piece after the transformation.
     *
     * @return The new piece after the transformation.
     */
    public final byte getTypeAfterTransformation() {
    	return _newPieceType;
    }

    /**
     * Set the new piece type after the transformation.
     *
     * @param pieceType The new piece type after the transformation.
     */
    public final void setTypeAfterTransformation( byte pieceType) {
    	_newPieceType = pieceType;
    }
}

