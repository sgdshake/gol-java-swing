package org.sgdshake.gol.midi;

import javax.sound.midi.*;

/**
 * MIDI class to trigger sounds as the GOL cells evolve
 *
 * @author sgdshake
 */
public class MidiPlayer {

    private static final int instrument = 36;

    /**
     * Initialises MIDI player for subsequent use
     *
     * @param note
     */
    public void init(int note) {
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();

            // Set instrument type
            track.add(makeEvent(192, 1, instrument, 0, 1));

            // Add a note on event with specified note
            track.add(makeEvent(144, 1, note, 100, 1));

            // Add a note off event with specified note
            track.add(makeEvent(128, 1, note, 100, 2));

            track.add(makeEvent(224, 1, note, 100, 1));

            // Add attack to the midi channel, tick corespondes to timestamp of event
            track.add(new MidiEvent(new ShortMessage(ShortMessage.CONTROL_CHANGE, 1, 73, 90), 1));

            // Add resonence to the filter
            track.add(new MidiEvent(new ShortMessage(ShortMessage.CONTROL_CHANGE, 1, 71, 110), 1));

            sequencer.setSequence(sequence);
            sequencer.start();
            Thread.sleep(500); // Sleep to allow time for the sequencer to play
            sequencer.close();

            while (true) {
                if (!sequencer.isRunning()) {
                    sequencer.close();
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Helper method to map notes existing in a given range to a valid midi range
     *
     * @param x
     * @param y
     * @param minRange
     * @param maxRange
     * @return valid midi note
     */
    public int mapToMidiRange(double x, double y, double minRange, double maxRange) {
        double multGrid = x * y;
        int lowerMidiBound = 100;
        int upperMidiBound = 120;

        // output = output_start + ((output_end - output_start) / (input_end - input_start)) * (input - input_start)
        double mappedNote = (lowerMidiBound + ((upperMidiBound - lowerMidiBound) / (maxRange - minRange)) * (multGrid - minRange));

        System.out.println("mapped note: " + mappedNote);
        return (int) mappedNote;  // Cast double to int
    }

    /**
     * Creates a MIDI event object to play a short note / pattern
     *
     * @param command
     * @param channel
     * @param note
     * @param velocity
     * @param tick
     * @return MidiEvent
     */
    private MidiEvent makeEvent(int command, int channel,
                                int note, int velocity, int tick) {

        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(command, channel, note, velocity);
            event = new MidiEvent(a, tick);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return event;
    }
}






