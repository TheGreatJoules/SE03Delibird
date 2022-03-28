package com.csulb.ase.assignment3.relationships;

import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.testutils.SetupCommon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;


public class TestPerson {
    private Owner owner;

    @BeforeSuite
    public void setup() throws IOException {
        InputStream inputJsonStream = new FileInputStream("tests/com/csulb/ase/assignment3/data/person2.json");
        String[] items = IOUtils.toString(inputJsonStream, StandardCharsets.UTF_8).split("\\r?\\n");
        owner = (Owner) SetupCommon.loadPersonGraph(items);
        System.out.println(owner.getSalesPersonMap().size());
    }

    @Test
    public void testOwner(){
        System.out.println();
    }

}
