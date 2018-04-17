package com.lab5.humanResources;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BusinessTravel{
    private final String destination;
    private final LocalDate dayStart;
    private final LocalDate dayEnd;
    private final int compensation;
    private final String description;

    //region Конструкторы
    public BusinessTravel(){
        destination = null;
        dayStart = LocalDate.now();
        dayEnd = dayStart.plusDays(1);
        compensation = 0;
        description = null;
    }
    public BusinessTravel(String destination, int daysCount, int compensation, String description){
        this.destination = destination;
        dayStart = LocalDate.now();
        dayEnd = dayStart.plusDays(daysCount);
        this.compensation = compensation;
        this.description = description;
    }
    public BusinessTravel(String destination, LocalDate dayStart, LocalDate dayEnd, int compensation, String description){

        if((int)ChronoUnit.DAYS.between(dayStart, dayEnd) < 0)
            throw new IllegalArgumentException("Дата начала не может быть позже даты конца");
        if(compensation < 0)
            throw new IllegalArgumentException("Компенсация не может быть равна отрицательному числу");

        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.compensation = compensation;
        this.destination = destination;
        this.description = description;
    }
    //endregion

    //region Методы [get]
    public int getCompensation() {
        return compensation;
    }

    public int getDaysCount() {
        return (int)ChronoUnit.DAYS.between(dayStart, dayEnd);
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }
    //endregion

    //region Переопределенные методы

    //region Old toString()
    /*
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("");
        if(destination != null && !destination.equals(""))
            stringBuilder.append(destination);
        if(getDaysCount() != 0)
            if (stringBuilder.length() != 0)
                stringBuilder.append(" ").append(getDaysCount());
            else
                stringBuilder.append(getDaysCount());
        if(compensation != 0)
            if(stringBuilder.length() != 0)
                stringBuilder.append(" (").append(compensation).append(")");
            else
                stringBuilder.append("(").append(compensation).append(")");
        if(description != null && !description.equals(""))
            if(stringBuilder.length() != 0)
                stringBuilder.append(". ").append(description);
            else
                stringBuilder.append(description);
        return stringBuilder.toString();
    }*/
    //endregion

    public String toString(){
        return String.format("%1$s%2$s%3$s%4$s",
                (destination != null && !destination.isEmpty()) ? destination : "",
                (getDaysCount() != 0) ? " " + getDaysCount() : "",
                (compensation != 0) ? " (" + compensation + ")" : "",
                (description != null && !description.isEmpty()) ? ". " + description : "").trim();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        BusinessTravel businessTravel = (BusinessTravel)obj;
        return description.equals(businessTravel.getDescription())
                && destination.equals(businessTravel.getDestination())
                && compensation == businessTravel.compensation
                && dayStart.equals(businessTravel.dayStart)
                && dayEnd.equals(businessTravel.dayEnd);
    }

    @Override
    public int hashCode(){
        return destination.hashCode()
                ^ dayStart.hashCode()
                ^ dayEnd.hashCode()
                ^ compensation
                ^ description.hashCode();
    }
    //endregion

    public LocalDate getDayStart() {
        return dayStart;
    }

    public LocalDate getDayEnd() {
        return dayEnd;
    }
}
