package com.kinder.kindergarten.repository.Employee;

import com.kinder.kindergarten.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
