package com.SE459.Portal.Service;

import com.SE459.Portal.Dao.ScheduleDao;
import com.SE459.Portal.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;

import com.SE459.Portal.Entity.Scheduler;
import com.SE459.Portal.Configuration.OneTimeScheduler;
import com.SE459.Portal.Configuration.RepeatedScheduler;
import com.SE459.Portal.Dao.AccountDao;
import com.SE459.Portal.Dao.DeviceDao;
import com.SE459.Portal.Dao.ScheduleDao;
import com.SE459.Portal.Entity.Scheduler;

import java.util.logging.Logger;

@Service
public class ScheduleService {

	@Autowired
    ScheduleDao scheduleDao;

	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	AccountDao accountDao;

	@Autowired
	DeviceService deviceService;

	@Autowired
	RepeatedScheduler repeatedScheduler;

	@Autowired
	OneTimeScheduler oneTimeScheduler;

	final Logger logger = Logger.getLogger(this.getClass().getName());

	public void generateSchedule(String email, String m, String t, String w, String th, String f, String s, String sun, String appt, String freq) {

		Scheduler schedule = new Scheduler();
		if(m!=null) {
			schedule.setM(true);
		}
		if(t!=null) {
			schedule.setT(true);
		}
		if(w!=null) {
			schedule.setW(true);
		}
		if(th!=null) {
			schedule.setTh(true);
		}
		if(f!=null) {
			schedule.setF(true);
		}
		if(s!=null) {
			schedule.setS(true);
		}
		if(sun!=null) {
			schedule.setSun(true);
		}
		if(appt != null) {
			schedule.setAppt(appt);
		}
		if(freq != null) {
			schedule.setFreq(freq);
		}
		scheduleDao.save(schedule);
		
		/*
		 * if(accountDao.findAccountByEmail(account)!=null) { Account currentUser =
		 * accountDao.findAccountByEmail(account); if(currentUser.getDevice()!=null) {
		 * Device device = currentUser.getDevice(); device.setScheduler(schedule);
		 * deviceDao.save(device);} }
		 */

		Account currentUser = accountDao.findAccountByEmail(email);

		logger.info(schedule.toString());
		logger.info(appt);
		logger.info(((Integer)appt.length()).toString());
		
		if(freq.equals("repeated")) {
			RepeatedScheduler.getInstance().stopAll();
			executeRepeatedScheduled(currentUser.getAccountId(), schedule);
		}
		else {
			OneTimeScheduler.getInstance().stopAll();
			executeOneTimeScheduled(currentUser.getAccountId(), schedule);
		}
		
	}
	
	public Scheduler findSchedule() {
		return scheduleDao.findAll().get(scheduleDao.findAll().size()-1);
	}

	private String generateCronString(Scheduler schedule) {
		String cronString = schedule.toCronString();
		return cronString;
	}

	private void executeRepeatedScheduled(int accountId, Scheduler schedule) {
		String cronScheduleEntry = generateCronString(schedule);
		Runnable scheduledJob = deviceService.RepeatedRunner(accountId, cronScheduleEntry);
		Trigger jobTrigger = new CronTrigger(cronScheduleEntry);
		try{
			RepeatedScheduler.getInstance().start(scheduledJob, jobTrigger);
		}
		catch(Exception e) {
			logger.warning(e.toString());
		}

	}

	private void executeOneTimeScheduled(int accountId, Scheduler schedule) {
		String cronScheduleEntry = generateCronString(schedule);
		Runnable scheduledJob = deviceService.OneTimeRunner(accountId, cronScheduleEntry);
		Trigger jobTrigger = new CronTrigger(cronScheduleEntry);
		try{
			OneTimeScheduler.getInstance().start(scheduledJob, jobTrigger);
		}
		catch(Exception e) {
			logger.warning(e.toString());
		}
	}
}
