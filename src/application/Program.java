package application;

import model.dao.DaoFactory;
import model.dao.PersonDao;
import model.entities.Person;

public class Program {

    public static void main(String[] args) {

        PersonDao personDao = DaoFactory.createPersonDao();

        System.out.println("=== Test 1: Person insert ===");
        Person person = new Person(null, "João da Silva", "48999999999", "joaodasilva@gmail.com");
        personDao.insert(person);
        System.out.printf("Inserted! New id = %d%n", person.getId());
    }
}
