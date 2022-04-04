package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;

import java.util.HashMap;
import java.util.Map;

public class PersonManager {
    private Map<String, Person> persons;

    public PersonManager() {
        this.persons = new HashMap<>();
    }

    public PersonManager(Map<String, Person> persons) {
        this.persons = persons;
    }

    public int createPerson(Person person) {
        this.persons.put(person.getId(), person);
        return 0;
    }

    public Person retrievePerson(String person_id) {
        return this.persons.get(person_id);
    }

}
