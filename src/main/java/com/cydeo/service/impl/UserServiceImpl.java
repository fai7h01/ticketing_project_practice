package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDTO> findAllUsers() {
        return null;
    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }

    @Override
    public void save(UserDTO user) {

    }

    @Override
    public void deleteByUserName(String username) {

    }
}
