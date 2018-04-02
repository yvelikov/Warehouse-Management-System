package org.softuni.wms.areas.users.repositories;

import org.softuni.wms.areas.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String>{

    User findByUsername(String username);

    @Query(value = "SELECT * FROM users as u\n" +
            "ORDER BY u.username ASC", nativeQuery = true)
    List<User> findAllSortedByUsername();
}
