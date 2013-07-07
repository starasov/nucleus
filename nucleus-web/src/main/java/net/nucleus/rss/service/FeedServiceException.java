package net.nucleus.rss.service;

import net.nucleus.rss.NucleusException;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 8:02 PM
 */
public class FeedServiceException extends NucleusException {
    public FeedServiceException(String message) {
        super(message);
    }

    public FeedServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedServiceException(Throwable cause) {
        super(cause);
    }
}
