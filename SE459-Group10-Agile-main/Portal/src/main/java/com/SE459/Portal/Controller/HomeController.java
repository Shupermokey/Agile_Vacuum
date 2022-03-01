package com.SE459.Portal.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Logger;

@Controller()
public class HomeController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @RequestMapping("")
    public RedirectView index(HttpServletRequest req) {
        // Redirect To Login if we don't have a logged in User.
        if (req.getSession().getAttribute("email") == null){
            return new RedirectView("Login");
        }

    	return new RedirectView("Scheduler");
    }
}
