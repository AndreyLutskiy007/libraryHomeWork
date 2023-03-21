package sky.pro.demolibrary.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import sky.pro.demolibrary.exception.EmployeeAlreadyAddedException;
import sky.pro.demolibrary.exception.EmployeeNotFoundException;
import sky.pro.demolibrary.exception.EmployeeStorageIsFullException;
import sky.pro.demolibrary.exception.WrongEmployeeDataException;
import sky.pro.demolibrary.people.Employee;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final int MAX_EMPLOYEES_COUNT = 2;

    private final List<Employee> employees = new ArrayList<>();

    public Employee add(String firstName, String lastName) {
        checkFullName(firstName, lastName);

        if (employees.size() == MAX_EMPLOYEES_COUNT) {
            throw new EmployeeStorageIsFullException("Массив сотрудников переполнен");
        }

        Employee employee = new Employee(firstName, lastName);

        if (employees.contains(employee)) {
            throw new EmployeeAlreadyAddedException("В массиве уже есть такой сотрудник");
        }

        employees.add(employee);


        return employee;
    }

    public Employee find(String firstName, String lastName) {
        checkFullName(firstName, lastName);
        Employee employee = null;

        for (Employee e : employees) {
            if (e != null && firstName.equals(e.getFirstName()) && lastName.equals(e.getLastName())) {
                employee = e;
            }
        }

        if (employee == null) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }

        return employee;
    }

    public Employee remove(String firstName, String lastName) {
        checkFullName(firstName, lastName);
        Employee employee = find(firstName, lastName);

        for (Employee e : employees) {
            if (e.equals(employee)) {
                return e;
            }
        }

        return employee;
    }

    public List<Employee> getAll() {
        return employees;
    }

    private void checkFullName(String firstName, String lastName) {
        checkMistakeFullName(firstName);
        checkMistakeFullName(lastName);
    }

    private void checkMistakeFullName(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new WrongEmployeeDataException("Не допустимое значение! Имя или Фамилия не могут быть пустой строкой");
        }
        if (!username.equals(StringUtils.capitalize(StringUtils.lowerCase(username)))) {
            throw new WrongEmployeeDataException("Не допустимое значение! Имя и Фамилия должны начинаться с заглавной буквы");
        }
        if (!StringUtils.isAlpha(username)) {
            throw new WrongEmployeeDataException("Не допустимое значение! Имя и Фамилия должны содержать только буквы ");
        }

    }

}
