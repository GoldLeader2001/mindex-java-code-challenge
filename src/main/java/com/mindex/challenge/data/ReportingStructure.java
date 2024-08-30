package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee employee;
    private Integer numberOfReports;

    public ReportingStructure() {
    }

    public ReportingStructure(Employee employee, Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
