package org.softuni.wms.areas.users.services.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.softuni.wms.areas.users.entities.Role;
import org.softuni.wms.areas.users.entities.User;
import org.softuni.wms.areas.users.models.binding.RegisterUserDto;
import org.softuni.wms.areas.users.models.binding.UserDto;
import org.softuni.wms.areas.users.models.binding.UserEditDto;
import org.softuni.wms.areas.users.models.service.RoleServiceDto;
import org.softuni.wms.areas.users.models.service.UserServiceDto;
import org.softuni.wms.areas.users.models.view.UserViewDto;
import org.softuni.wms.areas.users.repositories.UserDao;
import org.softuni.wms.areas.users.services.impl.RoleServiceImpl;
import org.softuni.wms.areas.users.services.impl.UserServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String EMAIL = "test.mail@mail.com";
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String EMPLOYEE_AUTHORITY = "EMPLOYEE";
    private static final String ADMIN_AUTHORITY = "ADMIN";
    private static final String SUPER_ADMIN_AUTHORITY = "SUPER_ADMIN";

    @Mock
    private UserDao userDao;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;


    private User user;
    private User admin;
    private UserDto userDto;
    private UserEditDto userEditDto;
    private RegisterUserDto registerUserDto;

    @Before
    public void setUp() throws Exception {

        Role employeeRole = new Role();
        employeeRole.setId("3");
        employeeRole.setAuthority(EMPLOYEE_AUTHORITY);

        Role adminRole = new Role();
        adminRole.setId("2");
        adminRole.setAuthority(ADMIN_AUTHORITY);

        RoleServiceDto employeeRoleServiceDto = new RoleServiceDto();
        employeeRoleServiceDto.setId("3");
        employeeRoleServiceDto.setAuthority(EMPLOYEE_AUTHORITY);

        RoleServiceDto superAdminRoleServiceDto = new RoleServiceDto();
        superAdminRoleServiceDto.setId("1");
        superAdminRoleServiceDto.setAuthority(SUPER_ADMIN_AUTHORITY);

        this.userDto = new UserDto();
        this.userDto.setUsername(USERNAME);
        this.userDto.setPassword(PASSWORD);
        this.userDto.setEmail(EMAIL);
        this.userDto.setFirstName(FIRST_NAME);
        this.userDto.setLastName(LAST_NAME);
        this.userDto.setAuthorities(new HashSet<>() {{
            add("EMPLOYEE");
        }});

        this.user = new User();
        this.user.setId("1");
        this.user.setUsername(USERNAME);
        this.user.setPassword(PASSWORD);
        this.user.setEmail(EMAIL);
        this.user.setFirstName(FIRST_NAME);
        this.user.setLastName(LAST_NAME);
        this.user.setAuthorities(new HashSet<>() {{
            add(employeeRole);
        }});

        this.admin = new User();
        this.admin.setId("2");
        this.admin.setUsername("jaco");
        this.admin.setPassword("1234");
        this.admin.setEmail("jaco@wms.com");
        this.admin.setFirstName("Jaco");
        this.admin.setLastName("Smith");
        this.admin.setAuthorities(new HashSet<>() {{
            add(adminRole);
        }});

        this.userEditDto = new UserEditDto();
        this.userEditDto.setId("2");
        this.userEditDto.setUsername("jaco_edited");
        this.userEditDto.setEmail("jaco2@wms.com");
        this.userEditDto.setFirstName("Jaco Edit");
        this.userEditDto.setLastName("Smith Edit");
        this.userEditDto.setAuthorities(new HashSet<>() {{
            add(EMPLOYEE_AUTHORITY);
        }});

        this.userEditDto = new UserEditDto();
        this.userEditDto.setId("2");
        this.userEditDto.setUsername("jaco_edited");
        this.userEditDto.setEmail("jaco2@wms.com");
        this.userEditDto.setFirstName("Jaco Edit");
        this.userEditDto.setLastName("Smith Edit");
        this.userEditDto.setAuthorities(new HashSet<>() {{
            add(EMPLOYEE_AUTHORITY);
        }});

        this.registerUserDto = new RegisterUserDto();
        this.registerUserDto.setUsername("admin");
        this.registerUserDto.setEmail("admin@wms.com");
        this.registerUserDto.setFirstName("admin");
        this.registerUserDto.setLastName("admin");
        this.registerUserDto.setPassword(PASSWORD);


        when(this.userDao.saveAndFlush(any())).thenAnswer(a -> a.getArgument(0));

        when(this.userDao.getOne(userEditDto.getId())).thenReturn(this.admin);

        when(this.userDao.findAllByOrderByUsername()).thenReturn(List.of(this.admin, this.user));

        when(this.roleService.findByAuthority(EMPLOYEE_AUTHORITY)).thenReturn(employeeRoleServiceDto);
        when(this.roleService.findByAuthority(SUPER_ADMIN_AUTHORITY)).thenReturn(superAdminRoleServiceDto);

    }

    @Test
    public void addUser_withValidInput_ResultNotNull() {
        UserServiceDto userServiceDto = this.userService.addUser(this.userDto);
        Assert.assertNotNull("User was not created successfully", userServiceDto);
    }

    @Test
    public void addUser_withValidInput_MappedCorrectly() {
        UserServiceDto userServiceDto = this.userService.addUser(this.userDto);

        String encryptedPassword = this.passwordEncoder.encode(PASSWORD);

        RoleServiceDto roleServiceDto = new RoleServiceDto();
        roleServiceDto.setId("3");
        roleServiceDto.setAuthority(EMPLOYEE_AUTHORITY);
        Set<RoleServiceDto> expectedAuthorities = new HashSet<>() {{
            add(roleServiceDto);
        }};

        Assert.assertEquals("Username not mapped correctly", this.user.getUsername(), userServiceDto.getUsername());
        Assert.assertEquals("Password not encrypted correctly", encryptedPassword, userServiceDto.getPassword());
        Assert.assertEquals("Email not mapped correctly", this.user.getEmail(), userServiceDto.getEmail());
        Assert.assertEquals("First name not mapped correctly", this.user.getFirstName(), userServiceDto.getFirstName());
        Assert.assertEquals("Last name not mapped correctly", this.user.getLastName(), userServiceDto.getLastName());
        Assert.assertEquals("Authorities not mapped correctly",
                expectedAuthorities.stream().map(RoleServiceDto::getAuthority).toArray()[0],
                userServiceDto.getAuthorities().stream().map(RoleServiceDto::getAuthority).toArray()[0]);

        Assert.assertTrue("AccountNonExpired not true", userServiceDto.getAccountNonExpired());
        Assert.assertTrue("AccountNonLocked not true", userServiceDto.getAccountNonLocked());
        Assert.assertTrue("CredentialsNonExpired not true", userServiceDto.getCredentialsNonExpired());
        Assert.assertTrue("Enabled not true", userServiceDto.getEnabled());
    }

    @Test
    public void findAllSortedByUsername_mapsRolesCorrectly() {
        this.user.setEnabled(true);
        this.user.setAccountNonLocked(true);
        this.user.setAccountNonExpired(true);
        this.user.setCredentialsNonExpired(true);
        this.admin.setEnabled(true);
        this.admin.setAccountNonLocked(true);
        this.admin.setAccountNonExpired(true);
        this.admin.setCredentialsNonExpired(true);
        List<UserViewDto> allSortedByUsername = this.userService.findAllSortedByUsername();
        Assert.assertEquals("User roles not mapped correctly",
                Arrays.toString(List.of("[ADMIN]", "[EMPLOYEE]").toArray()),
                Arrays.toString(allSortedByUsername.stream().map(UserViewDto::getAuthorities).toArray()));
    }

    @Test
    public void edit_withValidUserEditDto_isMappedCorrectly() {
        this.admin.setEnabled(true);
        this.admin.setAccountNonLocked(true);
        this.admin.setAccountNonExpired(true);
        this.admin.setCredentialsNonExpired(true);
        UserServiceDto userServiceDto = this.userService.edit(this.userEditDto);
        Assert.assertEquals("Username not mapped correctly", userServiceDto.getUsername(), this.userEditDto.getUsername());
        Assert.assertEquals("First name not mapped correctly", userServiceDto.getFirstName(), this.userEditDto.getFirstName());
        Assert.assertEquals("Last name not mapped correctly", userServiceDto.getLastName(), this.userEditDto.getLastName());
        Assert.assertEquals("EMail not mapped correctly", userServiceDto.getEmail(), this.userEditDto.getEmail());
        Assert.assertEquals("User roles not mapped correctly",
                Arrays.toString(List.of("EMPLOYEE").toArray()),
                Arrays.toString(userServiceDto.getAuthorities().stream().map(RoleServiceDto::getAuthority).toArray()));
    }

    @Test
    public void registerFirstUser_userHasAuthoritySuperAdmin() {
        UserServiceDto userServiceDto = this.userService.registerFirstUser(this.registerUserDto);
        Assert.assertEquals("Super admin role not assigned correctly",
                Arrays.toString(List.of("SUPER_ADMIN").toArray()),
                Arrays.toString(userServiceDto.getAuthorities().stream().map(RoleServiceDto::getAuthority).toArray()));
    }

    @Test
    public void disable_DisablesUser() {
        this.admin.setEnabled(true);
        this.admin.setAccountNonLocked(true);
        this.admin.setAccountNonExpired(true);
        this.admin.setCredentialsNonExpired(true);
        UserServiceDto userServiceDto = this.userService.disable("2");
        Assert.assertFalse("User is not disabled", userServiceDto.getEnabled());
    }

    @Test
    public void enable_EnablesUser() {
        this.admin.setEnabled(false);
        this.admin.setAccountNonLocked(true);
        this.admin.setAccountNonExpired(true);
        this.admin.setCredentialsNonExpired(true);
        UserServiceDto userServiceDto = this.userService.enable("2");
        Assert.assertTrue("User is not enabled", userServiceDto.getEnabled());
    }

}