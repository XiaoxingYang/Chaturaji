package com.Chess.ply;

public interface TransformationPly extends Ply {

    // Methods

    /**
     * Get the new type of the piece after the transformation.
     *
     * @return The new piece after the transformation.
     */
    byte getTypeAfterTransformation();

    /**
     * Set the new piece type after the transformation.
     *
     * @param pieceType The new piece type after the transformation.
     */
    void setTypeAfterTransformation( byte pieceType);
}
