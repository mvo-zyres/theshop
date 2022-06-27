package com.onlineshop.theshop.controller;

import com.onlineshop.theshop.service.PrivilegeService;
import com.onlineshop.theshop.service.RoleService;
import com.onlineshop.theshop.shop.model.user.Privilege;
import com.onlineshop.theshop.shop.model.user.PrivilegeBoolean;
import com.onlineshop.theshop.shop.model.user.Role;
import com.onlineshop.theshop.CONSTANT;
import com.onlineshop.theshop.shop.form.role.AddRoleForm;
import com.onlineshop.theshop.shop.form.role.DeleteRoleForm;
import com.onlineshop.theshop.shop.form.role.EditRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    PrivilegeService privilegeService;

    @GetMapping("/authorities/role/all")
    public String allRoles(Model model){
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("allroles", roles);
        return "/authorities/allroles";
    }

    @GetMapping("/authorities/role")
    public String role(@RequestParam(name = "id", required = false) UUID id, Model model){
        if (id != null) {
            if (roleService.roleExists(id)) {
                Role role = roleService.getRoleById(id);
                roleService.loadPrivileges(role);
                model.addAttribute("role", role);
                List<PrivilegeBoolean> privilegeBooleans = new ArrayList<>();
                List<UUID> privilegeIds = role.getPrivileges()
                        .stream()
                        .map(Privilege::getId)
                        .collect(Collectors.toList());
                List<UUID> allPrivilegeIds = privilegeService.getAllPrivileges()
                        .stream()
                        .map(Privilege::getId)
                        .collect(Collectors.toList());
                allPrivilegeIds
                        .stream()
                        .map(privilegeId -> new PrivilegeBoolean(privilegeService.getPrivilegeById(privilegeId), privilegeIds.contains(privilegeId)))
                        .forEach(privilegeBooleans::add);
                model.addAttribute("privilegeBooleans", privilegeBooleans);
                return "/authorities/role";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }


    }
    @GetMapping("/authorities/role/add")
    public String addRole(Model model){
        model.addAttribute("privileges", privilegeService.getAllPrivileges());
        return "/authorities/addrole";
    }
    @PostMapping("/authorities/role/add.post")
    public String addRolePost(AddRoleForm addRoleForm){
        if (addRoleForm.getName() != null && addRoleForm.getPIds() != null) {
            List<Privilege> privileges = new ArrayList<>();

            for (UUID uuid: addRoleForm.getPIds()) {
                privileges.add(privilegeService.getPrivilegeById(uuid));
            }
            UUID id = UUID.randomUUID();
            Role role = new Role(id, addRoleForm.getName());
            role.setPrivileges(privileges);
            roleService.addRole(role);
            return CONSTANT.MANAGEMENT_SUCCESS;
        }
        else {
            return CONSTANT.REDIRECT_400;
        }
    }
    @PostMapping("/authorities/role/edit.post")
    public String editRolePost(EditRoleForm editRoleForm) {
        if (editRoleForm.getRoleId() != null && editRoleForm.getRoleName() != null && editRoleForm.getPIds() != null) {
            if (roleService.roleExists(editRoleForm.getRoleId())) {
                List<Privilege> privileges = new ArrayList<>();
                for(UUID uuid : editRoleForm.getPIds()){
                    privileges.add(privilegeService.getPrivilegeById(uuid));
                }
                Role role = new Role(editRoleForm.getRoleId(), editRoleForm.getRoleName(), privileges);
                roleService.updateRoleWithPrivileges(role);
                return CONSTANT.MANAGEMENT_SUCCESS;
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }
    @GetMapping("/authorities/role/delete")
    public String deleteRole(@RequestParam(name = "id", required = false) UUID id, Model model){
        if (id != null) {
            if (roleService.roleExists(id)) {
                model.addAttribute("id", id);
                return "/authorities/deleterole";
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }
    @PostMapping("/authorities/role/delete.post")
    public String deleterolePost(DeleteRoleForm deleteRoleForm){
        if (deleteRoleForm.getId() != null) {
            if (roleService.roleExists(deleteRoleForm.getId())) {
                roleService.deleteRoleById(deleteRoleForm.getId());
                return CONSTANT.MANAGEMENT_SUCCESS;
            }
            else {
                return CONSTANT.REDIRECT_404;
            }
        }
        else {
            return CONSTANT.REDIRECT_400;
        }

    }

    @GetMapping("/authorities/privilege/all")
    public String allPrivileges(Model model){
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        model.addAttribute("allprivileges", privileges);
        return "/authorities/allprivileges";
    }
}
