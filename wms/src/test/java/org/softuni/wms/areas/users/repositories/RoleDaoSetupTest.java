//package org.softuni.wms.areas.users.repositories;
//
//import org.junit.*;
//import org.junit.runner.RunWith;
//import org.softuni.wms.areas.users.entities.Role;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@DataJpaTest
//@ActiveProfiles("dev")
//public class RoleDaoSetupTest {
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private RoleDao roleDao;
//
//    private Role superAdminRole;
//    private Role adminRole;
//    private Role employeeRole;
//    private Role warehousemanRole;
//
//    @BeforeClass
//    public static void init() throws SQLException, ClassNotFoundException, IOException {
//        Class.forName("org.hsqldb.jdbc.JDBCDriver");
//        // initialize database
//        initDatabase();
//    }
//
//    @AfterClass
//    public static void destroy() throws SQLException, ClassNotFoundException, IOException {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
//            statement.executeUpdate("DROP TABLE roles");
//            connection.commit();
//        }
//    }
//
//    private static void initDatabase() throws SQLException {
//        try (Connection connection = getConnection(); Statement statement = connection.createStatement();) {
//            statement.execute("CREATE TABLE roles (id VARCHAR(255) NOT NULL, authority VARCHAR(255) NOT NULL,"
//                            + "PRIMARY KEY (id))");
//            connection.commit();
//            statement.executeUpdate(
//                    "INSERT INTO roles VALUES ('1','SUPER_ADMIN')");
//            statement.executeUpdate("INSERT INTO roles VALUES ('2','ADMIN')");
//            statement.executeUpdate("INSERT INTO roles VALUES ('3','EMPLOYEE')");
//            statement.executeUpdate("INSERT INTO roles VALUES ('4','WAREHOUSEMAN')");
//            connection.commit();
//        }
//    }
//
//
//    private static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection("jdbc:hsqldb:mem:wms_test", "sa", "");
//    }
//
//
//    @Test
//    public void testFindAllRolesFilter_withGivenAuthority_ShouldFilterAuthorities() {
////        this.testEntityManager.persistAndFlush(this.superAdminRole);
////        this.testEntityManager.persistAndFlush(this.adminRole);
////        this.testEntityManager.persistAndFlush(this.employeeRole);
////        this.testEntityManager.persistAndFlush(this.warehousemanRole);
//
//        List<Role> result = this.roleDao.findAllRolesFilter("SUPER_ADMIN");
//
//        Assert.assertEquals("ADMIN,EMPLOYEE,WAREHOUSEMAN",
//                String.join(",",result.stream().map(Role::getAuthority).collect(Collectors.toList())));
//    }
//
//}