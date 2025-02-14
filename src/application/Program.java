package application;

import model.dao.DaoFactory;
import model.dao.PersonDao;
import model.entities.ContactBook;
import model.entities.Person;

public class Program {

    public static void main(String[] args) {

        PersonDao personDao = DaoFactory.createPersonDao();

        System.out.println("=== Test 1: Person insert ===");
        Person person = new Person(null, "Maria da Silva", "48999999999", "mariadasilva@gmail.com");
        personDao.insert(person);
        System.out.printf("Inserted! New id = %d%n", person.getId());

        System.out.println("\n=== Test 2: Person findAll ===");
        ContactBook contacts = new ContactBook(personDao.findAll());
        contacts.getContacts().forEach(System.out::println);
    }
}
