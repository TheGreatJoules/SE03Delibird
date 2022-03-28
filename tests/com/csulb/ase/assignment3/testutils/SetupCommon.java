package com.csulb.ase.assignment3.testutils;

import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SetupCommon {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static Person loadPerson(String item) throws IOException {
        switch(PersonEnum.valueOf(new JSONObject(item).get("type").toString())) {
            case OWNER:
                Owner owner = objectMapper.readValue(item, Owner.class);
                if (owner.getCustomerMap() == null || owner.getCustomerMap().isEmpty()) {
                    owner.setCustomerMap(new HashMap<>());
                }
                if (owner.getSalesPersonMap() == null || owner.getSalesPersonMap().isEmpty()) {
                    owner.setSalesPersonMap(new HashMap<>());
                }
                return owner;
            case SUPPLIER:
                return objectMapper.readValue(item, Supplier.class);
            case SALESPERSON:
                return objectMapper.readValue(item, SalesPerson.class);
            case CUSTOMER:
                return objectMapper.readValue(item, Customer.class);
        }
        return null;
    }

    public static Person loadPersonGraph(String[] items) throws IOException {
        Owner owner = null;

        for (String item : items) {
            Person person = loadPerson(item);
            if (person instanceof Owner) {
                owner = (Owner) person;
            } else if (person instanceof Customer) {
                Objects.requireNonNull(owner).getCustomerMap().put(person.getId(),(Customer) person);
            } else if (person instanceof Supplier) {
                Objects.requireNonNull(owner).getSupplierMap().put(person.getId(),(Supplier) person);
            } else if (person instanceof SalesPerson) {
                Objects.requireNonNull(owner).getSalesPersonMap().put(person.getId(),(SalesPerson) person);
            }
        }

        return owner;
    }
}
