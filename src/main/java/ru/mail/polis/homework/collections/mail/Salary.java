package ru.mail.polis.homework.collections.mail;

public class Salary extends MailWorker {
    private int salary;

    public Salary(String talker, String listener, int salary) {
        super(talker, listener);

        this.salary = salary;
    }
}
