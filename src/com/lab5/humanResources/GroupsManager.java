package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.List;

public interface GroupsManager extends List<EmployeeGroup> {
    //void add(EmployeeGroup employeeGroup) throws AlreadyAddedException;
    int groupsQuantity();
    boolean remove(String name);
    int remove(EmployeeGroup employeeGroup);
    EmployeeGroup getEmployeeGroup(String name);
    EmployeeGroup[] getEmployeeGroups();
    int employeeQuantity();
    int employeeQuantity(JobTitlesEnum jobTitle);
    Employee mostValuableEmployee();
    EmployeeGroup getEmployeesDepartment(String firstName, String secondName);

    boolean hasEmployeeGroup(String name);
    
    int partTimeEmployeesQuantity(); //Возвращающий число совместителей
    int staffEmployeesQuantity(); //Возвращающий число штатных сотрудников
    int isTravelingQuantity(); //Возвращающий число сотрудников, находящихся в командировке в данный момент
    Employee[] travellers(LocalDate dayStart, LocalDate dayEnd); //Возвращающий массив сотрудников, находящихся в командировке в заданный период времени
}
