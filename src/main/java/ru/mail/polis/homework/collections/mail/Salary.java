package ru.mail.polis.homework.collections.mail;

public class Salary extends Envelop<Integer> {
    public Salary(Client sender, Client recipient, Integer content) {
        super(sender, recipient, content);
    }
}
