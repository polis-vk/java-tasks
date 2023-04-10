package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Salary extends Mail {

    private final int salary;

    public Salary(String receiver, String sender, int salary) {
        super(receiver, sender);
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSalary: " + salary + "\n";
    }
}
