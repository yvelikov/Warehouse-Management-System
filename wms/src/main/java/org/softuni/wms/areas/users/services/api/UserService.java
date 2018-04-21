package org.softuni.wms.areas.users.services.api;

import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public interface UserService extends UserDetailsService{

    boolean addUser(UserDto userDto);

    List<UserViewDto> findAllSortedByUsername();

    UserEditDto findById(String id);

    boolean edit(@Valid UserEditDto userEditDto);

    Long getUsersCount();

    boolean registerFirstUser(@Valid RegisterUserDto registerUserDto);

    boolean disable(String id);

    boolean enable(String id);

    UserViewDto findByUsername(String username);

    UserViewDto findUserByEmail(String email);
}
