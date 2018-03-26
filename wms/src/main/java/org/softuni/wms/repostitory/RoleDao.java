package org.softuni.wms.repostitory;

import org.softuni.wms.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long>{
    Role findFirstByAuthority(String authority);
}
