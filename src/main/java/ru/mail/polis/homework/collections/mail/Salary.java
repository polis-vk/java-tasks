package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail {

    private int salary;

    public Salary(String receiver, String sender, int salary) {
        super(receiver, sender);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }
