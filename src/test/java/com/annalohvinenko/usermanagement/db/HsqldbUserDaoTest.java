package com.annalohvinenko.usermanagement.db;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.annalohvinenko.usermanagement.User;

import junit.framework.TestCase;

public class HsqldbUserDaoTest extends TestCase {
	private UserDao userDao;
	
	@Before
	  public void setUp() throws Exception {
        super.setUp();
        userDao = new HsqldbUserDao();
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


}
