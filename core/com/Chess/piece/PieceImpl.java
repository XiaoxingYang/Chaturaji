package com.Chess.piece;

import chaturaji.game.Color;
import chaturaji.game.Type;

/*
PieceImpl - A class to implement the functionality of a chess piece.
*/ 

public class PieceImpl implements Piece {


  // Instance variables

  /**
   * The color and type of a piece.
   */
	private Color _color;
    private Type _type;
  // Constructors

  /**
   * Create a new piece instance.
   *
   * @param typeColor The color and type of piece, with color in bit 0-1 
   *                  and the type in bit 2-4.
   */
  public PieceImpl( Color color, Type type) {
	_color = color;
	_type = type;
  }

  // Methods

  public final Type getType() {
	return _type;
  }

  public final void setType( Type type) {
	_type = type;
  }

  public final Color getColor() {
	return _color;
  }

  public final void setColor(Color color) {
	_color = color;
  }

@Override
public int getPoints() {
	return _type.getType();
}


}



