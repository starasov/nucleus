package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.service.FeedEntryService;
import net.nucleus.rss.service.FeedEntryServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * User: starasov
 * Date: 5/1/13
 * Time: 8:20 PM
 */
@Controller
@RequestMapping("/feed")
public class ReaderController {
    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);

    private FeedEntryService feedEntryService;

    @RequestMapping("/{feedId}")
    public ModelAndView combined(@PathVariable("feedId") int feedId) throws FeedEntryServiceException {
        logger.debug("[{}][index] - begin - feedId: {}", this.getClass().getSimpleName(), feedId);

        ModelAndView modelAndView = new ModelAndView("index");

        Outline feed = feedEntryService.findFeed(feedId);
        modelAndView.addObject("feed", feed);

        feedEntryService.updateFeed(feed);
        List<FeedEntry> list = feedEntryService.findEntries(feed, 0, 30);

        modelAndView.addObject("entries", list);

        logger.debug("[{}][index] - end", this.getClass().getSimpleName());
        return modelAndView;
    }

    @Autowired
    public void setFeedEntryService(FeedEntryService feedEntryService) {
        this.feedEntryService = feedEntryService;
    }
}
