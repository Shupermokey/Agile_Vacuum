package com.SE459.Portal.Dao;

import com.SE459.Portal.Entity.Navigator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigatorDao extends JpaRepository<Navigator, Long>{

}
