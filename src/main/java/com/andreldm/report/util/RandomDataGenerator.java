package com.andreldm.report.util;

import com.andreldm.report.pojo.Customer;
import com.andreldm.report.pojo.Purchase;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class RandomDataGenerator {
    private static Faker FAKER = new Faker();

    public static Customer randomCustomer(int id) {
        Customer c = new Customer();
        c.setId(id);

        String firstName = FAKER.name().firstName();
        String lastName = FAKER.name().lastName();
        c.setName(StringUtils.join(firstName, " ", lastName));

        c.setEmail(FAKER.internet().safeEmailAddress(StringUtils.join(
                firstName.replaceAll("'", "").toLowerCase(), ".",
                lastName.replaceAll("'", "").toLowerCase())));

        c.setAddress(FAKER.address().fullAddress());
        c.setPhone(FAKER.phoneNumber().phoneNumber());
        c.setSince(RandomDataGenerator.randomDate(LocalDate.of(1998, 01, 01), LocalDate.of(2016, 12, 31)));

        List<Purchase> purchases = new ArrayList<>();
        c.setPurchases(purchases);
        for (int i = 0; i < FAKER.number().numberBetween(0, 50); i++) {
            purchases.add(randomPurchase());
        }

        return c;
    }

    public static Purchase randomPurchase() {
        Purchase p = new Purchase();
        p.setProduct(FAKER.commerce().productName());
        p.setValue(1 + (FAKER.random().nextDouble() * (1000 - 1)));
        p.setQuantity(FAKER.number().numberBetween(1, 10));
        p.setDate(RandomDataGenerator.randomDateTime(
                LocalDateTime.of(2012, 01, 01, 0, 0),
                LocalDateTime.of(2017, 03, 31, 23, 59)));

        return p;
    }

    public static LocalDateTime randomDateTime(LocalDateTime min, LocalDateTime max) {
        long MIN = min.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long MAX = max.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long RESULT = FAKER.number().numberBetween(MIN, MAX);

        return Instant.ofEpochMilli(RESULT).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate randomDate(LocalDate min, LocalDate max) {
        long MIN = min.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long MAX = max.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long RESULT = FAKER.number().numberBetween(MIN, MAX);

        return Instant.ofEpochMilli(RESULT).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
