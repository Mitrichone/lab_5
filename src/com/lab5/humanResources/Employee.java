package com.lab5.humanResources;

import static com.lab5.humanResources.JobTitlesEnum.NONE;

public abstract class Employee implements Comparable<Employee> {
    String firstName;
    String secondName;
    JobTitlesEnum jobTitle;
    int salary;
    final static JobTitlesEnum DEFAULT_JOB_TITLE = NONE;
    final static int DEFAULT_SALARY = 0;

    //region Конструкторы
    protected Employee(String firstName, String secondName){
        this(firstName, secondName, DEFAULT_JOB_TITLE, DEFAULT_SALARY);
    }
    protected Employee(String firstName, String secondName, JobTitlesEnum jobTitle, int Salary){
        if(Salary < 0)
            throw new IllegalArgumentException();
        this.firstName = firstName;
        this.secondName = secondName;
        this.jobTitle = jobTitle;
        this.salary = Salary;
    }
    //endregion

    //region Методы [get]
    public abstract int getBonus();

    public String getFirstName() {
        return firstName;
    }

    public JobTitlesEnum getJobTitle() {
        return jobTitle;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getSalary() {
        return salary;
    }
    //endregion

    //region Методы [set]
    public abstract void setBonus(int bonus);

    public void setFirstName(String Name)
    {
        this.firstName = Name;
    }

    public void setJobTitle(JobTitlesEnum jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    //endregion

    //region Переопределенные методы
    //region Old toString()
    /*
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("");
        if(secondName != null && !secondName.equals(""))
            stringBuilder.append(secondName);
        if(firstName != null && !firstName.equals(""))
            if(stringBuilder.length() != 0)
                stringBuilder.append(" ")
                        .append(firstName);
            else
                stringBuilder.append(firstName);
        if(jobTitle != null && jobTitle != NONE)
            if(stringBuilder.length() != 0)
                stringBuilder.append(", ")
                        .append(jobTitle);
            else
                stringBuilder.append(jobTitle);
        if(salary != 0)
            if(stringBuilder.length() != 0)
                stringBuilder.append(", ")
                        .append(salary)
                        .append("р.");
            else
                stringBuilder.append(salary)
                        .append("р.");
        return stringBuilder.toString();
    }
    */
    //endregion
    @Override
    public String toString(){
        return String.format("%1$s%2$s%3$s%4$s",
                (secondName != null && !secondName.isEmpty()) ? secondName : "",
                (firstName != null && !firstName.isEmpty()) ? " " + firstName : "",
                (jobTitle != null && jobTitle != NONE) ? " " + jobTitle : "",
                (salary != 0) ? ", " + salary + "р.": "").trim();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Employee employee = (Employee)obj;
        return firstName.equals(employee.getFirstName())
                && secondName.equals(employee.getSecondName())
                && jobTitle == employee.jobTitle
                && salary == employee.salary;
    }

    @Override
    public int hashCode(){
        return firstName.hashCode()
                ^ secondName.hashCode()
                ^ jobTitle.hashCode()
                ^ salary;
    }

    @Override
    public int compareTo(Employee o) {
        return (getSalary() + getBonus()) - (o.getSalary() + o.getBonus());
    }
    //endregion
}
