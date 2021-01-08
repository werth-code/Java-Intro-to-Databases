package com.codedifferently.addressbook;

import com.codedifferently.addressbook.exceptions.AddressBookPersonNotFoundException;
import com.codedifferently.database.MySQLDatabase;
import com.codedifferently.database.exceptions.DatabaseCouldNotSaveException;
import com.codedifferently.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AddressBook {
    private static final Logger logger = Logger.getGlobal();
    private Person owner;
    private List<Person> people;
    private MySQLDatabase dataBase;

    public AddressBook(Person owner, MySQLDatabase database){
        this.owner = owner;
        this.dataBase = database;
        this.people = new ArrayList<>();
        //this.people = database.getAllPeople(); //Should get all of our people from database.
    }

    public void addPerson(Person person){
        this.people.add(person);
        saveAll();
        removePerson(person);
    }

    public void removePerson(Person person){
        this.people.remove(person);
    }

    public Person getPersonByEmail(String email) throws AddressBookPersonNotFoundException {
        return dataBase.getAllPeople().stream()
                .filter(person -> person.getEmail().equals(email))
                .findFirst()
                .orElseThrow(AddressBookPersonNotFoundException::new);
    }

    public List<Person> getAllPeople(){
        return people;
    }

    public Boolean saveAll(){ //Save all should sink up with our database. So remove extras and add missing. // TODO: 1/8/21
        try {
            dataBase.saveAllPeople(this.people);
            return true;
        } catch (DatabaseCouldNotSaveException e) {
           logger.info("Failed To Save AT SAVEALL() IN ADDRESSBOOK");
            return false;
        }

    }

}
