package org.softuni.wms.areas.users.repositories;

import org.softuni.wms.areas.users.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<Role, String>{
    Role findFirstByAuthority(String authority);

    @Query(value = "SELECT * FROM roles as r\n" +
            "WHERE r.authority NOT LIKE :authority", nativeQuery = true)
    List<Role> findAllRolesFilter(@Param(value = "authority") String authority);
}
