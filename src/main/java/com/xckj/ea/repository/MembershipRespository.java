package com.xckj.ea.repository;

import com.xckj.ea.dao.Membership;
import com.xckj.ea.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MembershipRespository extends JpaRepository<Membership,String> {
    @Query(value = "SELECT  * from act_id_membership where USER_ID_= :id",nativeQuery = true,name = "Membership.findById")


    Membership findMembershipById(@Param("id") String id);
}
