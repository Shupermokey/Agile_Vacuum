package com.SE459.Portal.Dao;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.SE459.Portal.Entity.Log;

public interface LogDao extends JpaRepository<Log, Integer> {

	ArrayList<Log> findAllLogByAccountId(int accountId);


}
