package model.dao;

import model.entities.Person;

import java.util.List;

public interface PersonDao {

    void insert(Person person);
    void update(Person person);
    void deleteById(Integer id);
    Person findById(Integer id);
    Person findByName(String name);
    List<Person> findAll();
}
