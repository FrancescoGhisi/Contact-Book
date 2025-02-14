package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.PersonDao;
import model.entities.Person;

import java.sql.*;
import java.util.List;

public class PersonDaoJDBC implements PersonDao {

    private final Connection connection;

    public PersonDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Person person) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                                    """
                                        INSERT INTO person
                                        (Name, PhoneNumber, Email)
                                        VALUES
                                        (?, ?, ?)
                                        """, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getPhoneNumber());
            preparedStatement.setString(3, person.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    person.setId(id);
                }
            } else {
                throw new DbException("Unexpected error! No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public void update(Person person) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Person findById(Integer id) {
        return null;
    }

    @Override
    public Person findByName(String name) {
        return null;
    }

    @Override
    public List<Person> findAll() {
        return List.of();
    }
}
