package org.softuni.wms.repostitory;

import org.softuni.wms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String>{
}
