package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
        return mapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO user) {
        userRepository.save(mapper.convertToEntity(user));
    }

    @Override
    public void deleteByUserName(String username) {

    }
}
