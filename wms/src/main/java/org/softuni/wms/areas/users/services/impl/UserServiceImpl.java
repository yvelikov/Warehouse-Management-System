package org.softuni.wms.areas.users.services.impl;

import org.softuni.wms.areas.users.entities.Role;
import org.softuni.wms.areas.users.entities.User;
import org.softuni.wms.areas.users.entities.enums.UserRole;
import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.service.UserServiceDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.softuni.wms.areas.users.repositories.UserDao;
import org.softuni.wms.areas.users.services.api.RoleService;
import org.softuni.wms.areas.users.services.api.UserService;
import org.softuni.wms.utils.DTOConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserServiceDto addUser(UserDto userDto) {
        try {
            User user = DTOConvertUtil.convert(userDto, User.class);
            user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Set<Role> roles = new HashSet<>();

            for (String roleName : userDto.getAuthorities()) {
                RoleServiceDto roleDto = this.roleService.findByAuthority(roleName);
                Role role = DTOConvertUtil.convert(roleDto, Role.class);
                roles.add(role);
            }

            user.setAuthorities(roles);

            User savedUser = this.userDao.saveAndFlush(user);
            return DTOConvertUtil.convert(savedUser, UserServiceDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public List<UserViewDto> findAllSortedByUsername() {
        List<User> users = this.userDao.findAllByOrderByUsername();
        if (users == null) {
            return new ArrayList<>();
        }

        List<UserViewDto> userViewDtos = new ArrayList<>();

        for (User user : users) {
            UserViewDto userViewDto = DTOConvertUtil.convert(user, UserViewDto.class);
            Set<String> roles = new HashSet<>();
            for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
                roles.add(grantedAuthority.getAuthority());
            }
            userViewDto.setAuthorities(roles);
            userViewDtos.add(userViewDto);
        }

        return userViewDtos;
    }

    @Override
    public UserEditDto findById(String id) {

        User user = this.userDao.getOne(id);

        Set<String> authorities = new HashSet<>();
        UserEditDto userEditDto = DTOConvertUtil.convert(user, UserEditDto.class);
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }

        userEditDto.setAuthorities(authorities);

        return userEditDto;
    }

    @Override
    public UserServiceDto edit(@Valid UserEditDto userEditDto) {
        try {
            User user = this.userDao.getOne(userEditDto.getId());
            Set<Role> userRoles = new HashSet<>();

            for (String roleName : userEditDto.getAuthorities()) {
                RoleServiceDto roleDto = this.roleService.findByAuthority(roleName);
                Role role = DTOConvertUtil.convert(roleDto, Role.class);
                userRoles.add(role);
            }

            user.setUsername(userEditDto.getUsername());
            user.setFirstName(userEditDto.getFirstName());
            user.setLastName(userEditDto.getLastName());
            user.setEmail(userEditDto.getEmail());
            user.setAuthorities(userRoles);

            User savedUser = this.userDao.saveAndFlush(user);
            return DTOConvertUtil.convert(savedUser, UserServiceDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public Long getUsersCount() {
        return this.userDao.count();
    }

    @Override
    public UserServiceDto registerFirstUser(@Valid RegisterUserDto registerUserDto) {
        try {
            User user = DTOConvertUtil.convert(registerUserDto, User.class);
            user.setPassword(this.passwordEncoder.encode(registerUserDto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Set<Role> roles = new HashSet<>();

            RoleServiceDto roleDto = this.roleService.findByAuthority(UserRole.SUPER_ADMIN.name());
            Role role = DTOConvertUtil.convert(roleDto, Role.class);
            roles.add(role);
            user.setAuthorities(roles);

            User savedUser = this.userDao.saveAndFlush(user);
            return DTOConvertUtil.convert(savedUser, UserServiceDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public UserServiceDto disable(String id) {
        try {
            User user = this.userDao.getOne(id);
            user.getAuthorities().forEach(r -> {
                if (r.getAuthority().equals(UserRole.SUPER_ADMIN.name())) {
                    throw new IllegalStateException();
                }
            });

            user.setEnabled(false);
            User savedUser = this.userDao.saveAndFlush(user);
            return DTOConvertUtil.convert(savedUser,UserServiceDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public UserServiceDto enable(String id) {
        try {
            User user = this.userDao.getOne(id);
            user.getAuthorities().forEach(r -> {
                if (r.getAuthority().equals(UserRole.SUPER_ADMIN.name())) {
                    throw new IllegalStateException();
                }
            });

            user.setEnabled(true);
            User savedUser = this.userDao.saveAndFlush(user);
            return DTOConvertUtil.convert(savedUser,UserServiceDto.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    @Override
    public UserViewDto findByUsername(String username) {
        User user = this.userDao.findByUsername(username);
        if(user != null){
            return DTOConvertUtil.convert(user, UserViewDto.class);
        }
        return null;
    }

    @Override
    public UserViewDto findUserByEmail(String email) {
        User user = this.userDao.findByEmail(email);
        if(user != null){
            return DTOConvertUtil.convert(user, UserViewDto.class);
        }
        return null;
    }
}
