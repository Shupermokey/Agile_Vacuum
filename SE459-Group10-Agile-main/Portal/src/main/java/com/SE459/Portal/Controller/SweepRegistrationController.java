package com.SE459.Portal.Controller;

import com.SE459.Portal.Dao.CleanerDao;
import com.SE459.Portal.Dao.DirtDetectorDao;
import com.SE459.Portal.Dao.NavigatorDao;
import com.SE459.Portal.Dao.PowerManagerDao;
import com.SE459.Portal.Dao.ScheduleDao;
import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Device;
import com.SE459.Portal.Service.AccountService;
import com.SE459.Portal.Service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller()
public class SweepRegistrationController {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    HttpSession session;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AccountService accountService;


    @Autowired
    private CleanerDao cleanDao;

    @Autowired
    private DirtDetectorDao dirtDetectorDao;

    @Autowired
    private NavigatorDao navigatorDao;

    @Autowired
    private ScheduleDao schedulerDao;

    @Autowired
    private PowerManagerDao powerManagerDao;

    @RequestMapping("/SweepRegistration")
    public ModelAndView index(HttpServletRequest req) {

        // Redirect To Login if we don't have a logged in User.
        if (req.getSession().getAttribute("email") == null){
            return new ModelAndView("redirect:/Login");
        }

        return new ModelAndView("SweepRegistration");
    }

    @PostMapping("/addDevice")
    public ModelAndView addDevice(HttpServletRequest req, Model model, String serialNumber) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/Scheduler");

        HttpSession session = req.getSession();

        String email = (String)session.getAttribute("email");
        System.out.println(email);
        //get the current logged in account through the session attribute
        Account account = accountService.getAccountByEmail(email);
        //create a new device
        Device newDevice = deviceService.createNewDevice();
        newDevice.setAccount(account);
        newDevice.setSerialNumber(serialNumber);

        deviceService.saveDevice(newDevice);

        //add the device to the current account's device list
        account.getDevicesList().add(newDevice);
        accountService.saveAccount(account);

        //set the list to a model and send it to the scheduler page
        session = updateSessionDeviceList(session, mv);

        return mv;
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

        return session;
    }
}