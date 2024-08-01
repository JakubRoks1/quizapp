package com.example.quizapp.service;

import com.example.quizapp.entity.RoleEntity;
import com.example.quizapp.json.RoleJson;
import com.example.quizapp.mappers.RoleMapper;
import com.example.quizapp.model.Role;
import com.example.quizapp.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleJson addRole(RoleJson roleJson) {
        Role role = roleMapper.mapToRole(roleJson);
        RoleEntity roleEntity = roleMapper.mapToRoleEntity(role);
        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        Role savedRole = roleMapper.mapToRole(savedRoleEntity);
        return roleMapper.mapToRoleJson(savedRole);
    }

    public List<RoleJson> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return roleEntities.stream()
                .map(roleMapper::mapToRole)
                .map(roleMapper::mapToRoleJson)
                .collect(Collectors.toList());
    }

    public Optional<RoleJson> getRole(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::mapToRole)
                .map(roleMapper::mapToRoleJson);
    }

    public RoleJson updateRole(Long id, RoleJson roleJson) {
        return roleRepository.findById(id)
                .map(existingRole -> {
                    existingRole.setRoleName(roleJson.getRoleName());
                    RoleEntity updatedRoleEntity = roleRepository.save(existingRole);
                    Role updatedRole = roleMapper.mapToRole(updatedRoleEntity);
                    return roleMapper.mapToRoleJson(updatedRole);
                })
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public void deleteRole(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Role not found");
        }
    }
}

