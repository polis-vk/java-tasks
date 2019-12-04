package ru.mail.polis.homework.collections.mail;

abstract class MailWorker {
    private String talker;
    private String listener;

    MailWorker(String talker, String listener) {
        this.talker = talker;
        this.listener = listener;
    }

    String getTalker() {
        return talker;
    }

    String getListener() {
        return listener;
    }
}
