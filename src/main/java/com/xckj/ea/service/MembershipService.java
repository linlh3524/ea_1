package com.xckj.ea.service;

import com.xckj.ea.dao.Membership;
import com.xckj.ea.repository.MembershipRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


public class MembershipService {
    @Autowired
    MembershipRespository membershipRespository;
    Membership findMembershipById(String id){
      return   membershipRespository.findMembershipById(id);


    }
}
