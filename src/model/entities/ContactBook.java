package model.entities;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ContactBook implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Person> contacts;

    public ContactBook() {
    }

    public ContactBook(List<Person> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Person person) {
        contacts.add(person);
    }
}
