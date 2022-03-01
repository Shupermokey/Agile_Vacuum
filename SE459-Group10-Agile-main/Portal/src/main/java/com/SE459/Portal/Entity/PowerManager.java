package com.SE459.Portal.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PowerManager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long powerId;
	
	//initial battery life of the vaccum
	private int batteryLife = 250;
	
	//store the type of suface the vacuum is currently on, and shift power management accordingly
	//Bare floor: 1
	//Low-pile carpet 2
	//High-pile carpet 3
	//if moving from one surface to another, the power needed is the average between the 2 surfaces (bare floor to low-pile is 1.5 units)
	private String surface;
	
	//is the vacuum on or off
	private boolean powerOn;

	

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public boolean isPowerOn() {
		return powerOn;
	}

	public void setPowerOn(boolean powerOn) {
		this.powerOn = powerOn;
	}

	public int getBatteryLife() {
		return batteryLife;
	}

	public void setBatteryLife(int batteryLife) {
		this.batteryLife = batteryLife;
	}
	
	
	
}
