package com.Chess.hashtable;

import chaturaji.game.Color;

import com.Chess.board.*;
import com.Chess.ply.*;



/**
 * This interface defines the functionality of a entry in
 * the ply hashtable.
 */
public interface PlyHashtableEntry {

    // Methods

    /**
     * Get the color, that is supposed to apply this ply.
     *
     */
	Color getColor();

    /**
     * Get the game position before the ply is applied.
     *
     * @return The board before the ply is applied.
     */
    Board getBoard();

    /**
     * Get the ply.
     *
     * @return The ply.
     */
    Ply getPly();

    /**
     * Request a key for this entry.
     *
     * @return The key for this entry.
     */
    long hashKey();
}
