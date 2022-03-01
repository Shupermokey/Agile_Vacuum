package com.SE459.Portal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SE459.Portal.Entity.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
	
	public Account findAccountByEmail(String Email);

}
