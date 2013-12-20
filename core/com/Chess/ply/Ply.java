package com.Chess.ply;

import com.Chess.position.Position;

public interface Ply {

    // Static variables


    // Methods
    
    /**
     * Get the source of the ply.
     */
    Position getSource();

    /**
     * Set the source of the ply.
     */
    void setSource( Position source);

    /**
     * Get the destination of the piece.
     */
    Position getDestination();

    /**
     * Set the destination of the piece.
     */
    void setDestination( Position destination);

    /**
     * Test, if this ply is equal to another ply.
     *
     * @param ply The other ply.
     *
     * @return true, if the 2 plies are equal.
     */
    boolean equals( Ply ply);
}

