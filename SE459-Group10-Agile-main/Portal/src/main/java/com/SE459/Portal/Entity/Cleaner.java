package com.SE459.Portal.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cleaner {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cleanId;
	
	//Boolean for if a surface is clean or not. A surface might not be clean after a single/multiple use. 
	private boolean isClean;
	
	//cleaning mode depending on the surface being cleaned
	private String cleaningMode;
	
	//remove one unit of dirt
	private boolean clean;
	
	//initial dirt capacity of the cleaner is 50 units. Once it hits 0, then it must return to it's station and turn on it's "EMPTY ME" indicator
	private int dirtCapacity = 50;
	
	private boolean EmptyMe;
	
}
