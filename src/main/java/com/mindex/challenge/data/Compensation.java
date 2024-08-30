package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;
import java.util.UUID;

public class Compensation {

    @Id
    private String compId;
    private Employee employee;
    private String salary;
    private Instant effectiveDate;

    //For Testing
    public Compensation() {
    }

    public Compensation(Employee employee, String salary, Instant effectiveDate) {
        this.compId = UUID.randomUUID().toString();
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
