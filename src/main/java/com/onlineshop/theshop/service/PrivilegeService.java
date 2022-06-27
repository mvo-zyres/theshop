package com.onlineshop.theshop.service;

import com.onlineshop.theshop.shop.model.user.Privilege;
import com.onlineshop.theshop.shop.db.dao.PrivilegeDAO;
import com.onlineshop.theshop.shop.db.dao.RoleDAO;
import com.onlineshop.theshop.shop.db.dao.RolePrivilegeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PrivilegeService {
    @Autowired
    PrivilegeDAO privilegeDAO;

    @Autowired
    RolePrivilegeDAO rolePrivilegeDAO;

    @Autowired
    RoleDAO roleDAO;

    public List<Privilege> getAllPrivileges() {
        return privilegeDAO.read();
    }

    public Privilege getPrivilegeById(UUID privilegeId) {
        return privilegeDAO.readById(privilegeId);
    }


    public boolean privilegeExists(UUID privilegeId) {
        List<Privilege> privilegeList = privilegeDAO.read().stream().filter(privilege -> privilege.getId().toString().equals(privilegeId.toString())).collect(Collectors.toList());
        return privilegeList.size() == 1;
    }
}
