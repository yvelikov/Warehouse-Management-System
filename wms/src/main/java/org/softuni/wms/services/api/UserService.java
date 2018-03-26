package org.softuni.wms.services.api;

import org.softuni.wms.models.binding.RegisterUserDto;
import org.softuni.wms.models.binding.UserDto;
import org.softuni.wms.models.binding.UserEditDto;
import org.softuni.wms.models.view.UserViewDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public interface UserService extends UserDetailsService{

    void register(UserDto userDto);

    List<UserViewDto> findAllSortedByUsername();

    UserEditDto findById(String id);

    void edit(@Valid UserEditDto userEditDto);

    Long getUsersCount();

    void registerFirstUser(@Valid RegisterUserDto registerUserDto);
}
