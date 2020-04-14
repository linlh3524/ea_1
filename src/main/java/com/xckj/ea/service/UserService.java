package com.xckj.ea.service;

import com.xckj.ea.dao.Department;
import com.xckj.ea.dao.User;
import com.xckj.ea.repository.DepartRepository;
import com.xckj.ea.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartRepository departRepository;

    public User findById(String id)
    {
       User user=userRepository.findUserById(id);
        return user;
    }

    public List<User> findAllUsers(String id)
    {
        List<User> user=userRepository.findAllUsers();
       // userRepository.
        return user;
    }
    public Department findDepartmentById(String id)
    {
        Department user=departRepository.findDepartmentById(id);
        // userRepository.
        return user;
    }

}
