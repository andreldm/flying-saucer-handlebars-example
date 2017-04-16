package com.andreldm.report;

import com.andreldm.report.engine.ReportEngine;
import com.andreldm.report.pojo.Customer;
import com.andreldm.report.util.RandomDataGenerator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    public static void main(String[] args) {
        List<Customer> customers = IntStream
                .rangeClosed(1, 25)
                .mapToObj(RandomDataGenerator::randomCustomer)
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());

        Map<String, Object> data = new HashMap<>();
        data.put("customers", customers);

        new ReportEngine().generate("purchases", "report.pdf", data);
    }
}
