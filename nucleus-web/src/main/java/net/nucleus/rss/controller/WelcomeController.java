package net.nucleus.rss.controller;

import net.nucleus.rss.model.FeedEntry;
import net.nucleus.rss.model.Outline;
import net.nucleus.rss.model.OutlineType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/")
    @Transactional
    public ModelAndView index() {
        logger.debug("[{}][index] - begin", this.getClass().getSimpleName());

        List<FeedEntry> list = new ArrayList<FeedEntry>();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("entries", list);

        Outline outline = new Outline();
        outline.setType(OutlineType.FOLDER);
        outline.setTitle("Test");
        entityManager.persist(outline);

        logger.debug("[{}][index] - end", this.getClass().getSimpleName());
        return modelAndView;
    }
}
