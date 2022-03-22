package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.PersonEnum;

public class ProjectUtils {
    public static final String delimit = "-";
    public static int ownerCount = 0;
    public static int supplierCount = 0;
    public static int salespersonCount = 0;
    public static int customerCount = 0;

    public static String generatePersonId(PersonEnum type){
        StringBuilder sb = new StringBuilder();
        switch(type) {
            case OWNER:
                sb.append("OWN");
                sb.append(delimit);
                sb.append(++ownerCount);
                return sb.toString();
            case SUPPLIER:
                sb.append("SUP");
                sb.append(delimit);
                sb.append(++supplierCount);
                return sb.toString();
            case SALESPERSON:
                sb.append("SAL");
                sb.append(delimit);
                sb.append(++salespersonCount);
                return sb.toString();
            case CUSTOMER:
                sb.append("CUS");
                sb.append(delimit);
                sb.append(++customerCount);
                return sb.toString();
        }
        return "123";
    }

    public static String generateEntityId(ComponentEnum type){
        StringBuilder sb = new StringBuilder();
        switch(type) {
            case WAREHOUSE:
                sb.append("WAR");
                sb.append(delimit);
                sb.append(++ownerCount);
                return sb.toString();
            case ORDER:
                sb.append("ORD");
                sb.append(delimit);
                sb.append(++supplierCount);
                return sb.toString();
            case INVOICE:
                sb.append("INV");
                sb.append(delimit);
                sb.append(++salespersonCount);
                return sb.toString();
        }
        return "123";
    }
}
