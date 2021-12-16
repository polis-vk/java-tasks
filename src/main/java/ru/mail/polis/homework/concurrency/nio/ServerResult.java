package ru.mail.polis.homework.concurrency.nio;

public class ServerResult {
    private double result;
    private final int id;

    public ServerResult(int id) {
        this.id = id;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }
}
