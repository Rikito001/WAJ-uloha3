package sk.ukf.uloha2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.uloha2.dao.EmployeeDAO;
import sk.ukf.uloha2.entity.Employee;
import sk.ukf.uloha2.exception.EmailAlreadyExistsException;
import sk.ukf.uloha2.exception.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee findById(int id) {
        Employee employee = employeeDAO.findById(id);

        if (employee == null) {
            throw new ObjectNotFoundException("Zamestnanec s ID " + id + " nebol nájdený");
        }

        return employee;
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        validateEmail(employee);

        return employeeDAO.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        Employee employee = employeeDAO.findById(id);

        if (employee == null) {
            throw new ObjectNotFoundException("Zamestnanec s ID " + id + " nebol nájdený");
        }

        employeeDAO.deleteById(id);
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeDAO.findByFirstNameAndLastName(firstName, lastName);
    }

    private void validateEmail(Employee employee) {
        if (employee.getId() == 0) {
            if (employeeDAO.existsByEmail(employee.getEmail())) {
                throw new EmailAlreadyExistsException(
                        "Email " + employee.getEmail() + " už existuje v databáze"
                );
            }
        } else {
            Optional<Employee> existingEmployee = employeeDAO.findByEmail(employee.getEmail());

            if (existingEmployee.isPresent() && existingEmployee.get().getId() != employee.getId()) {
                throw new EmailAlreadyExistsException(
                        "Email " + employee.getEmail() + " už používa iný zamestnanec"
                );
            }
        }
    }
}