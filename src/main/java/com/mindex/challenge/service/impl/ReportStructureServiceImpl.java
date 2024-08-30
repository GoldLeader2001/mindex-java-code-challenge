package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReportStructureServiceImpl implements ReportStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportStructureServiceImpl.class);

    private final EmployeeService employeeService;

    @Autowired
    public ReportStructureServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ReportingStructure generateStructure(String id) {
        LOG.debug("Creating Reporting Structure for Employee with id [{}]", id);

        Employee employee = employeeService.read(id);
        if (employee == null) {
            LOG.error("No employee found with id [{}]", id);
            throw new IllegalArgumentException("Invalid employeeId: " + id);
        }

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));

        LOG.info("Reporting Structure created for Employee with id [{}] with [{}] reports", id, reportingStructure.getNumberOfReports());

        return reportingStructure;
    }

    private int calculateNumberOfReports(Employee employee) {

        // Check if the employee has any direct reports. If not, return 0 as there are no reports.
        if (employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            return 0;
        }

        // Stream through each direct report, retrieve the Employee object,
        // and recursively calculate the number of reports for each one.
        return employee.getDirectReports().stream()
                .map(report -> employeeService.read(report.getEmployeeId()))
                .filter(Objects::nonNull)
                .mapToInt(report -> 1 + calculateNumberOfReports(report))
                .sum();
    }
}