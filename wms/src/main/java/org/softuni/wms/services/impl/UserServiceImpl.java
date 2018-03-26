package org.softuni.wms.services.impl;

import org.softuni.wms.entities.Role;
import org.softuni.wms.entities.User;
import org.softuni.wms.models.binding.RegisterUserDto;
import org.softuni.wms.models.binding.UserDto;
import org.softuni.wms.models.binding.UserEditDto;
import org.softuni.wms.models.view.UserViewDto;
import org.softuni.wms.repostitory.RoleDao;
import org.softuni.wms.repostitory.UserDao;
import org.softuni.wms.services.api.UserService;
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
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
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
    public void register(UserDto userDto) {
        User user = DTOConvertUtil.convert(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();

        for (String roleName : userDto.getAuthorities()) {
            Role role = this.roleDao.findFirstByAuthority(roleName);
            roles.add(role);
        }

        user.setAuthorities(roles);

        this.userDao.saveAndFlush(user);
    }


    @Override
    public List<UserViewDto> findAllSortedByUsername() {
        List<User> users = this.userDao.findAllSortedByUsername();
        if (users == null) {
            return new ArrayList<>();
        }
        return DTOConvertUtil.convert(users, UserViewDto.class);
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
    public void edit(@Valid UserEditDto userEditDto) {
        User user = this.userDao.getOne(userEditDto.getId());
        Set<Role> userRoles = new HashSet<>();

        for (String roleName : userEditDto.getAuthorities()) {
            Role role = this.roleDao.findFirstByAuthority(roleName);
            userRoles.add(role);
        }

        user.setUsername(userEditDto.getUsername());
        user.setFirstName(userEditDto.getFirstName());
        user.setLastName(userEditDto.getLastName());
        user.setEmail(userEditDto.getEmail());
        user.setAuthorities(userRoles);

        this.userDao.saveAndFlush(user);
    }

    @Override
    public Long getUsersCount() {
        return this.userDao.count();
    }

    @Override
    public void registerFirstUser(@Valid RegisterUserDto registerUserDto) {
        User user = DTOConvertUtil.convert(registerUserDto, User.class);
        user.setPassword(this.passwordEncoder.encode(registerUserDto.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();

        Role role = this.roleDao.findFirstByAuthority("ADMIN");
        roles.add(role);
        user.setAuthorities(roles);

        this.userDao.saveAndFlush(user);
    }
}
