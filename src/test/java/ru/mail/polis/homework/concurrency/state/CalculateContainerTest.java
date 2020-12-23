package ru.mail.polis.homework.concurrency.state;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;


public class CalculateContainerTest {

    @Test
    public void deadlockTest() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
            CalculateContainer<Double> container = new CalculateContainer<>(10d);
            for (int j = 0; j < 5; j++) {
                System.out.println("j = " + j);
                service.execute(() -> container.finish(value -> System.out.println("finish " + value)));
                service.execute(() -> container.init(Math::sqrt));
                service.execute(() -> container.run((start, param) -> start + param, 5d));
            }
//            Thread.sleep(10000);
            container.close(value -> System.out.println("close " + value));
            assertEquals(State.CLOSE, container.getState());
        }

    }

}