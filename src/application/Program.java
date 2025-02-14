package application;

import model.dao.DaoFactory;
import model.dao.PersonDao;
import model.entities.ContactBook;
import model.entities.Person;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PersonDao personDao = DaoFactory.createPersonDao();


        System.out.println("=== Test 1: Person insert ===");
        Person person = new Person(null, "Maria da Silva", "48999999999", "mariadasilva@gmail.com");
        personDao.insert(person);
        System.out.printf("Inserted! New id = %d%n", person.getId());

        System.out.println("\n=== Test 2: Person findAll ===");
        ContactBook contacts = new ContactBook(personDao.findAll());
        contacts.getContacts().forEach(System.out::println);

        System.out.println("\n=== Test 3: Person findById ===");
        System.out.print("Enter id to find a contact: ");
        int id = sc.nextInt();
        Person personFound = personDao.findById(id);
        System.out.println(personFound);

        System.out.println("\n=== Test 3: Person update ===");
        personFound.setName("Marcos da Silva");
        personDao.update(personFound);
        System.out.println("Update completed!");

        System.out.println("\n=== Test 4: Person findByName ===");
        System.out.print("Enter name to find a contact: ");
        sc.nextLine();
        String name = sc.nextLine();
        personFound = personDao.findByName(name);
        System.out.println(personFound);

        System.out.println("\n=== Test 5: Person deleteById ===");
        System.out.print("Enter id to delete a contact: ");
        id = sc.nextInt();
        personDao.deleteById(id);
        System.out.println("Delete completed");
    }
}
