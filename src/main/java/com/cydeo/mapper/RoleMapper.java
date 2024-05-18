package com.cydeo.mapper;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private final ModelMapper mapper;

    public RoleMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public RoleDTO convertToDTO(Role role){
        return mapper.map(role, RoleDTO.class);
    }

    public Role convertToEntity(RoleDTO role){
        return mapper.map(role, Role.class);
    }
}
