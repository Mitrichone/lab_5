package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.Set;

public interface BusinessTraveller extends Set<BusinessTravel> {
    //void addTravel(BusinessTravel businessTravel) throws IllegalDatesException;
    BusinessTravel[] getTravels();

    boolean isTraveling(); //возвращающий логическое значение – находится ли сотрудник в данный момент в командировке
    int daysTravel(LocalDate dayStart, LocalDate dayEnd); //Проверяющий, находился ли сотрудник в заданный период времени
    //(принимает параметры – даты начала и конца проверяемого периода времени).
    //Метод возвращает число дней из заданного периода в течение которых
    //сотрудник находился в командировке

}
