package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationServiceImpl(EmployeeRepository employeeRepository, CompensationRepository compensationRepository) {
        this.employeeRepository = employeeRepository;
        this.compensationRepository = compensationRepository;
    }

    @Override
    public Compensation createCompensation(final Compensation compensation) {
        LOG.debug("Creating Compensation for Employee with id [{}]", compensation.getEmployee().getEmployeeId());

        final Employee employee = employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId());
        if (employee == null) {
            LOG.error("Failed to create compensation. No employee found with id [{}]", compensation.getEmployee().getEmployeeId());
            throw new IllegalArgumentException("Invalid employeeId: " + compensation.getEmployee().getEmployeeId());
        }

        compensation.setEmployee(employee);
        compensationRepository.save(compensation);
        LOG.info("Compensation created successfully for Employee with id [{}]", employee.getEmployeeId());
        return compensation;
    }

    @Override
    public Compensation readCompensation(final String id) {
        LOG.debug("Reading Compensation for Employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        Compensation compensation = compensationRepository.findByEmployee_EmployeeId(id);
        if (compensation == null) {
            LOG.warn("No compensation found for Employee with id [{}]", id);
            throw new RuntimeException("No Compensation found for Employee: " + id);
        } else {
            LOG.info("Compensation read successfully for Employee with id [{}]", id);
        }
        return compensation;
    }
}