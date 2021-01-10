package com.codedifferently.database;
import com.codedifferently.database.exceptions.DatabaseCouldNotSaveException;
import com.codedifferently.person.Person;

import java.util.List;

interface Database {
    void updatePerson(Person person, String table) throws DatabaseCouldNotSaveException;
    void saveAllPeople(List<Person> people) throws DatabaseCouldNotSaveException;
    void deletePerson(Person person) throws DatabaseCouldNotSaveException;
    void setOwnerOnDatabase(Person person) throws DatabaseCouldNotSaveException;
    List<Person> getAllPeople();
}
