package com.SE459.Portal.Service;

import com.SE459.Portal.Dao.*;
import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AccountService {
	
	@Autowired
	AccountDao aDao;
	
	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	CleanerDao cleanDao;
	
	@Autowired
	DirtDetectorDao dirtDetectorDao;
	
	@Autowired
	NavigatorDao navigatorDao;
	
	@Autowired
	ScheduleDao schedulerDao;
	
	@Autowired
	PowerManagerDao powerManagerDao;

	@Autowired
	DeviceService deviceService;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public Account getAccountByEmail(String Email) {
		
		return aDao.findAccountByEmail(Email);
	}

	public boolean createAccount(String email, String password) {
		try {
			if (aDao.findAccountByEmail(email) == null) {

				// initializes all objects needed in the case a new device has been added.
				Device device = setupNewDevice();
				List<Device> deviceList = new ArrayList<>();
				deviceList.add(device);
				// sets up the new Account and provides a default password of "password"
				// TODO: pass a real password over to the new account setup.
				Account account = new Account();
				account.setEmail(email);
				account.setPass(password);
				account.setDevicesList(deviceList);
				account.setDevice(device);
				deviceDao.save(device);
				aDao.save(account);
				logger.info("Successfully created an Account.");
				return true;
			}
		}
		catch (Exception e) {
			logger.throwing(this.getClass().getName(),"createAccount", e);
		}

		return false;
	}

	public boolean validateLogin(String email, String pass) {

		try {
			Account account = aDao.findAccountByEmail(email);

			if (account != null) {
				if (account.getPass().equals(pass)) {
					logger.info("AccountService: Account Found and Valid Login credentials were passed.");
					return true;
				}
				logger.warning("AccountService: Account Found; Password was Invalid.");

				return false;
			}
		}
		catch (Exception e) {
			logger.throwing("AccountService", "validateLogin", e);
		}

		return false;
	}

	private Device setupNewDevice() {
		Device newDevice = deviceService.createNewDevice();

		cleanDao.save(newDevice.getCleaner());
		dirtDetectorDao.save(newDevice.getDirtDetector());
		navigatorDao.save(newDevice.getNavigator());
		schedulerDao.save(newDevice.getScheduler());
		powerManagerDao.save(newDevice.getPowerManager());

		return newDevice;
	}
	
	public boolean saveAccount(Account a) {
		try {
			this.aDao.save(a);
			return true;
		}
		catch (Exception e) {
			logger.throwing(this.getClass().getName(), "saveAccount", e);
			return false;
		}
	}
}
