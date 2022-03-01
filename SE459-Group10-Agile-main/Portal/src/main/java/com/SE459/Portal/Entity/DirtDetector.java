package com.SE459.Portal.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DirtDetector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dirtId;
	
	private boolean dirtSensor;
}
