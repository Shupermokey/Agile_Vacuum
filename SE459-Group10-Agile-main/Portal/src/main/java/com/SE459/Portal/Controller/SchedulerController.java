package com.SE459.Portal.Controller;

import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Device;
import com.SE459.Portal.Entity.Scheduler;
import com.SE459.Portal.Service.AccountService;
import com.SE459.Portal.Service.DeviceService;
import com.SE459.Portal.Service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller()
public class SchedulerController {

	@Autowired
	ScheduleService scheduleService;

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	AccountService accountService;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	@RequestMapping("/Scheduler")
    public ModelAndView simulate(Model model, HttpServletRequest req) {
			ModelAndView mv = new ModelAndView();

		// Redirect To Login if we don't have a logged in User.
		if (req.getSession().getAttribute("email") == null){
			mv.setViewName("redirect:/Login");
			return mv;
		}

		HttpSession session = req.getSession();
		session = updateSessionDeviceList(session, mv);
		mv.setViewName("Scheduler");
		return mv;
	}

    @PostMapping("/Schedule")
    public ModelAndView schedule(Model model, String M, String T, String W, String Th, String F, String S, String Sun, String appt, String freq, HttpServletRequest req) {
    	HttpSession session = req.getSession();
		ModelAndView mv = new ModelAndView();
		// Redirect To Login if we don't have a logged in User.
		if (req.getSession().getAttribute("email") == null){
			return new ModelAndView("Login");
		}

		mv.setViewName("redirect:/Scheduler");

		String email = (String)session.getAttribute("email");
		Account account = accountService.getAccountByEmail(email);
    	//scheduleService.generateSchedule(M, T, W, Th, F, S, Sun, email);
		scheduleService.generateSchedule(email, M, T, W, Th, F, S, Sun, appt, freq);
    	
    	Scheduler schedule = scheduleService.findSchedule();
    	model.addAttribute("schedule", schedule);

		List<Device> deviceList = account.getDevicesList();
		deviceList.get(0).scheduler = schedule;
		account.setDevice(deviceList.get(0));
		accountService.saveAccount(account);

		session = updateSessionDeviceList(session, mv);

    	System.out.println(schedule);

		return mv;
	}

	@PostMapping("/DeleteDevice")
	public ModelAndView deleteDevice(String serialNumber, HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Scheduler");
		try {
			String normalizedSerialNumber = serialNumber.trim();
			HttpSession session = req.getSession();
			String email = (String)session.getAttribute("email");
			Account currentUser = accountService.getAccountByEmail(email);
			logger.info(getClass().getName() + ": Attempting to delete device " + serialNumber);
			List<Device> deviceToDelete = currentUser
												.getDevicesList()
												.stream()
												.filter(device -> device.serialNumber.equals(normalizedSerialNumber))
												.collect(Collectors.toList());

			if (deviceToDelete.size() > 0) {
				deviceService.deleteDevice(deviceToDelete.get(0));
			}

			// get an updated copy of the user now that we've changed the DB and
			// Set the deviceList on the session again now that one has been deleted.
			session = updateSessionDeviceList(session, mv);

			mv.setStatus(HttpStatus.OK);

			return mv;
		}
		catch (Exception e){
			logger.throwing(getClass().getName(), "deleteDevice", e);
			mv.setStatus(HttpStatus.NOT_FOUND);

			return mv;
		}
	}

	// Common Method to add / remove devices and serialNumbers for scheduler view.
	private HttpSession updateSessionDeviceList(HttpSession session, ModelAndView mv) {
		String email = (String)session.getAttribute("email");
		Account currentUser = accountService.getAccountByEmail(email);
		List<Device> deviceList = currentUser.getDevicesList();

		session.removeAttribute("deviceList");
		session.setAttribute("deviceList", deviceList);
		mv.addObject("deviceList", deviceList);

		ArrayList<String> serialNumbers = new ArrayList<String>();
		deviceList.forEach((device -> serialNumbers.add(device.serialNumber)));

		session.removeAttribute("serialNumbers");
		session.setAttribute("serialNumbers", serialNumbers);
		mv.addObject("serialNumbers", serialNumbers);

		session.removeAttribute("schedule");
		session.setAttribute("schedule", currentUser);

		return session;
	}
}
