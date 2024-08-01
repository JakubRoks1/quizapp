package com.example.quizapp.mappers;

import com.example.quizapp.entity.RoleEntity;
import com.example.quizapp.json.RoleJson;
import com.example.quizapp.model.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleEntity mapToRoleEntity(Role role);

    Role mapToRole(RoleEntity roleEntity);

    RoleJson mapToRoleJson(Role role);

    Role mapToRole(RoleJson roleJson);
}
