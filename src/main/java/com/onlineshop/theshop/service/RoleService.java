package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.user.Privilege;
import com.onlineshop.theshop.shop.model.user.Role;
import com.onlineshop.theshop.shop.db.dao.PrivilegeDAO;
import com.onlineshop.theshop.shop.db.dao.RoleDAO;
import com.onlineshop.theshop.shop.db.dao.RolePrivilegeDAO;
import com.onlineshop.theshop.shop.db.dao.UserRoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Autowired
    RolePrivilegeDAO rolePrivilegeDAO;

    @Autowired
    PrivilegeDAO privilegeDAO;

    @Autowired
    PrivilegeService privilegeService;

    public void loadPrivileges(Role role) {
        List<Privilege> privileges = rolePrivilegeDAO.readByRoleId(role.getId())
                .stream()
                .map(uuid -> privilegeDAO.readById(uuid))
                .collect(Collectors.toList());
        role.setPrivileges(privileges);
    }

    public List<Privilege> getPrivilegesByRoleId(UUID roleId) {
        return rolePrivilegeDAO.readByRoleId(roleId)
                .stream()
                .map(uuid -> privilegeDAO.readById(uuid))
                .collect(Collectors.toList());
    }

    public Role getByUserId(UUID userId) {
        UUID roleId = userRoleDAO.readRoleIdByUserId(userId);
        return roleDAO.readById(roleId);
    }

    public List<Role> getAllRoles() {
        return roleDAO.read();
    }
    public Role getRoleById(UUID roleId) {
        return roleDAO.readById(roleId);
    }

    public void addRole(Role role) {
        roleDAO.create(role);
        role.getPrivileges()
                .forEach(privilege -> rolePrivilegeDAO.create(role.getId(), privilege.getId()));

    }
    public void updateRoleWithPrivileges(Role role) {
        roleDAO.update(role);
        rolePrivilegeDAO.readByRoleId(role.getId())
                .forEach(uuid -> rolePrivilegeDAO.delete(role.getId(), uuid));
        role.getPrivileges()
                .forEach(privilege -> rolePrivilegeDAO.create(role.getId(), privilege.getId()));
    }

    public void deleteRoleById(UUID roleId) {
        Role role = roleDAO.readById(roleId);
        loadPrivileges(role);
        role.getPrivileges()
                .forEach(privilege -> rolePrivilegeDAO.delete(roleId, privilege.getId()));
        roleDAO.delete(roleId);
    }

    public void removeRoleFromUserByUserId(UUID userId) {
        UUID roleId = userRoleDAO.readRoleIdByUserId(userId);
        userRoleDAO.delete(userId, roleId);
    }

    public Role getRoleByName(String name) {
        return roleDAO.read().stream().filter(rolee -> rolee.getName().equals(name)).collect(Collectors.toList()).get(0);
    }
    public boolean roleExists(UUID roleId) {
        List<Role> roles = roleDAO.read().stream().filter(role -> role.getId().toString().equals(roleId.toString())).collect(Collectors.toList());
        return roles.size() == 1;
    }
}