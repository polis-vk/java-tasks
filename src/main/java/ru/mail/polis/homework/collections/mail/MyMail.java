package ru.mail.polis.homework.collections.mail;

abstract public class MyMail {
    private String talker;
    private String listener;

    public MyMail(String talker, String listener) {
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
