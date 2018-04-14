package org.softuni.wms.areas.users.services.impl;

import org.softuni.wms.areas.users.entities.Role;
import org.softuni.wms.areas.users.entities.enums.UserRole;
import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.view.AllRolesViewDto;
import org.softuni.wms.areas.users.repositories.RoleDao;
import org.softuni.wms.areas.users.services.api.RoleService;
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
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<RoleServiceDto> findAll() {

        List<Role> roles = this.roleDao.findAll();
        return DTOConvertUtil.convert(roles, RoleServiceDto.class);
    }

    @Override
    public void seedRoles() {

        Set<Role> defaultRoles = new LinkedHashSet<>(Arrays.asList(
                new Role(UserRole.SUPER_ADMIN.name()),
                new Role(UserRole.ADMIN.name()),
                new Role(UserRole.EMPLOYEE.name()),
                new Role(UserRole.WAREHOUSEMAN.name())));

        for (Role defaultRole : defaultRoles) {
            this.roleDao.saveAndFlush(defaultRole);
        }
    }

    @Override
    public List<RoleServiceDto> findAllRolesFilter(String authority) {
        List<Role> allRolesFilter = this.roleDao.findAllRolesFilter(authority);
        return DTOConvertUtil.convert(allRolesFilter, RoleServiceDto.class);
    }

    @Override
    public RoleServiceDto findById(String id) {
        Role role = this.roleDao.getOne(id);
        return DTOConvertUtil.convert(role, RoleServiceDto.class);
    }

    @Override
    public RoleServiceDto findByAuthority(String authority) {
        Role role = this.roleDao.findFirstByAuthority(authority);
        return DTOConvertUtil.convert(role, RoleServiceDto.class);
    }

    @Override
    public AllRolesViewDto getAllRolesView() {
        List<Role> roles = this.roleDao.findAll();
        AllRolesViewDto allRoles = new AllRolesViewDto();
        for (Role role : roles) {
            allRoles.add(role.getAuthority());
        }
        return allRoles;
    }

    @Override
    public Long getRolesCount() {
        return this.roleDao.count();
    }
}
