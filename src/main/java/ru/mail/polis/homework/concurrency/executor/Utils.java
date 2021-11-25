package ru.mail.polis.homework.concurrency.executor;

public class Utils {

    public static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
