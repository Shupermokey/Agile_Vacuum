package com.SE459.Portal.Service;

import java.util.logging.Logger;

import com.SE459.Portal.Dao.NavigatorDao;
import com.SE459.Portal.Entity.Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NavigatorService {
	
	@Autowired
    NavigatorDao DAO;

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public void save(Navigator direction) {
		DAO.save(direction);
	}
	
	/*
	 * public List<String> getAllDirections(){
	 * 
	 * List<Navigator> allDirections = DAO.findAll(); List<String> directions = new
	 * ArrayList<>();
	 * 
	 * for(int i = 0; i<allDirections.size(); i++) {
	 * directions.add(allDirections.get(i)); }
	 * 
	 * 
	 * }
	 */

}
