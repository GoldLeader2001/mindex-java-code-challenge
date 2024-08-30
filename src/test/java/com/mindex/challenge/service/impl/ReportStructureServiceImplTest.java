package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportStructureServiceImplTest {

    private String employeeUrl;
    private String readReportingStructureUrl;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Employee testEmployee;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        readReportingStructureUrl = "http://localhost:" + port + "/repstruct/{id}";

        testEmployee = employeeService.read("16a596ae-edd3-4847-99fe-c4518e82c86f");
    }

    @After
    public void teardown() {
        employeeUrl = null;
        readReportingStructureUrl = null;

        testEmployee = null;
    }

    @Test
    public void testRead() {
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 4);

        // Read checks
        ResponseEntity<ReportingStructure> readReportingStructureResponse = restTemplate.getForEntity(readReportingStructureUrl, ReportingStructure.class, testEmployee.getEmployeeId());
        assertEquals(HttpStatus.OK, readReportingStructureResponse.getStatusCode());
        ReportingStructure readReportingStructure = (ReportingStructure)readReportingStructureResponse.getBody();
        assertNotNull(readReportingStructure);
        assertEquals(readReportingStructure.getNumberOfReports(), testReportingStructure.getNumberOfReports());
        assertEmployeeEquivalence(readReportingStructure.getEmployee(), testReportingStructure.getEmployee());
    }

    private static void assertReportStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
    }
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}