package com.Chess.timer;

/**
 * A interface to define a game timer.
 */
public interface GameTimer {

    // Methods

    /**
     * Start the entire timer.
     */
    void start();

    /**
     * Toggle from one player to the other.
     */
    void toggle();

    /**
     * Stop the entire timer.
     */
    void stop();

    /**
     * Check if the timer is actually running.
     *
     * @return true, if the timer is active, false otherwise.
     */
    boolean isRunning();
}
