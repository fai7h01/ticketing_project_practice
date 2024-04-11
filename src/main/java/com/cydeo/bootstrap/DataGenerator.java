package com.cydeo.bootstrap;

import com.cydeo.dto.RoleDTO;

import java.util.Arrays;
import java.util.List;

public class DataGenerator {

    public static List<RoleDTO> listOfRoles(){
        return Arrays.asList(
                new RoleDTO(1L,"employee"),
                new RoleDTO(2L,"manager"),
                new RoleDTO(3L,"admin")
        );
    }

}
