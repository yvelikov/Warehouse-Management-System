package org.softuni.wms.services.impl;

import org.softuni.wms.entities.Role;
import org.softuni.wms.enums.UserRole;
import org.softuni.wms.models.service.RoleDto;
import org.softuni.wms.repostitory.RoleDao;
import org.softuni.wms.services.api.RoleService;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<RoleDto> findAll() {

        List<Role> roles = this.roleDao.findAll();
        return DTOConvertUtil.convert(roles, RoleDto.class);
    }

    @Override
    public void seedRoles() {

        Set<Role> defaultRoles = new LinkedHashSet<>(Arrays.asList(
                new Role(UserRole.ADMIN.name()),
                new Role(UserRole.EMPLOYEE.name()),
                new Role(UserRole.WAREHOUSEMAN.name())));

        for (Role defaultRole : defaultRoles) {
            this.roleDao.saveAndFlush(defaultRole);
        }
    }
}
