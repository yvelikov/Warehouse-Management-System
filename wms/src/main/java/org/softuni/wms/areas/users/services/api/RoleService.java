package org.softuni.wms.areas.users.services.api;

import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.view.AllRolesViewDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface RoleService {

    List<RoleServiceDto> findAll();

    boolean seedRoles();

    List<RoleServiceDto> findAllRolesFilter(String authority);

    RoleServiceDto findById(String id);

    RoleServiceDto findByAuthority(String authority);

    AllRolesViewDto getAllRolesView();

    Long getRolesCount();
}
