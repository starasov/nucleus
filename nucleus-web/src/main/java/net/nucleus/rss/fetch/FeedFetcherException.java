package net.nucleus.rss.fetch;

import net.nucleus.rss.NucleusException;

/**
 * User: starasov
 * Date: 5/12/13
 * Time: 1:43 PM
 */
public class FeedFetcherException extends NucleusException {
    public FeedFetcherException(String message) {
        super(message);
    }

    public FeedFetcherException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeedFetcherException(Throwable cause) {
        super(cause);
    }
}
