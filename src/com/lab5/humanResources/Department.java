package com.lab5.humanResources;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;

public class Department implements EmployeeGroup {
    private String name;
    private Employee[] employees;
    private int size;
    private final static int DEFAULT_SIZE = 0;
    private final static int DEFAULT_CAPACITY = 8;

    //region Конструкторы
    public Department(String name){
        this(name, DEFAULT_CAPACITY);
    }
    public Department(String name, int size) throws NegativeSizeException{
        if(size < 0)
            throw new NegativeSizeException();
        this.name = name;
        this.size = 0;
        this.employees = new Employee[size];
    }
    public Department(String name, Employee[] employees){
        this.name = name;
        this.size = employees.length;
        this.employees = employees;
    }
    //endregion

    //region Методы
    /*
    public void add(Employee employee) throws AlreadyAddedException{
        if(hasEmployee(employee.firstName, employee.secondName))
            throw new  AlreadyAddedException("Добавляемый сотрудник уже есть в списке или массиве");

        if (this.size == this.employees.length){
            Employee[] newEmployees = new Employee[this.size * 2];
            System.arraycopy(this.employees, 0, newEmployees, 0, this.size);
            this.employees = newEmployees;
        }
        this.employees[this.size] = employee;
        this.size++;
    }
*/
    public boolean remove(String firstName, String secondName){
        for(int i = 0; i < this.employees.length; i++) {
            if (this.employees[i] != null
                    && this.employees[i].getFirstName().equals(firstName)
                    && this.employees[i].getSecondName().equals(secondName) ) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public int employeeQuantity(){
        return size;
    }

    public Employee[] getEmployees(){
        Employee[] r = new Employee[size];
        System.arraycopy(this.employees, 0, r, 0, size);
        return r;
    }

    public Employee[] getEmployees(String jobTitle){
        int employeesCounter = 0;
        for (Employee e: this.employees) {
            if(e.getJobTitle() != null && e.getJobTitle().equals(jobTitle))
                employeesCounter++;
        }
        Employee[] employees = new Employee[employeesCounter];
        employeesCounter = 0;
        for (Employee e: this.employees) {
            if(e.getJobTitle() != null && e.getJobTitle().equals(jobTitle))
                employees[employeesCounter++] = e;
        }
        return employees;
    }

    public Employee mostValuableEmployee(){
        Employee mostValuableEmployee = null;
        int bestSalary = 0;
        for(int i = 0; i < size; i++) {
            if (employees[i] != null && employees[i].getSalary() > bestSalary){
                mostValuableEmployee = employees[i];
            }
        }
        return mostValuableEmployee;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int removeAll(JobTitlesEnum jobTitlesEnum){
        int countEmployees = 0;
        for(int i = 0; i < this.employees.length; i++) {
            if(this.employees[i].getJobTitle() == jobTitlesEnum) {
                remove(i);
                countEmployees++;
            }
        }
        return countEmployees;
    }

    public Employee getEmployee(String firstName, String secondName) {
        for (int i = 0; i < this.employees.length; i++) {
            if (this.employees[i].getFirstName().equals(firstName) &&
                    this.employees[i].getSecondName().equals(secondName))
                return this.employees[i];
        }
        return null;
    }

    public JobTitlesEnum[] jobTitles(){
        int countJobTitles = 0;
        for (int i = 0; i < this.employees.length; i++) {
            for (int j = i + 1; j < this.employees.length; j++) {
                if(this.employees[i].getJobTitle() == this.employees[j].getJobTitle())
                    break;
            }
            countJobTitles++;
        }
        JobTitlesEnum[] jobTitlesEnums = new JobTitlesEnum[countJobTitles];
        countJobTitles = 0;
        for (int i = 0; i < this.employees.length; i++) {
            for (int j = i + 1; j < this.employees.length; j++) {
                if(this.employees[i].getJobTitle() == this.employees[j].getJobTitle())
                    break;
            }
            jobTitlesEnums[countJobTitles] = this.employees[i].getJobTitle();
            countJobTitles++;
        }
        return null;
    }

    public Employee[] businessTravellers() {
        int countEmployees = 0;
        for (int i = 0; i < this.employees.length; i++) {
            StaffEmployee staffEmployee = (StaffEmployee) employees[i];
            if (staffEmployee.getTravels() != null)
                countEmployees++;
        }
        Employee[] employees = new Employee[countEmployees];
        for (int i = 0; i < this.employees.length; i++) {
            StaffEmployee staffEmployee = (StaffEmployee) employees[i];
            if (staffEmployee.getTravels() != null) {
                employees[countEmployees] = this.employees[i];
                countEmployees++;
            }
        }
        return employees;
    }
    public int employeesQuantity(JobTitlesEnum jobTitle){
        int employeesCount = 0;
        for(int i = 0; i < size; i++){
            if(employees[i].getJobTitle().equals(jobTitle))
                employeesCount++;
        }
        return employeesCount;
    }

    public boolean hasEmployee(String firstName, String secondName){
        for(int i = 0; i < size; i++) {
            if (employees[i].getFirstName().equals(firstName) &&
                    employees[i].getSecondName().equals(secondName))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Department ");
        stringBuilder.append(name)
                .append(":\n");
        for(int i = 0; i < size; i++){
            stringBuilder.append(this.employees[i].secondName)
                    .append(' ')
                    .append(this.employees[i].firstName)
                    .append('\n');
        }
        return stringBuilder.toString();
    }

    //endregion

    public int partTimeEmployeesQuantity(){
        int partTimeEmployeesQuantity = 0;
        for (Employee employee: employees) {
            if(employee instanceof PartTimeEmployee)
                partTimeEmployeesQuantity++;
        }
        return partTimeEmployeesQuantity;
    }

    public int staffEmployeesQuantity(){
        int staffEmployeesQuantity = 0;
        for (Employee employee: employees) {
            if(employee instanceof StaffEmployee)
                staffEmployeesQuantity++;
        }
        return staffEmployeesQuantity;
    }

    public int isTravelingQuantity(){
        int isTravelingQuantity = 0;
        for (Employee employee: employees) {
            if(employee instanceof StaffEmployee)
                if(((StaffEmployee) employee).isTraveling())
                    isTravelingQuantity++;
        }
        return isTravelingQuantity;
    }

    public Employee[] travellers(LocalDate dayStart, LocalDate dayEnd){
        List<Employee> travellers = new ArrayList<>();
        for (Employee employee: employees) {
            if(employee instanceof StaffEmployee)
                if(((StaffEmployee) employee).daysTravel(dayStart, dayEnd) > 0)
                    travellers.add(employee);
        }
        return (Employee[])travellers.toArray();
    }

    //region Lab5 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (Employee employee: employees) {
            if (employee.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return new Iterator<Employee>() {
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public Employee next() {
                return employees[pos++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return getEmployees();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(getEmployees(), size(), a.getClass());
    }

    @Override
    public boolean add(Employee employee){
        if(hasEmployee(employee.firstName, employee.secondName))
            return false;

        if (this.size == this.employees.length){
            Employee[] newEmployees = new Employee[this.size * 2];
            System.arraycopy(this.employees, 0, newEmployees, 0, this.size);
            this.employees = newEmployees;
        }
        this.employees[this.size] = employee;
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (employees[i].equals(o)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Employee> c) {
        boolean addAll = false;

        if(size + c.size() > employees.length){
            Employee[] newEmployees = new Employee[this.size * 2 + c.size()];
            System.arraycopy(this.employees, 0, newEmployees, 0, this.size);
            this.employees = newEmployees;
        }

        for (Employee employee: c) {
            addAll |= add(employee);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if(size + c.size() > employees.length){
            Employee[] newEmployees = new Employee[employees.length * 2 + c.size()];
            System.arraycopy(employees, 0, newEmployees, 0, size);
            this.employees = newEmployees;
        }

        if (index < size)
            System.arraycopy(employees, index, employees, index + c.size(),
                    employees.length - (index + c.size()));

        if(c.size() == 0)
            return false;

        for (Employee o : c) {
            //Todo проверить сначала наличие сотрудника
            employees[index++] = o;
            size++;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //todo foreach по c
        Iterator<Employee> iterator = iterator();
        boolean changed = false;
        while(iterator.hasNext()) {
            Employee employee = iterator.next();

            for (int i = 0; i < size; i++) {
                if (employee.equals(employees[i])) {
                    remove(i);
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //todo foreach по c
        boolean changed = false;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasEmployee = false;
            while (iterator.hasNext()) {
                if (employees[i].equals(iterator.next())) {
                    hasEmployee = true;
                    break;
                }
            }
            if (!hasEmployee) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        //employees = new Employee[employees.length];
        //todo ручками делаешь каждый элемент = null
        //Arrays.fill(employees, null);
        size = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Department department = (Department) obj;

        if(this.size != department.size)
            return false;

        int firstHashCode = 0;
        int secondHashCode = 0;

        for (int i = 0; i < size; i++) {
            firstHashCode += this.employees[i].hashCode();
            secondHashCode += department.employees[i].hashCode();
        }

        return name.equals(department.getName())
                && size == department.size
                && firstHashCode == secondHashCode;
    }

    @Override
    public int hashCode(){
        return name.hashCode()
                ^ Arrays.hashCode(employees)
                ^ size;
    }

    @Override
    public Employee get(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        return employees[index];
    }

    @Override
    public Employee set(int index, Employee element) {
        Employee employee = employees[index];
        employees[index] = element;

        return employee;
    }

    @Override
    public void add(int index, Employee element) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        //todo расширь массив и вообще операцию expand вынеси в отдельный приватный метод
        if(index < size - 1)
            System.arraycopy(this.employees, index, this.employees, index + 1, this.size - (index + 1));

        employees[index] = element;
        if(size < employees.length)
            size++;
    }

    @Override
    public Employee remove(int index) {
        Employee employee = employees[index];

        if(index < this.size - 1)
            System.arraycopy(this.employees, index + 1, this.employees, index, this.size - (index + 1));
        employees[size - 1] = null;
        size--;

        return employee;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(employees[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(employees[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        Department department = this;
        return new ListIterator<Employee>() {
            int pos = index;

            public boolean hasNext() {
                return employees.length > pos;
            }

            public Employee next() {
                return employees[pos++];
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public Employee previous() {
                return employees[pos--];
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }
            //todo должен соответсвовать контракту
            public void remove() {
                department.remove(pos);
            }

            public void set(Employee employee) {
                department.set(pos, employee);
            }

            public void add(Employee employee) {
                department.add(pos, employee);
            }
        };
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex){
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return null; //todo  пустой департамент
        /*
        Department dep = new Department(this.name, toIndex - fromIndex);
        dep.employees;
        dep.size;
        */

        List<Employee> subList = new ArrayList<>(); //todo департамент но с заданными эмплоями
        ListIterator<Employee> iterator = listIterator(fromIndex);

        while(iterator.previousIndex() < toIndex)
            subList.add(iterator.next());

        return subList;
    }

    //endregion . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
}
