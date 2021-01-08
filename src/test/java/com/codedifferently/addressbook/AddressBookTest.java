package com.codedifferently.addressbook;

import com.codedifferently.addressbook.exceptions.AddressBookPersonNotFoundException;
import com.codedifferently.database.DataBaseConnectionException;
import com.codedifferently.database.MySQLDatabase;
import com.codedifferently.person.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class AddressBookTest {
    AddressBook addressBook;
    MySQLDatabase mySQLDatabase;
    Person person;
    Map<String, String> personMap;

    @Before
    public void setup() throws DataBaseConnectionException {
        mySQLDatabase = new MySQLDatabase();
        addressBook = new AddressBook(null, mySQLDatabase);
        personMap = new HashMap<>();
        personMap.put("firstName", "Matt");
        personMap.put("lastName", "Werth");
        personMap.put("email", "matthewwerth@gmail.com");
        personMap.put("age", "33");
        person = new Person(personMap);
    }

    @Test
    public void addPerson() throws AddressBookPersonNotFoundException {
        addressBook.addPerson(person);
        String actual = addressBook.getPersonByEmail("matthewwerth@gmail.com").toString();
        String expected = "Person: First Name: 'Matt', Last Name: 'Werth', Age: '33, E-Mail: 'matthewwerth@gmail.com'";
        System.out.println(actual);

        Assert.assertEquals("Get Person Added To Address Book: ", expected, actual);
    }

    @Test
    public void removePerson() {
        addressBook.removePerson(person);
        Integer actual = addressBook.getAllPeople().size();
        Integer expected = 0;

        Assert.assertEquals("Assert List Size", expected, actual);
    }

    @Test
    public void getPersonByEmail() {
    }

    @Test
    public void getAllPeople() {
    }

    @Test
    public void saveAll() {
    }
}