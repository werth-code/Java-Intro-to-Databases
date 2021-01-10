package com.codedifferently;

import com.codedifferently.addressbook.AddressBook;
import com.codedifferently.addressbook.exceptions.AddressBookPersonNotFoundException;
import com.codedifferently.database.MySQLDatabase;
import com.codedifferently.database.DataBaseConnectionException;
import com.codedifferently.person.Person;

import java.util.*;

public class Main {

    private final AddressBook addressBook;
    private static Scanner scanner;
    private ArrayList<String> menu;
    MySQLDatabase mySQLDatabase;

    public Main() throws DataBaseConnectionException {
        mySQLDatabase = new MySQLDatabase();
        scanner = new Scanner(System.in);
        addressBook = new AddressBook(null, mySQLDatabase);
        initMenuOption();
    }

    private void initMenuOption(){
            menu = new ArrayList<>();
            menu.add("Exit");
            menu.add("Show All Contacts");
            menu.add("Add New Contact");
            menu.add("Find Contact By E-Mail");
            menu.add("Update An Existing Contact");
            menu.add("Delete A Contact");
            menu.add("Set Owner");
    }

    public Integer displayMenu(){
        int option = 0;
        for(int i = 0; i < menu.size(); i++){
            String menuOption = String.format("Press %d To %s", i, menu.get(i));
            System.out.println(menuOption);
        }
        option = scanner.nextInt();
        return option;
    }

    public void displayAllPeople() {
        mySQLDatabase.getAllPeople().forEach(person -> System.out.println(person.toString()));
        System.out.println("");
    }

    private String getStringOutPut(String msg){
        System.out.println(msg);
        return scanner.next();
    }

    public Person findPerson(){
        String email = getStringOutPut("Enter The Email Address Of The Contact You Are Looking For: ");
        try {
            Person person = addressBook.getPersonByEmail(email);
            displayPerson(person);
            return person;
        }
        catch (AddressBookPersonNotFoundException e) {
            System.out.println("No User With This E-Mail Address: " + email);
        }
        return null;
    }

    public void createNewPerson(){
        Map<String, String> rawData = new HashMap<>();
        rawData.put("firstName", getStringOutPut("Please enter first name:"));
        rawData.put("lastName", getStringOutPut("Please enter last name:"));
        rawData.put("email", getStringOutPut("Please enter email:"));
        rawData.put("age", getStringOutPut("Please enter age:"));
        Person person = new Person(rawData);
        addressBook.addPerson(person);
    }


   public void updateAnExistingContact() { // // TODO: 1/9/21 This works but is messy! 
        Person person = findPerson();
        Map<String, String> rawData = new HashMap<>();
        rawData.put("firstName", getStringOutPut("Please enter new first name:"));
        rawData.put("lastName", getStringOutPut("Please enter new last name:"));
        rawData.put("email", getStringOutPut("Please enter new email:"));
        rawData.put("age", getStringOutPut("Please enter new age:"));
        Person updatedPerson = new Person(person.getId(), rawData);
        mySQLDatabase.deletePerson(person);
        mySQLDatabase.updatePerson(updatedPerson, "PERSON");
   }

    public void deleteAnExistingContact() {
        Person person = findPerson();
        mySQLDatabase.deletePerson(person);
    }

    private void displayPerson(Person person){
        String output = String.format("%s %s %s %s %d", person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge());
        System.out.println(output);
    }

    public void setOwner() {
        Person person = findPerson();
        addressBook.setOwner(person);
        mySQLDatabase.updatePerson(person, "OWNER");
    }

    public void sayOwner() {
        if(addressBook.getOwner() != null) System.out.println(" Of " + addressBook.getOwner().getFirstName());
    }


    public static void main(String[] args) {
        try {
            Main main = new Main();
            Boolean endProgram = false;
            System.out.println("\nWelcome To The Address Book");
            while (!endProgram) {
                int menuOption = main.displayMenu();
                switch(menuOption){
                    case 0:
                        System.out.println("Goodbye!!");
                        endProgram = true;
                        break;
                    case 1:
                        main.displayAllPeople();
                        break;
                    case 2:
                        main.createNewPerson(); //// TODO: 1/8/21  
                        break;
                    case 3:
                        main.findPerson();
                        break;
                    case 4:
                        main.updateAnExistingContact();
                        break;
                    case 5:
                        main.deleteAnExistingContact();
                        break;
                    case 6:
                        main.setOwner();
                        break;
                    default:
                        System.out.println("I don't know that command");
                        break;
                }
            }
        }
        catch (DataBaseConnectionException e) {
            System.out.println("Your database could not be connected to.");
        }
    }
}
