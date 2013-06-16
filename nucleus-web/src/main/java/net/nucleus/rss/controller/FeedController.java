package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.User;
import net.nucleus.rss.service.FeedEntryServiceException;
import net.nucleus.rss.service.FeedService;
import net.nucleus.rss.service.UserService;
import org.dozer.Mapper;
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
public class FeedController {
    private static final int ITEMS_PER_PAGE = 30;

    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    private FeedService feedService;
    private UserService userService;
    private Mapper mapper;

    @RequestMapping("/{feedId}")
    public ModelAndView feed(@PathVariable("feedId") int feedId) throws FeedEntryServiceException {
        logger.debug("[index] - begin - feedId: {}", feedId);

        Outline feed = feedService.findFeed(getLoggedInUser(), feedId);
        feedService.updateFeed(feed);

        long feedEntriesCount = feedService.feedEntriesCount(feed);
        logger.debug("[feed] - feedEntriesCount: {}", feedEntriesCount);

        ModelAndView modelAndView = new ModelAndView("feed");
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
        Outline feed = feedService.findFeed(getLoggedInUser(), feedId);
        return feedService.findEntries(feed, ITEMS_PER_PAGE * page, ITEMS_PER_PAGE);
    }

    @RequestMapping("/{feedId}/refresh")
    @ResponseBody
    public FeedRefreshResult feedRefresh(@PathVariable("feedId") int feedId) throws FeedEntryServiceException {
        logger.debug("[feedRefresh] - begin - feedId: {}", feedId);

        Outline feed = feedService.findFeed(getLoggedInUser(), feedId);
        Set<FeedEntry> newFeedEntries = feedService.updateFeed(feed);
        long feedEntriesCount = feedService.feedEntriesCount(feed);

        return new FeedRefreshResult((feedEntriesCount / ITEMS_PER_PAGE) + 1, newFeedEntries);
    }

    @RequestMapping("/mark-as-read/{entryId}")
    @ResponseBody
    public void markEntryAsRead(@PathVariable("entryId") int entryId) {
        logger.debug("[markEntryAsRead] - begin - entryId: {}", entryId);
        feedService.markEntryAsRead(getLoggedInUser(), entryId);
        logger.debug("[markEntryAsRead] - end");
    }

    @RequestMapping("/outline")
    @ResponseBody
    public OutlineResult outline() {
        logger.debug("[outline] - begin");

        Outline rootOutline = feedService.findRootOutline(getLoggedInUser());
        OutlineResult outlineResult = mapper.map(rootOutline, OutlineResult.class);

        return outlineResult;
    }

    @Autowired
    public void setFeedService(FeedService feedService) {
        this.feedService = feedService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    private User getLoggedInUser() {
        return userService.login("test");
    }
}
