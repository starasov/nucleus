package net.nucleus.rss.controller;

import net.nucleus.rss.model.User;
import net.nucleus.rss.service.ImportService;
import net.nucleus.rss.service.ImportServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * User: starasov
 * Date: 7/7/13
 * Time: 3:31 PM
 */
@Controller
@RequestMapping("/import")
public class ImportController {

    private ImportService importService;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("import");
    }

    @RequestMapping(value = "/ompl", method = RequestMethod.POST)
    public ModelAndView importOmpl(@RequestParam("file") MultipartFile omplFile, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            boolean success = importService.importOmpl(user, omplFile.getInputStream());
            if (!success) {
                return createFailureResult("No feed have been found. Please try to upload another OMPL file.");
            }
        } catch (ImportServiceException e) {
            return createFailureResult("We could not recognize your OMPL file contents. Please try to upload another OMPL file.");
        } catch (IOException e) {
            return createFailureResult("Something went terribly wrong. Please try one more time.");
        }

        return new ModelAndView("redirect:/feed/");
    }

    @Autowired
    public void setImportService(ImportService importService) {
        this.importService = importService;
    }

    private ModelAndView createFailureResult(String message) {
        ModelAndView modelAndView = new ModelAndView("import");
        modelAndView.addObject("importFailed", true);
        modelAndView.addObject("errorMessage", message);
        return modelAndView;
    }
}
