package ru.mail.polis.homework.collections.mail;

public class Client {
    private String name;
    private int mailsCount;
    private int mailsSent;

    Client() {
        mailsCount = 0;
        mailsSent = 0;
    }

    Client(final String name) {
        this.name = name;
        mailsCount = 0;
        mailsSent = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMailsCount() {
        return mailsCount;
    }

    public void incrementMailsCount() {
        ++this.mailsCount;
    }

    public int getMailsSent() {
        return mailsSent;
    }

    public void incrementMailsSent() {
        ++this.mailsSent;
    }
}
