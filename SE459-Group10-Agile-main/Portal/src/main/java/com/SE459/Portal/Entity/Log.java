package com.SE459.Portal.Entity;

import java.util.ArrayList;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Log {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lid;
	private int accountId;
	

	private String log;


	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	

}
