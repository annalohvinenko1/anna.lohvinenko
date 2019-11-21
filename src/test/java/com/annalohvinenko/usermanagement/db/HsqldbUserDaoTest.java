package com.annalohvinenko.usermanagement.db;

import java.util.Collection;
import java.util.Date;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import org.junit.Test;

import com.annalohvinenko.usermanagement.User;

import junit.framework.TestCase;

public class HsqldbUserDaoTest extends TestCase {
	private UserDao userDao;
	private ConnectionFactory connectionFactory;
	
	@Before
	public void setUp() throws Exception {
        super.setUp();
        connectionFactory = new ConnectionFactoryImpl();
        userDao = new HsqldbUserDao(connectionFactory);
    }

	@Test
	  public void testCreateUser() throws DatabaseException {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setDateOfBirth(new Date());

        assertNull(user.getId());

        User userExpected = userDao.create(user);
        assertNotNull(userExpected);
        assertNotNull(userExpected.getId());
        assertEquals(userExpected.getFirstName(), user.getFirstName());
        assertEquals(userExpected.getLastName(), user.getLastName());
        assertEquals(userExpected.getDateOfBirth(), user.getDateOfBirth());
    }

    public void testFindAll() throws DatabaseException {
        int expectedUsersNumber = 2;
        Collection<User> users = userDao.findAll();
        assertNotNull("Collection is null", users);
        assertEquals("Collection size.", expectedUsersNumber, users.size());
    }
	
	protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl();
        return new DatabaseConnection(connectionFactory.createConnection());
    }
	
     protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(
                getClass()
                .getClassLoader()
                .getResourceAsStream("usersDataSet.xml")
        );
        return dataSet;
    }
}
