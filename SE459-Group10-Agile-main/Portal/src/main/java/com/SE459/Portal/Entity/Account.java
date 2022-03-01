package com.SE459.Portal.Entity;

import javax.persistence.*;
import java.util.List;


@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	private String email;
	private String pass;
	
	@OneToOne
	private Device device;
	
	@ElementCollection
	@CollectionTable
	@OneToMany(fetch= FetchType.EAGER, mappedBy = "account")
	private List<Device> devicesList;

	public int getAccountId() { return this.accountId; }

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
		}
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	

	public List<Device> getDevicesList() {
		return devicesList;
	}

	public void setDevicesList(List<Device> devicesList) {
		this.devicesList = devicesList;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", email=" + email + ", pass=" + pass + "]";
	}
	
	
	
	

}
