package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: starasov
 * Date: 5/1/13
 * Time: 8:20 PM
 */
@Controller
@RequestMapping("/")
public class WelcomeController {
    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @RequestMapping("/")
    @Transactional
    public ModelAndView index() {
        logger.debug("[{}][index] - begin", this.getClass().getSimpleName());

        List<FeedEntry> list = new ArrayList<FeedEntry>();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("entries", list);

        logger.debug("[{}][index] - end", this.getClass().getSimpleName());
        return modelAndView;
    }
}
