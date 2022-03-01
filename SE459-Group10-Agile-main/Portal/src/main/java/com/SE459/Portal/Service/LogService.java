package com.SE459.Portal.Service;

import java.util.ArrayList;

import com.SE459.Portal.Dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SE459.Portal.Dao.AccountDao;
import com.SE459.Portal.Dao.LogDao;
import com.SE459.Portal.Entity.Account;
import com.SE459.Portal.Entity.Log;

@Service
public class LogService {
	
	@Autowired
	LogDao logDao;
	
	@Autowired
	AccountDao accountDao;
	

	public ArrayList<Log> getLog(String email){
		Account account = accountDao.findAccountByEmail(email);
		ArrayList<Log> log = logDao.findAllLogByAccountId(account.getAccountId());
		
		return log;
	}
}
