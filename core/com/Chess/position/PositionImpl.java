package com.Chess.position;

public class PositionImpl implements Position {

    // Instance variables

    // The index of the chessboard square.
    private int _squareIndex;


    // Constructors

    public PositionImpl( int squareIndex) {
    	setSquareIndex( squareIndex);
    }
    
    public PositionImpl(String sqString) {
		char c = sqString.charAt(0);
		int n = Integer.parseInt(sqString.substring(1));
		_squareIndex = (8-n)*8+c-'A';
    }


    // Methods

    /**
     * Get the square index of this position (0-63).
     *
     * @return The square index of this position.
     */
    public final int getSquareIndex() {
    	return _squareIndex;
    }

    /**
     * Set the square index of this position.
     *
     * @param squareIndex The new square index of this position.
     */
    public final void setSquareIndex( int squareIndex) {
    	_squareIndex = squareIndex;
    }

    /**
     * Get the row index of this position (0-7).
     *
     * @return The row index of this position.
     */
    public final int getRowIndex() {
    	return _squareIndex >> 3;
    }

    /**
     * Get the line index of this postion (0-7).
     *
     * @return The line index of this position.
     */
    public final int getLineIndex() {
    	return _squareIndex & 7;
    }

    /**
     * Convert this position to a square name (like 'a4').
     *
     * @return The suare name of this position.
     */
    public final String toSquareName() {
		byte [] byteRepresentation = new byte[2];
		byteRepresentation[0] = (byte)((int)'A' + getLineIndex());
		byteRepresentation[1] = (byte)('0' + (char)(8 - getRowIndex()));
		System.out.println("djsdlfkjlksj: " + getRowIndex());
	
		return new String( byteRepresentation);
    }

    /**
     * Test if 2 positions are equal.
     *
     * @param Another position.
     *
     * @return true, if the positions are equal, false otherwise.
     */
    @Override
    public final boolean equals( Position pos) {
    	System.out.println("derpla");
    	return pos.getSquareIndex() == getSquareIndex();
    }
}
