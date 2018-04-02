package org.softuni.wms.areas.users.services;

import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface RoleService {

    List<RoleServiceDto> findAll();

    void seedRoles();

    List<RoleServiceDto> findAllRolesFilter(String authority);

    RoleServiceDto findById(Long id);

    RoleServiceDto findByAuthority(String authority);
}
