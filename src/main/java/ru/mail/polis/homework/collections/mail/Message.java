package ru.mail.polis.homework.collections.mail;

abstract class Message {
    private String rec;
    private String sen;

    public Message(String rec, String sen) {
        this.rec = rec;
        this.sen = sen;
    }

    public String getRec() {
        return rec;
    }

    public String getSen() {
        return sen;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public void setSen(String sen) {
        this.sen = sen;
    }
}
