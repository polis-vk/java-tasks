package ru.mail.polis.homework.collections.mail;

/**
 * 1 тугрик
 */
public class Mail<T> {
    String recipient;
    String sender;
    String textMessage;
    int salary;

    Mail(String sender, String recipient, String textMessage) {
        this.recipient = recipient;
        this.sender = sender;
        this.textMessage = textMessage;
    }

    Mail(String sender, String recipient, int salary) {
        this.recipient = recipient;
        this.sender = sender;
        this.salary = salary;
    }
}
