package com.csulb.ase.assignment3.relationships;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestPerson {
    @BeforeTest
    public void setup() throws IOException {
        InputStream inputJsonStream = new FileInputStream("test/com/csulb/ase/assignment3/data/person-data.json");
        String jsonStr = IOUtils.toString(inputJsonStream, StandardCharsets.UTF_8);
        JSONObject object = new JSONObject(jsonStr);
        JSONArray personObject = object.getJSONArray("person");
        JSONObject ownerObject = (JSONObject) personObject.get(0);
        JSONObject salespersonObject = (JSONObject) personObject.get(1);
        JSONObject customer = (JSONObject) personObject.get(2);
        System.out.println(ownerObject);
    }

    @Test
    public void testPerson(){
        System.out.println();
    }

}
