package com.Chess.hashtable;

import java.util.Hashtable;
import java.util.LinkedList;
import com.Chess.board.Board;
import com.Chess.hashtable.*;
import com.Chess.ply.Ply;



/**
 * This class implements the functionality to store chess plies in
 * a hashtable.
 */
public class PlyHashtableImpl implements PlyHashtable {

    // Instance variables

    /**
     * The maximum number of entries.
     */
    private int _maxSize;

    /**
     * A hashtable to store the plies.
     */
    Hashtable _hashtable;

    /**
     * A linked list to reproduce the order of
     * the inserted plies.
     */
    LinkedList _orderedList;


    // Constructors

    /**
     * Create a new hashtable instance with a given maximum size.
     *
     * @param maxSize The maximum number of entries.
     */
    public PlyHashtableImpl( int maxSize) {
	_hashtable = new Hashtable();
	_orderedList = new LinkedList();
	setMaximumSize( maxSize);
    }


    // Methods

    /**
     * Get the maximum number of entries in the hashtable.
     *
     * @return The maximum number of entries in the hashtable.
     */
    public final int getMaximumSize() {
	return _maxSize;
    }

    /**
     * Set the maximum number of entries in the hashtable.
     *
     * @param maximumEntries The new maximum number of entries.
     */
    public final void setMaximumSize( int maximumEntries) {
	_maxSize = maximumEntries;

	// Remove the oldest entries, if the current size
	// is bigger than the new maximum size.
	while( getSize() > getMaximumSize()) {
	    removeOldestEntry();
	}
    }

    /**
     * Get the current number of entries.
     *
     * @return The current number of entries.
     */
    public final int getSize() {
	return _hashtable.size();
    }

    /**
     * Try to push a new entry into the hashtable.
     *
     * @param entry The new entry, that the hashtable might store.
     */
    public final void pushEntry( PlyHashtableEntry ply) {

	// Compute and store the key for this ply.
	Long hashKey = new Long( ply.hashKey());

	// Check if this ply is not already in the hashtable
	if( _hashtable.containsKey( hashKey)) {
	    return;
	}

	// Check, if the hashtable has not exceeded it's maximum capacity
	// and remove older entries, if necessary.
	while( getSize() >= getMaximumSize()) {
	    removeOldestEntry();
	}

	// Add the new ply to the hashtable
	_hashtable.put( hashKey, ply);
	
	// Append the new ply at the end of the list of plies.
	// ATTENTION: this will only work, if there's only _one_
	// ply for each key!
	_orderedList.add( hashKey);
    }

    /**
     * Get the stored ply for a given board and piece color.
     *
     * @param board The current board.
     * @param white true, if we want to ply with the white pieces.
     */
    public final Ply getPly( Board board, byte color) {
	
	// Compute the hashkey for the board and piece color and try to get a ply.
	PlyHashtableEntry entry = (PlyHashtableEntry)( _hashtable.get( new Long( PlyHashtableEntryImpl.hashKey( board, color))));

	// Check, if the board from the entry is identical to the passed board.
	if( ( entry != null) && entry.getBoard().equals( board)) {
	    return entry.getPly();
	}

	// No ply for this board found.
	return null;
    }

    /**
     * Try to remove the oldest entry.
     * ATTENTION: this version will only work, if there's only _one_ entry for each hashkey!
     */
    public final void removeOldestEntry() {

	// Get and remove the oldest ply from the ordered list.
	_hashtable.remove( _orderedList.removeFirst());
    }




	
}
