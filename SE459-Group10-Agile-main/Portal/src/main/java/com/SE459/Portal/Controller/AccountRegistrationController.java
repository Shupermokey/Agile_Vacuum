package com.SE459.Portal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.SE459.Portal.Service.AccountService;

import java.util.logging.Logger;

@Controller()
public class AccountRegistrationController {

	@Autowired
	AccountService accountService;

	private final Logger logger = Logger.getLogger(AccountRegistrationController.class.getName());

    @RequestMapping("/AccountRegistration")
    public String register(ModelAndView model) {
		model.addObject("AccountStatus", "");
		return "AccountRegistration"; }
    
    @PostMapping("/createAccount")
    public ModelAndView createAccount(String email, String password)
	{
		ModelAndView model = new ModelAndView();

		try{
			boolean createdAccount = accountService.createAccount(email, password);

			if(createdAccount) {
				model.addObject("AccountStatus", "Account Created Successfully.");
				model.setViewName("Login");
				logger.info("Create Account was Successful.");
				return model;
			}
		}
		catch (Exception e) {
			logger.throwing(this.getClass().getName(), "createAccount", e);
		}

    	model.addObject("AccountStatus", "Cannot Create Account.");
		model.setViewName("AccountRegistration");

		return model;
    }
}
