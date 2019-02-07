package org.sgdshake.gol.listeners;

/**
 * Interface to modify round value during cell evolution
 *
 * @author sgdshake
 */
public interface RoundChangeListener {
    void roundChanged(int roundValue);
}
