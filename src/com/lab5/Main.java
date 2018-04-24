package com.lab5;

import com.lab5.humanResources.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) throws IllegalDatesException, AlreadyAddedException {
        Department department = new Department("Department");
        department.add(new StaffEmployee("Андрей", "Андреев",JobTitlesEnum.ADMINISTRATOR, 100, 100));
        department.add(new StaffEmployee("Дмитрий", "Дмитриев",JobTitlesEnum.ADMINISTRATOR, 99, 200));
        Employee[] emp = department.sortedEmployees();
        System.out.println();
        //todo Эпичный тестовый класс
    }
}