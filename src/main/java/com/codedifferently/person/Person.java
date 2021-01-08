package com.codedifferently.person;

import com.codedifferently.Main;

import java.util.Map;
import java.util.UUID;

public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String email;
    private String phoneNumber;

    public Person(Map<String, String> personData){
        this(UUID.randomUUID().toString(), personData);
    }

    public Person(String id, Map<String, String> personData){
        this.id =id;
        this.firstName = personDataParserString("firstName", personData);
        this.lastName = personDataParserString("lastName", personData);
        this.age = personDataParserInteger("age", personData);
        this.email = personDataParserString("email", personData);
        this.phoneNumber = personDataParserString("phoneNumber", personData);
    }

    private String personDataParserString(String key, Map<String, String> personData){
        return (personData.containsKey(key))? personData.get(key) : "not set";
    }

    private Integer personDataParserInteger(String key, Map<String, String> personData){
        return (personData.containsKey(key))? Integer.parseInt(personData.get(key)) : 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person: " +
                "First Name: '" + firstName + '\'' +
                ", Last Name: '" + lastName + '\'' +
                ", Age: '" + age +
                ", E-Mail: '" + email + '\'';
    }
}
