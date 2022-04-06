package com.csulb.ase.assignment3.models;

/**
 * https://www.calculator.net/sales-tax-calculator.html
 */
public enum StateTaxRateEnum {
    AL("Alabama", 4.0f),
    AK("Alaska", 4.0f),
    AZ("Arizona", 5.6f),
    AR("Arkansas", 6.50f),
    CA("California", 7.25f),
    CO("Colorado", 2.9f),
    CT("Connecticut", 6.35f),
    DE("Delaware", 0f),
    DC("District of Columbia", 6f),
    Fl("Florida", 6f),
    GA("Georgia", 4f),
    GU("Guam", 4f),
    HI("Hawaii", 4.17f),
    ID("Idaho", 6f),
    IL("Ill", 6.25f),
    IN("Ind", 7f),
    IA("Iowa", 6f),
    KS("Kansas", 6.5f),
    KY("Kentucky", 6f),
    LA("Louisiana", 4.45f),
    ME("Maine", 5.0f),
    MD("Maryland", 6f),
    MA("Massachusetts", 6.25f),
    MI("Michigan", 6f),
    MN("Minnesota", 6.88f),
    MS("Mississippi", 7f),
    MO("Missouri", 4.23f),
    MT("Montana", 0f),
    NE("Nebraska", 5.50f),
    NV("Nevada", 6.85f),
    NH("New Hampshire", 0f),
    NJ("New Jersey", 6.63f),
    NM("New Mexico", 5.13f),
    NY("New York", 4f),
    NC("North Carolina", 4.75f),
    ND("North Dakota", 5f),
    OH("Ohio", 5.75f),
    OK("Oklahoma", 4.50f),
    OR("Oregon", 0f),
    PA("Pennsylvania", 6f),
    PR("Puerto Rico", 10.50f),
    RI("Rhode Island", 7f),
    SC("South Carolina", 6f),
    SD("South Dakota", 4f),
    TN("Tennessee", 7f),
    TX("Texas", 6.25f),
    UT("Utah", 5.95f),
    VT("Vermont", 6f),
    VI("Virgin Islands", 5.30f),
    VA("Virginia", 5.3f),
    WA("Washington", 6.5f),
    WV("West Virginia", 6f),
    WI("Wisconsin", 5f),
    WY("Wyoming", 4f);

    public final String state;
    public final float tax_rate;

    private StateTaxRateEnum(String state,float tax_rate) {
        this.state = state;
        this.tax_rate = tax_rate;
    }
}
