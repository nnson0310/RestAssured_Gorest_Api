package utils;

import net.datafaker.Faker;

import java.util.Random;

public class DataFaker {

    private static Faker faker;
    private static DataFaker dataFaker;

    private DataFaker() {
        faker = new Faker();
    }

    public static DataFaker getInstance() {
        if (dataFaker == null) {
            dataFaker = new DataFaker();
        }
        return dataFaker;
    }

    public String getFirstName() {
        return faker.name().firstName();
    }

    public String getLastName() {
        return faker.name().lastName();
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public String getEmail() {
        return faker.internet().emailAddress();
    }

    public String getGender() {
        String[] genders = {"male", "female"};
        return genders[MethodHelper.getZeroOrOne()];
    }

    public String getStatus() {
        String[] status = {"active", "inactive"};
        return status[MethodHelper.getZeroOrOne()];
    }
}
