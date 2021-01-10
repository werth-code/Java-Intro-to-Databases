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
        this.people = new ArrayList<>(); //// TODO: 1/8/21 Changed this to be a seperate List
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return this.owner;
    }

    public void addPerson(Person person){
        this.people.add(person);
        if(saveAll()) removePerson(person);  //// TODO: 1/8/21  Changed This to remove a person after we save them to DB
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

    public Boolean saveAll(){
        try {
            dataBase.saveAllPeople(this.people);
            return true;
        }
        catch (DatabaseCouldNotSaveException e) {
           logger.warning("Failed To Save to ADDRESS BOOK");
           return false;
        }
    }

}
