package com.xckj.ea.repository;

import com.xckj.ea.dao.Department;
import com.xckj.ea.dao.User;
import com.xckj.ea.dao.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<UserMessage,String> {


    @Query(value = "SELECT * from company_xc.user_message where  TASK_ID_= :id",nativeQuery = true,name = "UserMessage.findUserMessageById")
    UserMessage findUserMessageById(@Param("id") String id);
}
