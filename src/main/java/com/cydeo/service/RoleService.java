package com.cydeo.service;

import com.cydeo.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAllRoles();

    RoleDTO findById(Long id);
}
