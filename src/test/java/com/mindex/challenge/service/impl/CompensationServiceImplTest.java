package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String createCompensationUrl;
    private String readCompensationUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee testEmployeeOne;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        createCompensationUrl = "http://localhost:" + port + "/compensation";
        readCompensationUrl = "http://localhost:" + port + "/compensation/{id}";

        testEmployeeOne = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @After
    public void teardown() {
        employeeUrl = null;
        createCompensationUrl = null;
        readCompensationUrl = null;

        testEmployeeOne = null;
    }

    @Test
    public void testCreateRead() {
// Create a Compensation object
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployeeOne);
        testCompensation.setSalary("100000");
        testCompensation.setEffectiveDate(Instant.now());

        // Send POST request to create compensation
        ResponseEntity<Compensation> createResponse = restTemplate.postForEntity(createCompensationUrl, testCompensation, Compensation.class);
        assertNotNull(createResponse);
        assertEquals(HttpStatus.OK, createResponse.getStatusCode());

        // Assert values are equivalent (Skipping compId)
        Compensation createdCompensation = createResponse.getBody();
        assertNotNull(createdCompensation);
        assertEquals(testEmployeeOne.getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
        assertEquals(testCompensation.getSalary(), createdCompensation.getSalary());
        assertEquals(testCompensation.getEffectiveDate(), createdCompensation.getEffectiveDate());

        // Send GET request to read the compensation
        ResponseEntity<Compensation> readResponse = restTemplate.getForEntity(readCompensationUrl, Compensation.class, testEmployeeOne.getEmployeeId());
        assertNotNull(readResponse);
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());

        // Assert Values are correct
        Compensation readCompensation = readResponse.getBody();
        assertNotNull(readCompensation);
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertEquals(createdCompensation.getSalary(), readCompensation.getSalary());
        assertEquals(createdCompensation.getEffectiveDate(), readCompensation.getEffectiveDate());
    }


    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}