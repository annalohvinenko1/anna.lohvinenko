package com.annalohvinenko.usermanagement.db;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

import com.annalohvinenko.usermanagement.User;

 class HsqldbUserDao implements UserDao {
	private ConnectionFactory connectionFactory;
	
	private static final String INSERT_QUERU = "INSERT into users (firstname, lastname, dateOfBirth) values (?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM users";
   	
    public HsqldbUserDao() {
    }

    public HsqldbUserDao(ConnectionFactory connectionFactory){
        this.connectionFactory = connectionFactory;
    }

	
	@Override
    public User create(User user) throws DatabaseException {
        Connection connection = connectionFactory.createConnection();
        try {
            PreparedStatement statement = connection
                    .prepareStatement(INSERT_QUERU);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()) );
            int n = statement.executeUpdate();
            if(n != 1){
                throw new DatabaseException("Number of the date inserted rows: " + n);
            }
            CallableStatement callableStatement = connection
                    .prepareCall("call IDENTITY()");
            ResultSet keys = callableStatement.executeQuery();
            if(keys.next()){
                user.setId(new Long(keys.getLong(1)));
            }
            keys.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return user;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }
	
	@Override
	public User find(long id) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
// Реализация метода findAll
    
	 public Collection<User> findAll() throws DatabaseException {
        Statement statement = null;
        ResultSet resultSet = null;
        try (Connection connection = connectionFactory.createConnection()) {
            Collection<User> users = new LinkedList<>();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_QUERY);
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
            statement.close();
            resultSet.close();
            return users;
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
	
	 public ConnectionFactory getConnectionFactory() {
	        return connectionFactory;
	    }

		public void setConnectionFactory(ConnectionFactory connectionFactory) {
	        this.connectionFactory = connectionFactory;
		}
	
	private User mapUser(ResultSet resultSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User user) throws DatabaseException {
		// TODO Auto-generated method stub

	}

}
