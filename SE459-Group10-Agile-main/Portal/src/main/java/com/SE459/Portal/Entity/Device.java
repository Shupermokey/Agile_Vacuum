package com.SE459.Portal.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int deviceId;

	@OneToOne
	public Account account;

	public String serialNumber;

	@OneToOne
	private Cleaner cleaner;
	
	@OneToOne
	private DirtDetector dirtDetector;
	
	@OneToOne
	private Navigator navigator;
	
	@OneToOne
	public Scheduler scheduler;
	
	@OneToOne
	private PowerManager powerManager;

	public Device() {
		
	}
	
	public Device(Cleaner cleaner, DirtDetector dirtDetector, Navigator navigator, Scheduler scheduler,PowerManager powerManager) {
		this.cleaner = cleaner;
		this.dirtDetector = dirtDetector;
		this.navigator = navigator;
		this.scheduler = scheduler;
		this.powerManager = powerManager;
	}

	public Device(Account account, String serialNumber, Cleaner cleaner, DirtDetector dirtDetector, Navigator navigator, Scheduler scheduler,PowerManager powerManager) {
		this.account = account;
		this.serialNumber = serialNumber;
		this.cleaner = cleaner;
		this.dirtDetector = dirtDetector;
		this.navigator = navigator;
		this.scheduler = scheduler;
		this.powerManager = powerManager;
	}

	public Account getAccount() { return account; }
	
	public void setAccount(Account account) {
		this.account = account;
	}

	public Cleaner getCleaner() {
		return cleaner;
	}
	public void setCleaner(Cleaner cleaner) {
		this.cleaner = cleaner;
	}
	public DirtDetector getDirtDetector() {
		return dirtDetector;
	}
	public void setDirtDetector(DirtDetector dirtDetector) {
		this.dirtDetector = dirtDetector;
	}
	public Navigator getNavigator() {
		return navigator;
	}
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public PowerManager getPowerManager() {
		return powerManager;
	}

	public void setPowerManager(PowerManager powerManager) {
		this.powerManager = powerManager;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "Device [deviceId=" + deviceId+ "]";
	}


	
	

}
