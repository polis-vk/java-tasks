package ru.mail.polis.homework.collections.mail;

abstract public class MailWorker {
    private String talker;
    private String listener;

    MailWorker(String talker, String listener) {
        this.talker = talker;
        this.listener = listener;
    }

    public String getTalker() {
        return talker;
    }

    public String getListener() {
        return listener;
    }
}
