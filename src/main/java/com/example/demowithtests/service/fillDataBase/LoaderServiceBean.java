package com.example.demowithtests.service.fillDataBase;

import com.example.demowithtests.domain.Address;
import com.example.demowithtests.domain.Document;
import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.*;
import static java.util.concurrent.TimeUnit.*;

@Slf4j
@AllArgsConstructor
@Service
public class LoaderServiceBean implements LoaderService {

    private final EmployeeRepository employeeRepository;

    /**
     *
     */
    @Override
    public void generateData() {
        List<Employee> employees = createListEmployees();
        employeeRepository.saveAll(employees);
    }

    /**
     * @return
     */
    @Override
    public long count() {
        return employeeRepository.count();
    }

    public List<Employee> createListEmployees() {

        List<Employee> employees = new ArrayList<>();
        long seed = 1;

        Faker faker = new Faker(new Locale("en"), new Random(seed));
        for (int i = 0; i < 100_000; i++) {

            String name = faker.name().name();
            //String country = faker.country().name();
            String country = i % 30 == 0 ? "Ukraine" : faker.country().name();
            String email = faker.name().name();

            Set<Address> addresses = Set.copyOf(
                    Arrays.asList(
                            Address.builder()
                                    .country(faker.address().country())
                                    .city(faker.address().city())
                                    .street(faker.address().streetAddress())
                                    .addressHasActive(Boolean.valueOf(faker.address().streetAddress(false)))
                                    .build(),
                            Address.builder()
                                    .country(faker.address().country())
                                    .city(faker.address().city())
                                    .street(faker.address().streetAddress())
                                    .addressHasActive(Boolean.valueOf(faker.address().streetAddress(true)))
                                    .build()));


            Document document = Document.builder()
                    .number(faker.code().ean8())
                    .expireDate(faker.date().past(365, DAYS)
                            .toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime())
                    .build();

            Employee employee = Employee.builder()
                    .name(name)
                    .country(country)
                    .email(email.toLowerCase().replaceAll(" ", "") + "@mail.com")
                    .addresses(addresses)
                    .document(document)
                    .isDeleted(Boolean.FALSE)
                    .build();

            employees.add(employee);
        }
        return employees;
    }
}
