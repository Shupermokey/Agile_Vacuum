package com.SE459.Portal.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.SE459.Portal.Dao.LogDao;
import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Log;
import com.SE459.Portal.Service.AccountService;
import com.SE459.Portal.Service.DeviceService;
import com.SE459.Portal.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControlSystemController {
	
	public static List<String> log = new ArrayList<String>();

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	AccountService accountService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	LogService logService;

	@Autowired
	LogDao logDao;
	
	@RequestMapping("/ControlSystem")
	public String controlSystem(HttpServletRequest req) {

		HttpSession session = req.getSession();

		// Redirect To Login if we don't have a logged in User.
		if (req.getSession().getAttribute("email") == null){
			return "Login";
		}

		// Get Current User from session so we can get devices and logs.
		String email = (String)session.getAttribute("email");
		Account currentAccount = accountService.getAccountByEmail(email);

		session.setAttribute("deviceList", currentAccount.getDevicesList());
		session.setAttribute("log", logService.getLog(email));
		session.setAttribute("battery", deviceService.getBattery(email));

		return "ControlSystem";
	}
	
	@PostMapping("/ControlSystemButton")
	public ModelAndView ControlSystemButton(Model model, HttpServletRequest req) throws InterruptedException {
		//req.getSession().setAttribute("floor", floor);
		ModelAndView mv = new ModelAndView();
		HttpSession session = req.getSession();
		String email = (String)session.getAttribute("email");
		//call service to perform the one-button cleaning
		int accountId = accountService.getAccountByEmail(email).getAccountId();
		DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String currentDateTime = LocalDateTime.now().format(dateTimeFormat);
		log.add("[" + currentDateTime + "]: One-Button Cleaning was activated.");
		Log newTimeStampLog = new Log();
		newTimeStampLog.setLog("[" + currentDateTime + "]: One-Button Cleaning was activated.");
		newTimeStampLog.setAccountId(accountId);
		logDao.save(newTimeStampLog);
		// Execute the One-Time Cleaning and retrieve the logs from the run.
		ArrayList<String> cleaningLogs = deviceService.oneButtonClean(email); // change doAlgorithm to OneButtonCleaning()
		for (String cleanLog : cleaningLogs) {
			log.add(cleanLog);
			Log newLog = new Log();
			newLog.setAccountId(accountId);
			newLog.setLog(cleanLog);
			logDao.save(newLog);
		}


		session.setAttribute("battery", deviceService.getBattery(email));
		session.setAttribute("log", log);

		//service should have an algorithm
			//clean until cancelled || clean until floor is clean
			//If request is made outside of the preconfigured hoours, user confirms action
			//If user chooses not to clean right away, guide user to the One-Time Cleaning
		mv.setViewName("redirect:/ControlSystem");

		return mv;
		}
	
	@PostMapping("/ControlSystemTime")
	public String ControlSystemTime(Model model, HttpServletRequest req) {
		//req.getSession().setAttribute("floor", floor);
		HttpSession session = req.getSession();
		log.add("One-Time cleaning: Activate");
		session.setAttribute("log", log);
		//call service to perform the one-time cleaning
			// define a one-time schedule
			// can be set for a future time period
		
		
		return "ControlSystem";
		}
	
	@PostMapping("/ControlSystemRepeat")
	public String ControlSystemRepeat(Model model, HttpServletRequest req) {
		//req.getSession().setAttribute("floor", floor);
		HttpSession session = req.getSession();
		log.add("Repeated cleaning: Activate");
		session.setAttribute("log", log);
		//call service to perform the repeated cleaning
			//define a repeated schedule
			//
		
		return "ControlSystem";
		}

}
