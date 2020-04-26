package ru.mail.polis.homework.collections.mail;

public class Salary<T> extends Message<T> {

    public Salary(String sender, String receiver, T massageContent) {
        super(sender, receiver, massageContent);
    }
}
