package com.SE459.Portal.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SE459.Portal.Entity.Scheduler;
import java.util.List;

public interface ScheduleDao extends JpaRepository<Scheduler, Integer> {
    public List<Scheduler> findAll();
}
