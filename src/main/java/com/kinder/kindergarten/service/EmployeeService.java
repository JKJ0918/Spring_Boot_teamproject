package com.kinder.kindergarten.service;


import com.kinder.kindergarten.entity.Employee;
import com.kinder.kindergarten.exception.OutOfStockException;
import com.kinder.kindergarten.repository.Employee.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public Employee saveEmployee(Employee employee){
        validateDuplicateEmployee(employee);
        return employeeRepository.save(employee);
    }

    private void validateDuplicateEmployee(Employee employee){
        Employee em = employeeRepository.findByEmail(employee.getEmail());
        if(em != null){
            throw new OutOfStockException("이미 가입된 회원입니다");
        }
    }


    /*public void login(String email, String password, HttpSession session) {
        Employee employee = employeeRepository.findByEmail(email);
        if (employee == null || !passwordEncoder.matches(password, employee.getPassword())) {
            throw new UsernameNotFoundException("이메일 또는 비밀번호를 확인해주세요.");
        }

        EmployeeDTO empsession = new EmployeeDTO();
        empsession.setId(employee.getId());
        empsession.setName(employee.getName());
        empsession.setEmail(employee.getEmail());
        empsession.setPhone(employee.getPhone());
        empsession.setSalary(employee.getSalary());
        empsession.setHireDate(employee.getHireDate());

        session.setAttribute("employee", empsession); // 세션에 저장
    }*/

    @Override
    public UserDetails loadUserByUsername(String email){
        // 이메일 정보를 받아 처리
        Employee employee = employeeRepository.findByEmail(email);

        if(employee == null){ // 값이 없다면 등록된 직원이 아니라는 예외 발생
            throw new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(employee.getEmail())
                .password(employee.getPassword())
                .roles(employee.getDepartment().toString())
                .build();
    }
}
