package com.SE459.Portal.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Navigator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long navId;
	
	
	//The four sensor of the clean sweeper. Booleans to determine if the sensor encounters an obsticle
	private boolean forwardSensor;
	private boolean rightSensor;
	private boolean leftSensor;
	private boolean backwardSensor;
	//The current direction of the clean sweeper. Maybe have this set to an Enum: RIGHT,LEFT,FWD,BKWD
	private String direction;
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
	
	

}
