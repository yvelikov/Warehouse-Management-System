package org.softuni.wms.areas.users.services.api;

import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.service.UserServiceDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public interface UserService extends UserDetailsService{

    UserServiceDto addUser(UserDto userDto);

    List<UserViewDto> findAllSortedByUsername();

    UserEditDto findById(String id);

    UserServiceDto edit(@Valid UserEditDto userEditDto);

    Long getUsersCount();

    UserServiceDto registerFirstUser(@Valid RegisterUserDto registerUserDto);

    UserServiceDto disable(String id);

    UserServiceDto enable(String id);

    UserViewDto findByUsername(String username);

    UserViewDto findUserByEmail(String email);
}
