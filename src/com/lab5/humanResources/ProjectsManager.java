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
            employeeQuantity += node.value.size();
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

    private void removeNode(ProjectsNode node) {
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
                if (pos != size) {
                    node = node.next;
                    pos++;
                }
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
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        if(c.size() == 0)
            return false;
        //todo *FIXED* логично написать добавление ручками после нода getNode(index)
        ProjectsNode node = getNode(index);

        for (EmployeeGroup o : c) {
            if(!contains(o)){
                ProjectsNode newNode = new ProjectsNode(o);
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
        ProjectsNode node = head;
        boolean changed = false;
        //todo *FIXED* иди по своим нодам и проверяй есть ли value в c и если есть - удаляй ноду
        while(node != null) {
            ProjectsNode nextNode = node.next;

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
        //todo *FIXED* иди по своим нодам и проверяй есть ли value в c и если нет - удаляй ноду
        ProjectsNode node = head;
        boolean changed = false;
        while(node != null) {
            ProjectsNode nextNode = node.next;

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
        //todo *FIXED* ручками делаешь каждый элемент = null
        ProjectsNode node = head;
        while (node != tail){
            ProjectsNode nextNode = node.next;
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
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        return getNode(index).value;
    }

    @Override
    public EmployeeGroup set(int index, EmployeeGroup element) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        EmployeeGroup employeeGroup = getNode(index).value;
        getNode(index).value = element;

        return employeeGroup;
    }

    @Override
    public void add(int index, EmployeeGroup element) {
        //todo *FIXED* учти, если index = 0
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        ProjectsNode node = getNode(index);
        ProjectsNode newNode = new ProjectsNode(element);

        if(node != head)
            newNode.prev = node.prev;
        newNode.next = node;
        node.prev = newNode;
        size++;
    }

    @Override
    public EmployeeGroup remove(int index) {
        //todo *FIXED* учти особенности удаления при index = 0 или size-1
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        ProjectsNode node = getNode(index);
        EmployeeGroup employeeGroup = node.value;

        removeNode(node);

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

    //todo *FIXED* та же фигня, что и в департаменте
    @Override
    public ListIterator<EmployeeGroup> listIterator(int index) {
        if(index < 0 || index > size - 1)
            throw new IndexOutOfBoundsException();

        ProjectsManager projectsManager = this;
        return new ListIterator<EmployeeGroup>() {
            ProjectsNode node = getNode(index);
            int pos = index;
            ProjectsNode newElement = null;
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
                return size > pos;
            }

            public EmployeeGroup next() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.NEXT;
                        return node.value;
                    default:
                        lastOperation = ListIteratorOperation.NEXT;
                        EmployeeGroup employeeGroup = node.value;
                        if (pos >= size)
                            throw new NoSuchElementException();
                        node = node.next;
                        pos++;

                        return employeeGroup;
                }
            }

            public boolean hasPrevious() {
                return pos > 0;
            }

            public EmployeeGroup previous() {
                switch (lastOperation) {
                    case ADD:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        pos = newElementPos;
                        node = newElement;
                        return node.value;
                    default:
                        lastOperation = ListIteratorOperation.PREVIOUS;
                        EmployeeGroup employeeGroup = node.value;
                        if (pos < 0)
                            throw new NoSuchElementException();

                        node = node.prev;
                        pos--;

                        return employeeGroup;
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
                        projectsManager.removeNode(node.prev);
                        break;
                    case PREVIOUS:
                        projectsManager.removeNode(node.next);
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.REMOVE;
            }

            public void set(EmployeeGroup employeeGroup) {
                switch (lastOperation) {
                    case NEXT:
                        node.prev.value = employeeGroup;
                        break;
                    case PREVIOUS:
                        node.next.value = employeeGroup;
                        break;
                    default:
                        illegalState();
                }
                lastOperation = ListIteratorOperation.SET;
            }

            public void add(EmployeeGroup employeeGroup) {
                if(size == 0) {
                    projectsManager.add(employeeGroup);
                }
                else{
                    switch (lastOperation){
                        case NONE:
                            projectsManager.add(0, employeeGroup);
                            pos++;
                            break;
                        case NEXT:
                            newElementPos = pos - 1;
                            if(pos - 1 < 0)
                                projectsManager.add(0, employeeGroup);
                            else {
                                projectsManager.add(newElementPos, employeeGroup);
                                newElement = node.prev;
                            }
                            pos++;
                            break;
                        case PREVIOUS:
                            newElementPos = pos + 2;
                            if(pos + 2 > size - 1)
                                projectsManager.add(employeeGroup);
                            else{
                                projectsManager.add(newElementPos, employeeGroup);
                                newElement = node.next.next;
                            }
                            break;
                    }
                }
                lastOperation = ListIteratorOperation.ADD;
            }
        };
    }

    //todo *FIXED* та же фигня, что и в департаменте
    @Override
    public List<EmployeeGroup> subList(int fromIndex, int toIndex){
        if(fromIndex < 0 || toIndex > size - 1 || fromIndex > toIndex)
            throw new IndexOutOfBoundsException();
        if(fromIndex == toIndex)
            return new ProjectsManager();

        ProjectsManager subList = new ProjectsManager();

        ProjectsNode node = getNode(fromIndex);

        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(node.value);
            node = node.next;
        }
        subList.size = toIndex - fromIndex;

        return subList;
    }

    //endregion . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .
}
