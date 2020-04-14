package com.xckj.ea.repository;

import com.xckj.ea.dao.EventType;
import com.xckj.ea.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventTypeRepository extends JpaRepository<EventType,String> {
    @Query(value = "SELECT * FROM company_xc.event_type where ID_= :id",nativeQuery = true,name = "EventType.findById")
   // User findUserById(@Param("id") String id);
    int findById(@Param("id") int id);
}
