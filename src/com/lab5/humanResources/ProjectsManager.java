package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.*;

public class ProjectsManager implements GroupsManager {
    private class ProjectsNode {
        ProjectsNode next;
        ProjectsNode prev;
        EmployeeGroup value;
        ProjectsNode(EmployeeGroup value) {
            this.value = value;
        }
    }
    private ProjectsNode head;
    private ProjectsNode tail;
    private int size = 0;
    public ProjectsManager(){ }
    public ProjectsManager(EmployeeGroup[] employeeGroups ) throws AlreadyAddedException{
        for(int i = 0; i < employeeGroups.length; i++)
            add(employeeGroups[i]);
    }

    /*public void add(EmployeeGroup employeeGroup) throws AlreadyAddedException{
        if(hasEmployeeGroup(employeeGroup.getName()))
            throw new AlreadyAddedException("Добавляемая группа уже есть в списке или массиве");

        ProjectsNode newNode = new ProjectsNode(employeeGroup);
        if(head == null){
            head = newNode;
            tail = head;
            size = 1;
        }
        else{
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }*/
    public int groupsQuantity() {
        return size;
    }
    public boolean remove(String name) {
        ProjectsNode node = head;
        ProjectsNode prev = head;
        while(node != null){
            if(node.value.getName().equals(name)) {
                prev.next = node.next;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }
    public int remove(EmployeeGroup employeeGroup) {
        ProjectsNode node = head;
        ProjectsNode prev = head;
        int count = 0;
        while(node != null){
            if(node.value.equals(employeeGroup)) {
                prev.next = node.next;
                size--;
                count++;
            }
            prev = node;
            node = node.next;
        }
        return count;
    }
    public EmployeeGroup getEmployeeGroup(String name){
        ProjectsNode node = head;
        while(node != null){
            if(node.value.getName().equals(name)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }
    public EmployeeGroup[] getEmployeeGroups() {
        EmployeeGroup[] employeeGroups = new EmployeeGroup[size];
        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            employeeGroups[i] = node.value;
            node = node.next;
        }
        return employeeGroups;
    }
    public int employeeQuantity(){
        ProjectsNode node = head;
        int employeeQuantity = 0;
        for (int i = 0; i < size; i++) {
            employeeQuantity += node.value.employeeQuantity();
            node = node.next;
        }
        return employeeQuantity;
    }
    public int employeeQuantity(JobTitlesEnum jobTitle){
        ProjectsNode node = head;
        int employeeQuantity = 0;
        for (int i = 0; i < size; i++) {
            employeeQuantity += node.value.employeesQuantity(jobTitle);
            node = node.next;
        }
        return employeeQuantity;
    }
    public Employee mostValuableEmployee() {
        Employee employee = null;
        ProjectsNode node = head;
        int bestSalary = 0;
        for (int i = 0; i < size; i++) {
            if (node.value.mostValuableEmployee().getSalary() > bestSalary) {
                employee = node.value.mostValuableEmployee();
                bestSalary = employee.getSalary();
            }
            node = node.next;
        }
        return employee;
    }
    public EmployeeGroup getEmployeesDepartment(String firstName, String secondName){
        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            if(node.value.hasEmployee(firstName, secondName))
                return node.value;
            node = node.next;
        }
        return null;
    }

    public boolean hasEmployeeGroup(String name){
        ProjectsNode node = head;
        while(node != null) {
            if(node.value.getName().equals(name))
                return true;
        }
        return false;
    }

    public int partTimeEmployeesQuantity(){
        int partTimeEmployeesQuantity = 0;
        ProjectsNode projectsNode = head;
        while(projectsNode != null) {
            partTimeEmployeesQuantity += projectsNode.value.partTimeEmployeesQuantity();
        }
        return partTimeEmployeesQuantity;
    }

    public int staffEmployeesQuantity(){
        int staffEmployeesQuantity = 0;
        ProjectsNode projectsNode = head;
        while(projectsNode != null) {
            staffEmployeesQuantity += projectsNode.value.staffEmployeesQuantity();
        }
        return staffEmployeesQuantity;
    }

    public int isTravelingQuantity(){
        int isTravelingQuantity = 0;
        ProjectsNode projectsNode = head;
        while(projectsNode != null) {
            isTravelingQuantity += projectsNode.value.isTravelingQuantity();
        }
        return isTravelingQuantity;
    }

    public Employee[] travellers(LocalDate dayStart, LocalDate dayEnd){
        ArrayList<Employee> travellers = new ArrayList<>();
        ProjectsNode projectsNode = head;
        while(projectsNode != null) {
            Collections.addAll(travellers, projectsNode.value.travellers(dayStart, dayEnd));
        }
        return (Employee[])travellers.toArray();
    }


    //region Lab5 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

    private ProjectsNode getNode(int index){
        ProjectsNode node;
        if(index <= size / 2) {
            node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        else {
            node = tail;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }

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
        for (EmployeeGroup employeeGroup: this) {
            if (employeeGroup.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<EmployeeGroup> iterator() {
        return new Iterator<EmployeeGroup>() {
            ProjectsNode node = head;
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public EmployeeGroup next() {
                EmployeeGroup employeeGroup = node.value;
                node = node.next;
                pos++;
                return employeeGroup;
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

        ProjectsNode newNode = new ProjectsNode(employeeGroup);
        if(head == null){
            head = newNode;
            tail = head;
            size = 1;
        }
        else{
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;

            size++;
        }
        return true;
    }

    @Override
    public boolean remove(Object o) {
        ListIterator<EmployeeGroup> listIterator = listIterator();
        while(listIterator.hasNext()) {
            if (listIterator.next().equals(o)) {
                remove(listIterator.previousIndex());
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
        for (EmployeeGroup employeeGroup: c) {
            addAll &= add(employeeGroup);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends EmployeeGroup> c) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        if(c.size() == 0)
            return false;

        for (Object o : c) {
            add(index++, (EmployeeGroup) o);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<EmployeeGroup> iterator = iterator();
        ProjectsNode node = head;
        boolean changed = false;
        while(iterator.hasNext()) {
            EmployeeGroup employeeGroup = iterator.next();

            for (int i = 0; i < size; i++) {
                if (employeeGroup.equals(node.value)) {
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
        ProjectsNode node = head;
        for(int i = 0; i < size; i++) {
            Iterator<?> iterator = c.iterator();
            boolean hasTravel = false;
            while (iterator.hasNext()) {
                if (node.value.equals(iterator.next())) {
                    hasTravel = true;
                    break;
                }
            }
            if (!hasTravel) {
                remove(i);
                changed = true;
            }
            node = node.next;
        }
        return changed;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        ProjectsManager projectsManager = (ProjectsManager) obj;

        if(this.size != projectsManager.size)
            return false;

        int firstHashCode = 0;
        int secondHashCode = 0;

        ProjectsNode node = head;
        for (int i = 0; i < size; i++) {
            firstHashCode += node.value.hashCode();
            node = node.next;
        }

        node = projectsManager.head;
        for (int i = 0; i < size; i++) {
            secondHashCode += node.value.hashCode();
            node = node.next;
        }

        return firstHashCode == secondHashCode;
    }

    @Override
    public int hashCode(){
        return getEmployeeGroups().hashCode()
                ^ size;
    }

    @Override
    public EmployeeGroup get(int index) {
        if(index < 0 || index > size)
            throw new IndexOutOfBoundsException();

        return getNode(index).value;
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        EmployeeGroup employeeGroup = getNode(index).value;
        getNode(index).value = element;

        return employeeGroup;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        ListIterator<EmployeeGroup> listIterator = listIterator(index);

        ProjectsNode node = getNode(index);
        ProjectsNode newNode = new ProjectsNode(element);

        newNode.prev = node.prev;
        node.prev.next = newNode;

        newNode.next = node;
        node.prev = newNode;

        size++;
    }

    @Override
    public EmployeeGroup remove(int index) {
        ProjectsNode node = getNode(index);
        EmployeeGroup employeeGroup = node.value;

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;

        return employeeGroup;
    }

    @Override
    public int indexOf(Object o) {
        ProjectsNode node = head;
        for(int i = 0; i < size; i++){
            if(node.value.equals(o))
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        ProjectsNode node = tail;
        for(int i = size - 1; i > -1; i--){
            if(node.value.equals(o))
                return i;
            node = node.prev;
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

        ProjectsManager projectsManager = this;
        return new ListIterator<EmployeeGroup>() {
            ProjectsNode node = getNode(index);
            int pos = index;

            public boolean hasNext() {
                return size > pos;
            }

            public EmployeeGroup next() {
                EmployeeGroup employeeGroup = node.value;
                node = node.next;
                pos++;
                return employeeGroup;
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public EmployeeGroup previous() {
                EmployeeGroup employeeGroup = node.value;
                node = node.prev;
                pos--;
                return employeeGroup;
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                projectsManager.remove(pos);
            }

            public void set(EmployeeGroup employeeGroup) {
                projectsManager.set(pos, employeeGroup);
            }

            public void add(EmployeeGroup employeeGroup) {
                projectsManager.add(employeeGroup);
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
