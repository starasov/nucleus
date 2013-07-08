package net.nucleus.rss.service;

import net.nucleus.rss.NucleusException;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 8:02 PM
 */
public class ImportServiceException extends NucleusException {
    public ImportServiceException(String message) {
        super(message);
    }

    public ImportServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImportServiceException(Throwable cause) {
        super(cause);
    }
}
