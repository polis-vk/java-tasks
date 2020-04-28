package ru.mail.polis.homework.collections.mail;

public class Salary extends Mail<Integer> { //Т - Integer
    public Salary(String sender, String recipient, int salary) {
        super(salary, recipient, sender);
    }
}
