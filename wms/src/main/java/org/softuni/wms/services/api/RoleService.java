package org.softuni.wms.services.api;

import org.softuni.wms.models.service.RoleDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface RoleService {

    List<RoleDto> findAll();

    void seedRoles();
}
