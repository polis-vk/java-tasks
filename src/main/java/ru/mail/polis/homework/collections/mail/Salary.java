package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail{

    private int salary;

    public Salary(String sender, String recipient, int salary) {
        super(sender, recipient);
        this.salary = salary;
    }

    public int getSalary(){
        return salary;
    }
}
