package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.utils.CommissionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the relationship between people
 */
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

    public void updateEmployee(String person_id, long timestamp) {
        SalesPerson salesPerson = (SalesPerson) retrievePerson(person_id);
        double old_performance_score = salesPerson.getPerformance_score();

        salesPerson.setTotal_sales(CommissionUtil.calculateSingleSale(salesPerson.getTotal_sales()));

        salesPerson.setPerformance_score(CommissionUtil.calculatePerformanceScore(
                salesPerson.getPerformance_score(),
                salesPerson.getLast_sell(),
                timestamp));

        salesPerson.setCommission_rate(CommissionUtil.calculateCommissionRate(
                salesPerson.getCommission_rate(),
                old_performance_score,
                salesPerson.getPerformance_score()
        ));

        salesPerson.setTotal_earnings(CommissionUtil.calculateEarnings(
                salesPerson.getCommission_rate(),
                salesPerson.getTotal_sales()));

        salesPerson.setLast_sell(timestamp);
    }

}
