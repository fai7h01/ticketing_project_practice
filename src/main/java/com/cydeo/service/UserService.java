package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO user);
    void deleteByUserName(String username);
    void update(UserDTO user);
    void delete(String username);
    List<UserDTO> listAllByRole(String role);
}
