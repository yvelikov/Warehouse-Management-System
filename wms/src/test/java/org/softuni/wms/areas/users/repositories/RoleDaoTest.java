package org.softuni.wms.areas.users.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.wms.areas.users.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@ActiveProfiles("dev")
public class RoleDaoTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RoleDao roleDao;

    private Role superAdminRole;
    private Role adminRole;
    private Role employeeRole;
    private Role warehousemanRole;

    @Before
    public void setUp() throws Exception {
        this.superAdminRole = new Role();
        this.superAdminRole.setAuthority("SUPER_ADMIN");

        this.adminRole = new Role();
        this.adminRole.setAuthority("ADMIN");

        this.employeeRole = new Role();
        this.employeeRole.setAuthority("EMPLOYEE");

        this.warehousemanRole = new Role();
        this.warehousemanRole.setAuthority("WAREHOUSEMAN");
    }

    @Test
    public void testFindAllRolesFilter_withGivenAuthority_ShouldFilterAuthorities() {
//        this.testEntityManager.persistAndFlush(this.superAdminRole);
//        this.testEntityManager.persistAndFlush(this.adminRole);
//        this.testEntityManager.persistAndFlush(this.employeeRole);
//        this.testEntityManager.persistAndFlush(this.warehousemanRole);

        List<Role> result = this.roleDao.findAllRolesFilter("SUPER_ADMIN");

        Assert.assertEquals("ADMIN,EMPLOYEE,WAREHOUSEMAN",String.join(",",result.stream().map(Role::getAuthority).collect(Collectors.toList())));
    }

}