package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.*;

public class DepartmentsManager implements GroupsManager{
    private String name;
    private EmployeeGroup[] employeeGroups;
    private int size;
    private final static int DEFAULT_SIZE = 0;
    private final static int DEFAULT_CAPACITY = 8;

    //region Конструкторы
    public DepartmentsManager(String name){
        this.name = name;
        this.size = DEFAULT_SIZE;
        this.employeeGroups = new EmployeeGroup[DEFAULT_CAPACITY];
    }

    public DepartmentsManager(String name, int size) throws NegativeSizeException{
        if(size < 0)
            throw new NegativeSizeException();
        this.name = name;
        this.size = size;
        this.employeeGroups = new EmployeeGroup[size];
    }

    public DepartmentsManager(String name, EmployeeGroup[] employeeGroups){
        this.name = name;
        this.size = employeeGroups.length;
        System.arraycopy(employeeGroups, 0, this.employeeGroups, 0, employeeGroups.length);
    }
    //endregion

    //region Методы
    /*
    public void add(EmployeeGroup employeeGroup) throws AlreadyAddedException{
        if(hasEmployeeGroup(employeeGroup.getName()))
            throw new AlreadyAddedException("Добавляемая группа уже есть в списке или массиве");

        if (this.size == this.employeeGroups.length){
            Department[] newDepartments = new Department[this.size * 2];
            System.arraycopy(this.employeeGroups, 0, newDepartments, 0, this.size);
            this.employeeGroups = newDepartments;
        }
        this.employeeGroups[this.size] = employeeGroup;
        this.size++;
    }*/
    public boolean remove(String name){
        for(int i = 0; i < this.employeeGroups.length; i++) {
            if (this.employeeGroups[i] != null && this.employeeGroups[i].getName().equals(name)) {
                if(i < this.size - 1)
                    System.arraycopy(this.employeeGroups, i + 1, this.employeeGroups, i, this.size - (i + 1));
                this.employeeGroups[this.size - 1] = null;
                this.size--;
                return true;
            }
        }
        return false;
    }
    public int remove(EmployeeGroup employeeGroup){
        int count = 0;
        for(int i = 0; i < this.employeeGroups.length; i++) {
            if (this.employeeGroups[i] != null && this.employeeGroups[i].equals(employeeGroup)) {
                if(i < this.size - 1)
                    System.arraycopy(this.employeeGroups, i + 1, this.employeeGroups, i, this.size - (i + 1));
                this.employeeGroups[this.size - 1] = null;
                this.size--;
                i--;
                count++;
            }
        }
        return count;
    }
    public EmployeeGroup getEmployeeGroup(String name){
        for (EmployeeGroup d: getEmployeeGroups()) {
            if(d.getName().equals(name)) {
                return d;
            }
        }
        return null;
    }
    public EmployeeGroup[] getEmployeeGroups() {
        EmployeeGroup[] employeeGroups = new EmployeeGroup[size];
        System.arraycopy(this.employeeGroups, 0, employeeGroups, 0, size-1);
        return employeeGroups;
    }
    public int groupsQuantity(){
        return size;
    }
    public int employeeQuantity(){
        int employeesQuantity = 0;
        for(int i = 0; i < size; i++)
            employeesQuantity += this.employeeGroups[i].employeeQuantity();
        return employeesQuantity;
    }
    public Employee mostValuableEmployee(){
        Employee employee = null;
        int bestSalary = 0;
        for (int i = 0; i < size; i++){
            if(employeeGroups[i].mostValuableEmployee() != null && employeeGroups[i].mostValuableEmployee().getSalary()
                    > bestSalary) {
                employee = employeeGroups[i].mostValuableEmployee();
                bestSalary = employee.getSalary();
            }
        }
        return employee;
    }
    public String getName() {
        return name;
    }
    public int employeeQuantity(JobTitlesEnum jobTitle){
        int employeesQuantity = 0;
        for (int i = 0; i < size; i++) {
            employeesQuantity += employeeGroups[i].employeesQuantity(jobTitle);
        }
        return employeesQuantity;
    }
    public EmployeeGroup getEmployeesDepartment(String firstName, String secondName){
        for (int i = 0; i < size; i++) {
            if(employeeGroups[i].hasEmployee(firstName, secondName))
                return employeeGroups[i];
        }
        return null;
    }

    //endregion

    public boolean hasEmployeeGroup(String name){
        for (EmployeeGroup employeeGroup: employeeGroups) {
            if(employeeGroup.getName().equals(name))
                return true;
        }
        return false;
    }

    public int partTimeEmployeesQuantity(){
        int partTimeEmployeesQuantity = 0;
        for (EmployeeGroup employeeGroup: employeeGroups) {
            partTimeEmployeesQuantity += employeeGroup.partTimeEmployeesQuantity();
        }
        return partTimeEmployeesQuantity;
    }

    public int staffEmployeesQuantity(){
        int staffEmployeesQuantity = 0;
        for (EmployeeGroup employeeGroup: employeeGroups) {
            staffEmployeesQuantity += employeeGroup.staffEmployeesQuantity();
        }
        return staffEmployeesQuantity;
    }

    public int isTravelingQuantity(){
        int isTravelingQuantity = 0;
        for (EmployeeGroup employeeGroup: employeeGroups) {
            isTravelingQuantity += employeeGroup.isTravelingQuantity();
        }
        return isTravelingQuantity;
    }

    public Employee[] travellers(LocalDate dayStart, LocalDate dayEnd){
        ArrayList<Employee> travellers = new ArrayList<>();
        for (EmployeeGroup employeeGroup: employeeGroups) {
            Collections.addAll(travellers, employeeGroup.travellers(dayStart, dayEnd));
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
        for (EmployeeGroup employeeGroup: employeeGroups) {
            if (employeeGroup.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        return new Iterator<EmployeeGroup>() {
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public EmployeeGroup next() {
                return employeeGroups[pos++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return getEmployeeGroups();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) Arrays.copyOf(getEmployeeGroups(), size(), a.getClass());
    }

    @Override
    public boolean add(EmployeeGroup employeeGroup){
        if(hasEmployeeGroup(employeeGroup.getName()))
            return false;

        if (this.size == this.employeeGroups.length){
            EmployeeGroup[] newEmployeeGroups = new EmployeeGroup[this.size * 2];
            System.arraycopy(this.employeeGroups, 0, newEmployeeGroups, 0, this.size);
            this.employeeGroups = newEmployeeGroups;
        }
        this.employeeGroups[this.size] = employeeGroup;
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (employeeGroups[i].equals(o)) {
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
    public boolean addAll(Collection<? extends EmployeeGroup> c) {
        boolean addAll = true;

        if(size + c.size() > employeeGroups.length){
            EmployeeGroup[] newEmployeeGroup = new EmployeeGroup[this.size * 2];
            System.arraycopy(this.employeeGroups, 0, newEmployeeGroup, 0, this.size);
            this.employeeGroups = newEmployeeGroup;
        }

        for (EmployeeGroup employeeGroup: c) {
            addAll &= add(employeeGroup);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if(size + c.size() > employeeGroups.length){
            EmployeeGroup[] newEmployeeGroups = new EmployeeGroup[employeeGroups.length * 2];
            System.arraycopy(employeeGroups, 0, newEmployeeGroups, 0, size);
            employeeGroups = newEmployeeGroups;
        }

        if (index < size)
            System.arraycopy(employeeGroups, index, employeeGroups, index + c.size(),
                    employeeGroups.length - (index + c.size()));

        if(c.size() == 0)
            return false;

        for (Object o : c) {
            employeeGroups[index++] = (EmployeeGroup) o;
        }
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<EmployeeGroup> iterator = iterator();
        boolean changed = false;
        while(iterator.hasNext()) {
            EmployeeGroup employeeGroup = iterator.next();

            for (int i = 0; i < size; i++) {
                if (employeeGroup.equals(employeeGroups[i])) {
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
        boolean changed = false;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasTravel = false;
            while (iterator.hasNext()) {
                if (employeeGroups[i].equals(iterator.next())) {
                    hasTravel = true;
                    break;
                }
            }
            if (!hasTravel) {
                remove(i);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        employeeGroups = new EmployeeGroup[employeeGroups.length];
        size = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        DepartmentsManager department = (DepartmentsManager) obj;

        if(this.size != department.size)
            return false;

        int firstHashCode = 0;
        int secondHashCode = 0;

        for (int i = 0; i < size; i++) {
            firstHashCode += this.employeeGroups[i].hashCode();
            secondHashCode += department.employeeGroups[i].hashCode();
        }

        return name.equals(department.getName())
                && size == department.size
                && firstHashCode == secondHashCode;
    }

    @Override
    public int hashCode(){
        return name.hashCode()
                ^ Arrays.hashCode(employeeGroups)
                ^ size;
    }

    @Override
    public EmployeeGroup get(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        return employeeGroups[index];
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        EmployeeGroup employeeGroup = employeeGroups[index];
        employeeGroups[index] = element;

        return employeeGroup;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        if(index < size - 1)
            System.arraycopy(employeeGroups, index, employeeGroups, index + 1, this.size - (index + 1));

        employeeGroups[index] = element;
        if(size < employeeGroups.length)
            size++;
    }

    @Override
    public EmployeeGroup remove(int index) {
        EmployeeGroup employeeGroup = employeeGroups[index];

        if(index < this.size - 1)
            System.arraycopy(this.employeeGroups, index + 1, this.employeeGroups, index, this.size - (index + 1));
        employeeGroups[size - 1] = null;
        size--;

        return employeeGroup;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < size; i++){
            if(employeeGroups[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for(int i = size - 1; i > -1; i--){
            if(employeeGroups[i].equals(o))
                return i;
        }
        return -1;
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<EmployeeGroup> listIterator(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        DepartmentsManager departmentsManager = this;
        return new ListIterator<EmployeeGroup>() {
            int pos = index;

            public boolean hasNext() {
                return employeeGroups.length > pos;
            }

            public EmployeeGroup next() {
                return employeeGroups[pos++];
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public EmployeeGroup previous() {
                return employeeGroups[pos--];
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                departmentsManager.remove(pos);
            }

            public void set(EmployeeGroup employeeGroup) {
                departmentsManager.set(pos, employeeGroup);
            }

            public void add(EmployeeGroup employeeGroup) {
                departmentsManager.add(pos, employeeGroup);
            }
        };
    }

    @Override
    public List<EmployeeGroup> subList(int fromIndex, int toIndex){
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return null;

        List<EmployeeGroup> subList = new ArrayList<>();
        ListIterator<EmployeeGroup> iterator = listIterator(fromIndex);

        while(iterator.previousIndex() < toIndex)
            subList.add(iterator.next());

        return subList;
    }

    //endregion . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
}
