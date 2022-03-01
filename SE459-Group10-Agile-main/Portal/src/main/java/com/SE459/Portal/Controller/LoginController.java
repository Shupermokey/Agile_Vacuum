package com.SE459.Portal.Controller;

import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Error;
import com.SE459.Portal.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@Controller()
public class LoginController {

    @Autowired
    AccountService accountService;

    HttpSession session;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public LoginController() {
    }

    @RequestMapping("/Login")
    public String login(Model model) {
        model.addAttribute("Account", new Account());

        return "Login";
    }

    @PostMapping("/Login")
    public ModelAndView login(Account account, HttpServletRequest req)
    {
        ModelAndView model = new ModelAndView();

        try{
            session = req.getSession();

            // Attempts to login with the current sessions credentials.
            boolean validation = accountService.validateLogin(account.getEmail(), account.getPass());

            if(validation) {
                session.setAttribute("email", account.getEmail());
                session.setAttribute("pass", account.getPass());
                session.setAttribute("accountId", account.getAccountId());
                model.setView(new RedirectView(""));

                logger.info("Authentication was successful: " + account.toString());
                return model;
            }

            // Add an error object to the view model if validation fails.
            logger.info("Invalid Username or Password: "  + account.toString());
            model.addObject("Error", new Error(403,"Invalid Username or Password."));
        }
        catch (Exception e) {
            logger.throwing("AccountRegistrationController", "login", e);
        }

        model.setViewName("Login");
        return model;
    }

    @PostMapping("/Logout")
    public ModelAndView logout(HttpServletRequest req) {
        ModelAndView newModel = new ModelAndView();
        session = req.getSession();
        session.removeAttribute("email");
        session.removeAttribute("pass");
        session.removeAttribute("accountId");
        session.removeAttribute("log");
        session.removeAttribute("battery");

        newModel.clear();
        newModel.setView(new RedirectView("Login"));

        return newModel;
    }
}
