package com.lab5.humanResources;

import java.time.LocalDate;
import java.util.*;

public class Project implements EmployeeGroup{
    private class Node {
        Node next;
        Node prev;
        Employee value;
        Node(Employee value) {
            this.value = value;
        }
    }
    private String name;
    private Node head;
    private Node tail;
    private int size = 0;
    public Project(String name){
        this.name = name;
    }
    public Project(String name, Employee[] employees){
        this.name = name;
        for(int i = 0; i < employees.length; i++)
            add(employees[i]);
    }
/*
    public void add(Employee employee) throws AlreadyAddedException{
        if(hasEmployee(employee.firstName, employee.secondName))
            throw new AlreadyAddedException("Добавляемый сотрудник уже есть в списке или массиве");

        Node newNode = new Node(employee);
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
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getEmployee(String firstName, String secondName) {
        Node node = head;
        while(node != null){
            if(node.value.getFirstName().equals(firstName) && node.value.getSecondName().equals(secondName))
                return node.value;
            node = node.next;
        }
        return null;
    }
    public boolean remove(String firstName, String secondName) {
        Node node = head;
        Node prev = head;
        while(node != null){
            if(node.value.getFirstName().equals(firstName) && node.value.getSecondName().equals(secondName)) {
                prev.next = node.next;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }
    public boolean remove(Employee employee) {
        Node node = head;
        Node prev = head;
        while(node != null){
            if(node.value.equals(employee)) {
                prev.next = node.next;
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }
    public Employee mostValuableEmployee(){
        Node node = head;
        int bestSalary = 0;
        Employee bestEmployee = null;
        while(node != null){
            if(node.value.getSalary() > bestSalary) {
                bestEmployee = node.value;
                bestSalary = bestEmployee.getSalary();
            }
            node = node.next;
        }
        return bestEmployee;
    }

    public int employeeQuantity() {
        return size;
    }

    public Employee[] getEmployees(){
        Employee[] employees = new Employee[size];
        Node node = head;
        for(int i = 0; i < size; i++){
            employees[i] = node.value;
            node = node.next;
        }
        return employees;
    }

    public int employeesQuantity(JobTitlesEnum jobTitlesEnum){
        Node node = head;
        int employeesQuantity = 0;
        while(node != null){
            if(node.value.getJobTitle().equals(jobTitlesEnum))
                employeesQuantity++;
            node = node.next;
        }
        return employeesQuantity;
    }

    public boolean hasEmployee(String firstName, String secondName){
        Node node = head;
        for(int i = 0; i < size; i++) {
            if (node.value.getFirstName().equals(firstName) &&
                    node.value.getSecondName().equals(secondName))
                return true;
            node = node.next;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("Project ");
        stringBuilder.append(name)
                .append(":\n");
        Node node = head;
        for(int i = 0; i < size; i++){
            stringBuilder.append(node.value.toString())
                    .append('\n');
            node = node.next;
        }
        return stringBuilder.toString();
    }

    public int partTimeEmployeesQuantity(){
        int partTimeEmployeesQuantity = 0;
        Node node = head;
        while (node != null){
            if(node.value instanceof PartTimeEmployee)
                partTimeEmployeesQuantity++;
        }
        return partTimeEmployeesQuantity;
    }

    public int staffEmployeesQuantity(){
        int staffEmployeesQuantity = 0;
        Node node = head;
        while (node != null){
            if(node.value instanceof StaffEmployee)
                staffEmployeesQuantity++;
        }
        return staffEmployeesQuantity;
    }

    public int isTravelingQuantity(){
        int isTravelingQuantity = 0;
        Node node = head;
        while (node != null){
            if(node.value instanceof StaffEmployee)
                if(((StaffEmployee) node.value).isTraveling())
                    isTravelingQuantity++;
        }
        return isTravelingQuantity;
    }

    public Employee[] travellers(LocalDate dayStart, LocalDate dayEnd){
        ArrayList<Employee> travellers = new ArrayList<>();
        Node node = head;
        while (node != null){
            if(node.value instanceof StaffEmployee)
                if(((StaffEmployee) node.value).daysTravel(dayStart, dayEnd) > 0)
                    travellers.add(node.value);
        }
        return (Employee[])travellers.toArray();
    }

    //region Lab5 . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

    private Node getNode(int index){
        Node node;
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

    private void removeNode(Node node) {
        if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node.prev = null;
        node.next = null;
        node.value = null;
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
        for (Employee employee: this) {
            if (employee.equals(o))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return new Iterator<Employee>() {
            Node node = head;
            int pos = 0;

            public boolean hasNext() {
                return size > pos;
            }

            public Employee next() {
                Employee employee = node.value;
                if (pos >= size)
                    throw new NoSuchElementException();
                node = node.next;
                pos++;
                return employee;
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

        Node newNode = new Node(employee);
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
        ListIterator<Employee> listIterator = listIterator();
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
    public boolean addAll(Collection<? extends Employee> c) {
        boolean addAll = false;
        for (Employee employee: c) {
            addAll |= add(employee);
        }
        return addAll;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Employee> c) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        if(c.size() == 0)
            return false;

        Node node = getNode(index);

        for (Employee o : c) {
            if(!contains(o)){
                Node newNode = new Node(o);
                if(index > 0) {
                    newNode.prev = node.prev;
                    node.prev.next = newNode;
                }
                else
                    head = newNode;

                newNode.next = node;
                node.prev = newNode;
                size++;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Node node = head;
        boolean changed = false;
        while(node != null) {
            Node nextNode = node.next;

            if(c.contains(node.value)){
                removeNode(node);
                changed = true;
            }
            node = nextNode;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Node node = head;
        boolean changed = false;
        while(node != null) {
            Node nextNode = node.next;

            if(!c.contains(node.value)){
                removeNode(node);
                changed = true;
            }
            node = nextNode;
        }
        return changed;
    }

    @Override
    public void clear() {
        Node node = head;
        while (node != tail){
            Node nextNode = node.next;
            removeNode(node);
            node = nextNode;
        }
        head = null;
        tail = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Project project = (Project) obj;

        if(this.size != project.size)
            return false;

        int firstHashCode = 0;
        int secondHashCode = 0;

        Node node = head;
        for (int i = 0; i < size; i++) {
            firstHashCode += node.value.hashCode();
            node = node.next;
        }

        node = project.head;
        for (int i = 0; i < size; i++) {
            secondHashCode += node.value.hashCode();
            node = node.next;
        }

        return (name == project.name || name.equals(project.getName()))
                && firstHashCode == secondHashCode;
    }

    @Override
    public int hashCode(){
        return name.hashCode()
                ^ getEmployees().hashCode()
                ^ size;
    }

    @Override
    public Employee get(int index) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        return getNode(index).value;
    }

    @Override
    public Employee set(int index, Employee element) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        Employee employee = getNode(index).value;
        getNode(index).value = element;

        return employee;
    }

    @Override
    public void add(int index, Employee element) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        Node node = getNode(index);
        Node newNode = new Node(element);

        if(node != head)
            newNode.prev = node.prev;
        newNode.next = node;
        node.prev = newNode;
        size++;
    }

    @Override
    public Employee remove(int index) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        Node node = getNode(index);
        Employee employee = node.value;

        removeNode(node);

        size--;

        return employee;
    }

    @Override
    public int indexOf(Object o) {
        Node node = head;
        for(int i = 0; i < size; i++){
            if(node.value.equals(o))
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node node = tail;
        for(int i = size - 1; i > -1; i--){
            if(node.value.equals(o))
                return i;
            node = node.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<Employee> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<Employee> listIterator(int index) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        Project project = this;
        return new ListIterator<Employee>() {
            Node node = getNode(index);
            int pos = index;
            Node newElement = null;
            int newElementPos = 0;

            ListIteratorOperation lastOperation = ListIteratorOperation.NONE;

            private void illegalState(){
                switch (lastOperation){
                    case NONE:
                        throw new IllegalStateException("Не были вызваны методы \"next()\" или \"previous()\"");
                    case ADD:
                        throw new IllegalStateException("Последний вызов: \"add()\"");
                    case REMOVE:
                        throw new IllegalStateException("Последний вызов: \"remove()\"");
                }
            }

            public boolean hasNext() {
                return pos < size - 1;
            }

            //todo я перепутал, я знаю кто я =))) next() если нет следующего выбрасывает NoSuchElementException
            public Employee next() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.NEXT;
                        return node.value;
                    default:
                        lastOperation = ListIteratorOperation.NEXT;
                        Employee employee = node.value;
                        if (pos >= size)
                            throw new NoSuchElementException();
                        node = node.next;
                        pos++;
                        return employee;
                }
            }

            public boolean hasPrevious() {
                return pos > 0;
            }
            //todo я перепутал, я знаю кто я =))) previous() если нет предыдущего выбрасывает NoSuchElementException
            public Employee previous() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        pos = newElementPos;
                        node = newElement;
                        return node.value;
                    default:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        Employee employee = node.value;
                        if (pos < 0)
                            throw new NoSuchElementException();
                        node = node.prev;
                        pos--;
                        return employee;
                }
            }

            public int nextIndex() {
                return pos + 1;
            }

            public int previousIndex() {
                return pos - 1;
            }

            public void remove() {
                switch (lastOperation) {
                    case NEXT:
                        pos--;
                        project.removeNode(node.prev);
                        break;
                    case PREVIOUS:
                        project.removeNode(node.next);
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.REMOVE;
            }

            public void set(Employee employee) {
                switch (lastOperation) {
                    case NEXT:
                        node.prev.value = employee;
                        break;
                    case PREVIOUS:
                        node.next.value = employee;
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.SET;
            }

            public void add(Employee employee) {
                if(size == 0) {
                    project.add(employee);
                }
                else{
                    switch (lastOperation){
                        case NONE:
                            project.add(0, employee);
                            pos++;
                            break;
                        case NEXT:
                            newElementPos = pos - 1;
                            if(pos - 1 < 0)
                                project.add(0, employee);
                            else {
                                project.add(newElementPos, employee);
                                newElement = node.prev;
                            }
                            pos++;
                            break;
                        case PREVIOUS:
                            newElementPos = pos + 2;
                            if(pos + 2 > size - 1)
                                project.add(employee);
                            else{
                                project.add(newElementPos, employee);
                                newElement = node.next.next;
                            }
                            break;
                    }
                }
                lastOperation = ListIteratorOperation.ADD;
            }
        };
    }

    @Override
    public List<Employee> subList(int fromIndex, int toIndex){
        if(fromIndex < 0 || toIndex > size - 1 || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return new Project(null);

        Project subList = new Project(this.name);

        Node node = getNode(fromIndex);

        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(node.value);
            node = node.next;
        }
        subList.size = toIndex - fromIndex;

        return subList;
    }

    //endregion . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
}
