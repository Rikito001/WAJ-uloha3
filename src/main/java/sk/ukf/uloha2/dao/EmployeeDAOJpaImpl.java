package sk.ukf.uloha2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sk.ukf.uloha2.entity.Employee;

import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDAOJpaImpl implements EmployeeDAO {
    private EntityManager entityManager;

    @Autowired
    public EmployeeDAOJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Employee> findAll() {
        TypedQuery<Employee> query = entityManager.createQuery("from Employee", Employee.class);
        List<Employee> employees = query.getResultList();
        return employees;
    }

    @Override
    public Employee findById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        Employee employee_db = entityManager.merge(employee);
        return employee_db;
    }

    @Override
    public void deleteById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        entityManager.remove(employee);
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstname, String lastname) {
        TypedQuery<Employee> query = entityManager.createQuery(
                "from Employee where firstName=:firstName and lastName=:lastName",
                Employee.class
        );
        query.setParameter("firstName", firstname);
        query.setParameter("lastName", lastname);
        List<Employee> employees = query.getResultList();
        return employees;
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        try {
            TypedQuery<Employee> query = entityManager.createQuery(
                    "from Employee where email=:email",
                    Employee.class
            );
            query.setParameter("email", email);
            Employee employee = query.getSingleResult();
            return Optional.of(employee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
                "select count(e) from Employee e where e.email=:email",
                Long.class
        );
        query.setParameter("email", email);
        Long count = query.getSingleResult();
        return count > 0;
    }
}