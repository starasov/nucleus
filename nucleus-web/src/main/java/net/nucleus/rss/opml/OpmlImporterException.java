package net.nucleus.rss.opml;

import net.nucleus.rss.NucleusException;

/**
 * User: starasov
 * Date: 5/14/13
 * Time: 9:02 PM
 */
public class OpmlImporterException extends NucleusException {
    public OpmlImporterException(String message) {
        super(message);
    }

    public OpmlImporterException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpmlImporterException(Throwable cause) {
        super(cause);
    }
}
