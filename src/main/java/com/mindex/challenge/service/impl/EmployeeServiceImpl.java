package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Reading employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        final List<Employee> directReports = employee.getDirectReports() != null
                ? employee.getDirectReports().stream()
                .map(emp -> read(emp.getEmployeeId()))
                .collect(Collectors.toList())
                : new ArrayList<>();

        employee.setDirectReports(directReports);

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee with id [{}]", employee);

        Employee emp = employeeRepository.findByEmployeeId(employee.getEmployeeId());

        if (emp == null) {
            throw new RuntimeException("Invalid employeeId: " + employee.getEmployeeId());
        }

        employee.setEmployeeId(emp.getEmployeeId());

        return employeeRepository.save(employee);
    }
}
