package com.xckj.ea.repository;

import com.xckj.ea.dao.User;
import com.xckj.ea.dao.yingjiEvent;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.EAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<yingjiEvent,String> {
    @Query(value = "SELECT  * from company_xc.yingji_event where pro_def_id= :ProDefId",nativeQuery = true,name = "yingjiEvent.findEventByProDefId")
    List<yingjiEvent> findEventByProDefId(@Param("ProDefId") String ProDefId);
}
