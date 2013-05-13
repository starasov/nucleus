package net.nucleus.rss;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 1:43 PM
 */
public class NucleusException extends Exception {

    public NucleusException(String message) {
        super(message);
    }

    public NucleusException(String message, Throwable cause) {
        super(message, cause);
    }

    public NucleusException(Throwable cause) {
        super(cause);
    }
}
