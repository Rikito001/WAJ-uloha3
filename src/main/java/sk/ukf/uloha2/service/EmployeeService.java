package sk.ukf.uloha2.service;

import sk.ukf.uloha2.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee save(Employee employee);

    Employee findById(int id);

    void deleteById(int id);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

}
