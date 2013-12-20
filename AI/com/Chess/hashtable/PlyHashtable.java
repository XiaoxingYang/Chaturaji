package com.Chess.hashtable;

import com.Chess.board.*;
import com.Chess.ply.*;


/**
 * This interface defines the functionality to control a
 * hashtable with chess plies.
 */
public interface PlyHashtable {

    // Methods

    /**
     * Get the maximum number of entries in the hashtable.
     *
     * @return The maximum number of entries in the hashtable.
     */
    int getMaximumSize();

    /**
     * Set the maximum number of entries in the hashtable.
     *
     * @param maximumEntries The new maximum number of entries.
     */
    void setMaximumSize( int maximumEntries);

    /**
     * Get the current number of entries.
     *
     * @return The current number of entries.
     */
    int getSize();

    /**
     * Try to push a new entry into the hashtable.
     *
     * @param entry The new entry, that the hashtable might store.
     */
    void pushEntry( PlyHashtableEntry ply);

    /**
     * Get the stored ply for a given board and piece color.
     *
     * @param board The board before the move.
     * @param white Flag to indicate, if we want a move with the certain pieces.
     */
    Ply getPly( Board board, byte color);

    /**
     * Try to remove the oldest entry.
     */
    void removeOldestEntry();
}

