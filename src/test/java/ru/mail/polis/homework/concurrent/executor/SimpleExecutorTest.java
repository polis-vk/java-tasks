package ru.mail.polis.homework.concurrent.executor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

    @Test
    public void oneTaskWithInterval() {
        SimpleExecutor executor = new SimpleExecutor(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("Some work results");
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(executor.getLiveThreadsCount(), 1);
        }
    }

}