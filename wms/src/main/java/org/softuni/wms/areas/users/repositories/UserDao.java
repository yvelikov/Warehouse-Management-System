package org.softuni.wms.areas.users.repositories;

import org.softuni.wms.areas.users.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, String>{

    User findByUsername(String username);

    List<User> findAllByOrderByUsername();
}
