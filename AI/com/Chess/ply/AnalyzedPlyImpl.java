package com.Chess.ply;

/**
 * Utility class for plies and their scores.
 */
public class AnalyzedPlyImpl implements AnalyzedPly {


    // Instance variables

    /**
     * The analyzed ply.
     */
    private Ply _ply;

    /**
     * The result of the analysis.
     */
    private short _score;


    // Constructors

    /**
     * Create a new AnalyzedPly instance.
     */
    public AnalyzedPlyImpl( Ply ply, short score) {
	setPly( ply);
	setScore( score);
    }

    
    // Methods

    /**
     * Get the analyzed ply.
     *
     * @return The analyzed ply.
     */
    public final Ply getPly() {
	return _ply;
    }
    
    /**
     * Set a new ply.
     *
     * @param ply The new ply.
     */
    public final void setPly( Ply ply) {
	_ply = ply;
    }

    /**
     * Get the scrore of this ply.
     *
     * @return The score of this ply.
     */
    public final short getScore() {
	return _score;
    }

    /**
     * Set a new scrore for this ply.
     *
     * @param score The new score for this ply.
     */
    public final void setScore( short score) {
	_score = score;
    }

    /**
     * Clone this ply.
     *
     * @return A clone of this ply.
     */
    public Object clone() {
	return new AnalyzedPlyImpl( getPly(), getScore());
    }
}

