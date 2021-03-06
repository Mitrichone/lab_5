package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.*;

public interface EmployeeGroup extends List<Employee>{
    //void add(Employee employee) throws AlreadyAddedException;
    void setName(String name);
    String getName();
    default Employee[] sortedEmployees(){
        Employee[] sortedEmployees = (Employee[]) toArray();
        Arrays.sort(sortedEmployees, Collections.reverseOrder());
        return sortedEmployees;
    }
    Employee mostValuableEmployee();
    Employee getEmployee(String firstName, String secondName);
    boolean remove(String firstName, String secondName);
    int employeesQuantity(JobTitlesEnum jobTitle);
    boolean hasEmployee(String firstName, String secondName);

    int partTimeEmployeesQuantity(); //Возвращающий число совместителей
    int staffEmployeesQuantity(); //Возвращающий число штатных сотрудников
    int isTravelingQuantity(); //Возвращающий число сотрудников, находящихся в командировке в данный момент
    Employee[] travellers(LocalDate dayStart, LocalDate dayEnd); //Возвращающий массив сотрудников, находящихся в
                                                           //командировке в заданный период времени
}
