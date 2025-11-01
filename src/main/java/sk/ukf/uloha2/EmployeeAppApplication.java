package sk.ukf.uloha2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import sk.ukf.uloha2.model.Employee;
import sk.ukf.uloha2.repository.EmployeeRepository;

@SpringBootApplication
public class EmployeeAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeAppApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(EmployeeRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Employee("Ján", "Novák", "jan.novak@example.com", "Software Engineer", "Plný úväzok"));
                repository.save(new Employee("Eva", "Horváthová", "eva.horvathova@example.com", "Data Analyst", "Čiastočný úväzok"));
            }
        };
    }
}
