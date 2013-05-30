package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.User;
import net.nucleus.rss.service.FeedEntryService;
import net.nucleus.rss.service.FeedEntryServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

/**
 * User: starasov
 * Date: 5/1/13
 * Time: 8:20 PM
 */
@Controller
@RequestMapping("/feed")
public class ReaderController {
    private static final int ITEMS_PER_PAGE = 30;

    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);

    private FeedEntryService feedEntryService;

    @RequestMapping("/{feedId}")
    public ModelAndView feed(@PathVariable("feedId") int feedId) throws FeedEntryServiceException {
        logger.debug("[index] - begin - feedId: {}", feedId);

        Outline feed = feedEntryService.findFeed(getLoggedInUser(), feedId);
        feedEntryService.updateFeed(feed);

        long feedEntriesCount = feedEntryService.feedEntriesCount(feed);
        logger.debug("[feed] - feedEntriesCount: {}", feedEntriesCount);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("feed", feed);
        modelAndView.addObject("totalEntryPages", (feedEntriesCount / ITEMS_PER_PAGE) + 1);

        logger.debug("[index] - end");
        return modelAndView;
    }

    @RequestMapping("/{feedId}/entries")
    @ResponseBody
    public List<FeedEntry> feedEntries(@PathVariable("feedId") int feedId,
                                       @RequestParam(value = "page", defaultValue = "0") int page) throws FeedEntryServiceException {
        logger.debug("[feedEntries] - begin - feedId: {}", feedId);
        Outline feed = feedEntryService.findFeed(getLoggedInUser(), feedId);
        return feedEntryService.findEntries(feed, ITEMS_PER_PAGE * page, ITEMS_PER_PAGE);
    }

    @RequestMapping("/{feedId}/refresh")
    @ResponseBody
    public FeedRefreshResult feedRefresh(@PathVariable("feedId") int feedId) throws FeedEntryServiceException {
        logger.debug("[feedRefresh] - begin - feedId: {}", feedId);

        Outline feed = feedEntryService.findFeed(getLoggedInUser(), feedId);
        Set<FeedEntry> newFeedEntries = feedEntryService.updateFeed(feed);
        long feedEntriesCount = feedEntryService.feedEntriesCount(feed);

        return new FeedRefreshResult((feedEntriesCount / ITEMS_PER_PAGE) + 1, newFeedEntries);
    }

    @RequestMapping("/mark-as-read/{entryId}")
    @ResponseBody
    public void markEntryAsRead(@PathVariable("entryId") int entryId) {
        logger.debug("[markEntryAsRead] - begin - entryId: {}", entryId);
        feedEntryService.markEntryAsRead(getLoggedInUser(), entryId);
        logger.debug("[markEntryAsRead] - end");
    }

    @Autowired
    public void setFeedEntryService(FeedEntryService feedEntryService) {
        this.feedEntryService = feedEntryService;
    }

    private User getLoggedInUser() {
        return null;
    }
}
