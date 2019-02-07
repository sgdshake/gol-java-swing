# Game of Life (GOL) - Java Swing

GOL - Swing is a Java implementation of John Conway's "cellular automaton" game which uses Swing to display the GUI.
MIDI functionality is also been introduced through the inclusion of the javax.sound.midi package.

## MIDI Functionality
If you wish to use MIDI functionality then use the alternative constructor :
```bash
new CellGridController(gModel, new MidiPlayer());
```
in the constructor of the GridGui class (see comments for more info).

## Installation

To compile and run:

Checkout as a java project from github using your favourite IDE and and then compile and run GridGui.java

## License
[GNU](https://www.gnu.org/licenses/gpl-3.0.en.html)
