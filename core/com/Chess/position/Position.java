package com.Chess.position;

public interface Position {

    // Methods

    /**
     * Get the square index of this position instance.
     *
     * @return The square index of this piece position (0-63).
     */
    int getSquareIndex();

    /**
     * The square index of this position instance.
     *
     * @param index The square index of this position (0-63).
     */
    void setSquareIndex( int index);
  
    /**
     * Get the row index of this position.
     *
     * @return The row index of this position (0-7).
     */
    int getRowIndex();

    /**
     * Get the line index of this position.
     *
     * @return The line index of this position (0-7).
     */
    int getLineIndex();

    /**
     * Get the name of this square.
     *
     * @return The suare name of this position (i.e. a4).
     */
    String toSquareName();

    /**
     * Test if 2 positions are equal.
     *
     * @param Another position.
     *
     * @return true, if the positions are equal, false otherwise.
     */
    boolean equals( Position pos);
    
    
}
