package com.lab5.humanResources;

import static com.lab5.humanResources.JobTitlesEnum.NONE;

public class PartTimeEmployee extends Employee {
    protected PartTimeEmployee(String firstName, String secondName){
        this(firstName, secondName, DEFAULT_JOB_TITLE, DEFAULT_SALARY);
    }
    protected PartTimeEmployee(String firstName, String secondName, JobTitlesEnum jobTitle, int Salary){
        super(firstName, secondName, jobTitle, Salary);
    }

    @Override
    public void setBonus(int bonus) { }

    @Override
    public int getBonus() {
        return 0;
    }

    //region Old toString()
    /*
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        if(jobTitle != null && jobTitle != NONE)
            stringBuilder.insert(stringBuilder.indexOf(jobTitle.toString()) + jobTitle.toString().length(),
                    " (внешний совместитель) ");
        return stringBuilder.toString();
    }
    */
    //endregion

    @Override
    public String toString(){
        return String.format("%1$s%2$s%3$s%4$s",
                (secondName != null && !secondName.isEmpty()) ? secondName : "",
                (firstName != null && !firstName.isEmpty()) ? " " + firstName : "",
                (jobTitle != null && jobTitle != NONE) ? " " + jobTitle + " (внешний совместитель)" : "",
                (salary != 0) ? ", " + salary + "р.": "").trim();
    }
}
