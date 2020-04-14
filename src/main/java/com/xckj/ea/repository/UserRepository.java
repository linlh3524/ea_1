package com.xckj.ea.repository;

import com.xckj.ea.dao.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
//    @Query(value = "SELECT a.ID_,a.FIRST_,a.LAST_,a.PWD_,b.GROUP_ID_ FROM company_xc.act_id_membership b,company_xc.act_id_user a where  a.ID_=b.USER_ID_ and a.ID_= :id",nativeQuery = true,name = "User.findById")

    @Query(value = "SELECT a.*,b.GROUP_ID_ GROUP_ID_ FROM company_xc.act_id_membership b,company_xc.act_id_user a where  a.ID_=b.USER_ID_ and a.ID_= :id",nativeQuery = true,name = "User.findById")


    User findUserById(@Param("id") String id);

    @Query(value = "SELECT* from company_xc.act_id_user   ",nativeQuery = true,name = "User.findAllUsers")
    List<User> findAllUsers();

    @Modifying
    @Transactional
    @Query(value = "update  company_xc.act_id_user  set PWD_=:pwd where ID_=:id  ",nativeQuery = true)
    Integer UpdatePwd(@Param("id") String id,@Param("pwd") String pwd);




}
