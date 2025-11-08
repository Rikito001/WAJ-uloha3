package sk.ukf.uloha2.employee.service;

import sk.ukf.uloha2.employee.entity.Employee;
import sk.ukf.uloha2.employee.exception.EmailAlreadyExistsException;
import sk.ukf.uloha2.employee.exception.ObjectNotFoundException;
import sk.ukf.uloha2.employee.repo.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    public Employee getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Zamestnanec s ID " + id + " nebol nájdený"));
    }

    public Employee save(Employee e) {
        if (e.getId() == null) {
            if (repo.existsByEmail(e.getEmail())) {
                throw new EmailAlreadyExistsException("Email " + e.getEmail() + " už existuje");
            }
            e.setId(null);
            return repo.save(e);
        } else {
            if (repo.existsByEmailAndIdNot(e.getEmail(), e.getId())) {
                throw new EmailAlreadyExistsException("Email " + e.getEmail() + " už používa iný zamestnanec");
            }
            return repo.save(e);
        }
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new ObjectNotFoundException("Zamestnanec s ID " + id + " nebol nájdený");
        }
        repo.deleteById(id);
    }
}
