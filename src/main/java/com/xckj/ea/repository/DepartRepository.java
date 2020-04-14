package com.xckj.ea.repository;

import com.xckj.ea.dao.Department;
import com.xckj.ea.dao.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartRepository extends JpaRepository<Department,String> {


    @Query(value = "SELECT * from company_xc.department where  ID_= :id",nativeQuery = true,name = "Department.findDepartmentById")
    Department findDepartmentById(@Param("id") String id);



}
