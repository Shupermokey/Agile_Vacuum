package com.SE459.Portal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SE459.Portal.Entity.Device;

public interface DeviceDao extends JpaRepository<Device, Integer>{
	
	//public Device findDeviceByEmail(String email);

    public Device findDeviceBySerialNumber(String serialNumber);

}
