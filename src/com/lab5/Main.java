package com.lab5;

import com.lab5.humanResources.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalDatesException, AlreadyAddedException {
        //todo *FIXED* Эпичный тестовый класс
        Department department1 = new Department("A");
        Department department2 = new Department("B");
        Department department3 = new Department("C");
        Department department4 = new Department("D");
        DepartmentsManager departmentsManager = new DepartmentsManager("DepManager", new Department[]{department1});
        departmentsManager.add(department2);
        departmentsManager.add(0, department3);
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println();


        departmentsManager.remove(0);
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println();

        departmentsManager.set(1, department3);
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println();

        departmentsManager.addAll(new DepartmentsManager("DepManager", new Department[]{department1, department4}));
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println("");

        departmentsManager.retainAll(new DepartmentsManager("DepManager", new Department[]{department1, department2, department4}));
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println("");

        departmentsManager.addAll(new DepartmentsManager("DepManager", new Department[]{department1, department2, department3, department4}));
        for (int i = 0; i < departmentsManager.size(); i++)
            System.out.print(departmentsManager.get(i).getName());
        System.out.println("");

        List<EmployeeGroup> list = departmentsManager.subList(2, 4);
        for (int i = 0; i < list.size(); i++)
            System.out.print(list.get(i).getName());
        System.out.println("");

    }
}