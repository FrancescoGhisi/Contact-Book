package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.PersonDao;
import model.entities.Person;

import java.sql.*;
import java.util.ArrayList;
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
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                                """
                                    UPDATE person
                                    SET Name = ?, PhoneNumber = ?, Email = ?
                                    WHERE id = ?
                                    """);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getPhoneNumber());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, person.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Unexpected error! No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(
                                """
                                    DELETE FROM person
                                    WHERE id = ?
                                    """);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Can't find a contact by id: " + id);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Person findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(
                                """
                                    SELECT * FROM person
                                    WHERE id = ?
                                    """);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DbException("Can't find a contact by id: " + id);
            }

            return instantiatePerson(resultSet);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public Person findByName(String name) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                                """
                                    SELECT * FROM person
                                    WHERE name = ?
                                    """);

            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DbException("Can't find a contact by name: " + name);
            }

            return instantiatePerson(resultSet);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Person> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(
                                """
                                    SELECT * FROM person
                                    """);

            resultSet = preparedStatement.executeQuery();

            return instantiateContacts(resultSet);
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private List<Person> instantiateContacts(ResultSet resultSet) throws SQLException {
        List<Person> contacts = new ArrayList<>();
        while (resultSet.next()) {
            contacts.add(instantiatePerson(resultSet));
        }
        return contacts;
    }

    private Person instantiatePerson(ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("Id"));
        person.setName(resultSet.getString("Name"));
        person.setPhoneNumber(resultSet.getString("PhoneNumber"));
        person.setEmail(resultSet.getString("Email"));
        return person;
    }
}
