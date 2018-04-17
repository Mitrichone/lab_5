package com.lab5.humanResources;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class StaffEmployee extends Employee implements BusinessTraveller {
    private class ListNode {
        ListNode next;
        ListNode prev;
        BusinessTravel value;

        ListNode(BusinessTravel value) {
            this.value = value;
        }
    }

    private int bonus;
    private ListNode head;
    private ListNode tail;
    private int travelsQuantity = 0;

    public StaffEmployee(String firstName, String secondName){
        this(firstName, secondName, DEFAULT_JOB_TITLE, DEFAULT_SALARY);
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int Salary) {
        super(firstName, secondName, jobTitle, Salary);
        bonus = 0;
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int Salary, int bonus) {
        super(firstName, secondName, jobTitle, Salary);
        this.bonus = bonus;
    }

    public StaffEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int Salary,
                         BusinessTravel[] businessTravels) throws IllegalDatesException {
        super(firstName, secondName, jobTitle, Salary);
        bonus = 0;
        for (BusinessTravel businessTravel : businessTravels)
            if (!add(businessTravel))
                throw new IllegalDatesException();
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
/*
    public void addTravel(BusinessTravel businessTravel) throws IllegalDatesException{
        if(daysTravel(businessTravel.getDayStart(),
                businessTravel.getDayEnd()) > 0)
            throw new IllegalDatesException("");

        ListNode newNode = new ListNode(businessTravel);
        if(head == null){
            head = newNode;
            head.prev = head;
            head.next= head;
            tail = head;
            travelsQuantity = 1;
        }
        else{
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            tail = newNode;
            travelsQuantity++;
        }
    }
*/
    public BusinessTravel[] getTravels() {
        if(travelsQuantity == 0)
            return null;
        BusinessTravel[] businessTravels = new BusinessTravel[travelsQuantity];
        ListNode listNode = head;
        for (int i = 0; i < travelsQuantity; i++){
            businessTravels[i] = listNode.value;
            listNode = listNode.next;
        }
        return businessTravels;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("\nКомандировки:\n");
        if(getTravels() != null)
            for (BusinessTravel businessTravel : getTravels()) {
                stringBuilder.append(businessTravel.getDescription());
                stringBuilder.append('\n');
            }
        return stringBuilder.toString();
    }


    public boolean isTraveling() {
        LocalDate now = LocalDate.now();
        ListNode node = head;
        if (node != null)
            do {
                if (node.value.getDayStart().isBefore(now)
                        && node.value.getDayEnd().isAfter(now))
                    return true;
                node = node.next;
            }
            while (node != head);
        return false;
    }
    private int daysBetween(LocalDate start, LocalDate end){
        return (int)ChronoUnit.DAYS.between(start, end);
    }
    public int daysTravel(LocalDate dayStart, LocalDate dayEnd) {
        ListNode node = head;

        int daysTravel = 0;
        int addDays;
        if (node != null)
            do {
                addDays = daysBetween(dayStart, dayEnd)
                        - (daysBetween(dayStart, node.value.getDayStart())
                        + Math.abs(daysBetween(dayStart, node.value.getDayStart()))) / 2
                        - (daysBetween(node.value.getDayEnd(), dayEnd)
                        + Math.abs(daysBetween(node.value.getDayEnd(), dayEnd))) / 2;
                addDays = (addDays + Math.abs(addDays)) / 2;

                daysTravel += addDays;
                node = node.next;
            }
            while (node != head);
        return daysTravel;
    }

    //region Lab5 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
    //  1
    @Override
    public int size() {
        return travelsQuantity;
    }
    //  2
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    //  3
    @Override
    public boolean contains(Object o) {
        for (BusinessTravel businessTravel : this) {
            if (businessTravel.equals(o))
                return true;
        }
        return false;
    }
    //  4
    @Override
    public Iterator<BusinessTravel> iterator() {
        return new Iterator<BusinessTravel>() {
            ListNode listNode = head;
            int pos = 0;

            public boolean hasNext() {
                return travelsQuantity > pos;
            }

            public BusinessTravel next() {
                BusinessTravel businessTravel = listNode.value;
                listNode = listNode.next;
                pos++;
                return businessTravel;
            }
        };
    }
    //  5
    @Override
    public Object[] toArray() {
        return getTravels();
    }
    //  6
    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(getTravels(), size(), a.getClass());
    }
    //  7
    @Override
    public boolean add(BusinessTravel businessTravel){
        if(daysTravel(businessTravel.getDayStart(),
                businessTravel.getDayEnd()) > 0)
            return false;

        ListNode newNode = new ListNode(businessTravel);
        if(head == null){
            head = newNode;
            head.prev = head;
            head.next= head;
            tail = head;
            travelsQuantity = 1;
        }
        else{
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            tail = newNode;
            travelsQuantity++;
        }
        return true;
    }
    //  8
    @Override
    public boolean remove(Object o) {
        ListNode node = head;
        if (node != null)
            do {
                if(node.value.equals(o)) {
                    node.prev.next = node.next;
                    node.next.prev = node.prev;
                    travelsQuantity--;
                    return true;
                }
                node = node.next;
            }
            while (node != head);
        return false;
    }
    //  9
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }
    //  10
    @Override
    public boolean addAll(Collection<? extends BusinessTravel> c) {
        boolean addAll = true;
        for (BusinessTravel businessTravel: c) {
            addAll &= add(businessTravel);
        }
        return addAll;
    }
    //  11
    @Override
    public boolean retainAll(Collection<?> c) {
        Iterator<BusinessTravel> businessTravelIterator = iterator();
        boolean changed = false;
        while(businessTravelIterator.hasNext()) {
            BusinessTravel businessTravel = businessTravelIterator.next();
            Iterator<?> iterator = c.iterator();
            boolean hasTravel = false;

            while (iterator.hasNext()) {
                if (businessTravel.equals(iterator.next())) {
                    hasTravel = true;
                    break;
                }
            }
            if (!hasTravel)
                if(remove(businessTravel))
                    changed = true;
        }
        return changed;
    }
    //  12
    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<BusinessTravel> businessTravelIterator = iterator();
        boolean changed = false;
        while(businessTravelIterator.hasNext()) {
            BusinessTravel businessTravel = businessTravelIterator.next();

            for (Object o : c) {
                if (businessTravel.equals(o)) {
                    if (remove(businessTravel))
                        changed = true;
                    break;
                }
            }
        }
        return changed;
    }
    //  13
    @Override
    public void clear() {
        head = null;
        tail = null;
        travelsQuantity = 0;
    }
    //  14
    @Override
    public boolean equals(Object obj){
        StaffEmployee employee = (StaffEmployee) obj;

        if(this.travelsQuantity != employee.travelsQuantity)
            return false;

        int firstHashCode = 0;
        int secondHashCode = 0;

        ListNode node = head;
        for (int i = 0; i < travelsQuantity; i++) {
            firstHashCode += node.value.hashCode();
            node = node.next;
        }

        node = employee.head;
        for (int i = 0; i < travelsQuantity; i++) {
            secondHashCode += node.value.hashCode();
            node = node.next;
        }

        return super.equals(obj)
                && bonus == employee.bonus
                && firstHashCode == secondHashCode;
    }
    //  15
    @Override
    public int hashCode(){
        return super.hashCode()
                ^ bonus
                ^ travelsQuantity;
    }

//endregion . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
}
