package nl.sourceassist.datastorageutility.state;

/**
 * Enumeration of all the possible states the application might be in.
 * Depending on the state, the interaction with the app is decided.
 */
public enum State {
    STARTING,
    COLLECTING,
    DEFINING,
    MERGING,
    FINISHED
}
