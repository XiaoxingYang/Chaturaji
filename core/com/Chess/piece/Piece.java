package com.Chess.piece;

import chaturaji.game.Color;
import chaturaji.game.Type;

public interface Piece {

	/**
	 * Get the type of this piece.
	 *
	 * @return The type of this piece.
	 */
	public abstract Type getType();

	/**
	 * Set the type of this piece.
	 *
	 * @param type The type of this piece as defined as 
	 *             constants in the Piece interface.
	 */
	public abstract void setType(Type type);

	/**
	 * Get the color of this piece.
	 *
	 * @return The color of this piece.
	 */
	public abstract Color getColor();

	/**
	 * Set the color of this piece.
	 *
	 * @param The new color of this piece.
	 */
	public abstract void setColor(Color color);
	
	public int getPoints();

}