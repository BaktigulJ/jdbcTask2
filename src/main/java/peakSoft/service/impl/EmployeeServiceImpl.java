package peakSoft.service.impl;

import peakSoft.dao.impl.EmployeeDaoImpl;
import peakSoft.model.Employee;
import peakSoft.model.Job;
import peakSoft.service.EmployeeService;

import java.util.List;
import java.util.Map;

public class EmployeeServiceImpl implements EmployeeService {
 private final EmployeeDaoImpl employeeDaoImpl= new EmployeeDaoImpl();
    @Override
    public void createEmployee() {
        employeeDaoImpl.createEmployee();
    }

    @Override
    public void addEmployee(Employee employee) {
employeeDaoImpl.addEmployee(employee);
    }

    @Override
    public void dropTable() {
employeeDaoImpl.dropTable();
    }

    @Override
    public void cleanTable() {
employeeDaoImpl.cleanTable();
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
employeeDaoImpl.updateEmployee(id,employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDaoImpl.getAllEmployees();
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeDaoImpl.findByEmail(email);
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        return employeeDaoImpl.getEmployeeById(employeeId);
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        return employeeDaoImpl.getEmployeeByPosition(position);
    }
}
