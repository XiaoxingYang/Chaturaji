package com.Chess.ply;

/**
 * Utility class for plies and their scores.
 */
public interface AnalyzedPly {


    // Static class variables

    /**
     * The maximum score.
     */
    short MAX_SCORE = Short.MAX_VALUE;

    /**
     * The minimum score.
     */
    short MIN_SCORE = Short.MIN_VALUE;

    
    // Methods

    /**
     * Get the analyzed ply.
     *
     * @return The analyzed ply.
     */
    Ply getPly();
    
    /**
     * Set a new ply.
     *
     * @param ply The new ply.
     */
    void setPly( Ply ply);

    /**
     * Get the scrore of this ply.
     *
     * @return The score of this ply.
     */
    short getScore();

    /**
     * Set a new scrore for this ply.
     *
     * @param score The new score for this ply.
     */
    void setScore( short score);

    /**
     * Clone this ply.
     *
     * @return A clone of this ply.
     */
    Object clone();
}
