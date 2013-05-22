package net.nucleus.rss.service;

import net.nucleus.rss.NucleusException;

/**
 * User: starasov
 * Date: 5/15/13
 * Time: 8:02 PM
 */
public class FeedEntryServiceException extends NucleusException {
    public FeedEntryServiceException(String message) {
        super(message);
    }

    public FeedEntryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedEntryServiceException(Throwable cause) {
        super(cause);
    }
}
