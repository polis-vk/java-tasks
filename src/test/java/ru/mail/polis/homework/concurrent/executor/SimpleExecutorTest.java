package ru.mail.polis.homework.concurrent.executor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {
    private final int N = 5;
    private final int M = 5;
    private final int TASK_TIME = 1000;
    private final int AWAIT_TIME = 2000;

    private SimpleExecutor executor;

    @Before
    public void before() {
        executor = new SimpleExecutor(N);
    }

    @Test
    public void firstTest() throws InterruptedException {
        for (int i = 0; i < N; ++i) {
            executor.execute(createTask("1"));
            Thread.sleep(AWAIT_TIME);
            Assert.assertEquals(1, executor.getLiveThreadsCount());
            System.out.println();
        }
        executor.shutdown();
    }

    @Test
    public void secondTest() throws InterruptedException {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N - 1; ++j) {
                executor.execute(createTask(Integer.toString(j)));
            }
            Thread.sleep(AWAIT_TIME);
            Assert.assertEquals(N - 1, executor.getLiveThreadsCount());
            System.out.println();
        }
        executor.shutdown();
    }

    @Test
    public void thirdTest() throws InterruptedException {
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                executor.execute(createTask("MAIN" + j));
            }
            for (int j = 0; j < M; ++j) {
                executor.execute(createTask("ADITIONAL" + j));
            }
            Thread.sleep(AWAIT_TIME * 2);
            Assert.assertEquals(N, executor.getLiveThreadsCount());
            System.out.println();
        }
        executor.shutdown();
    }

    private Runnable createTask() {
        return createTask("");
    }

    private Runnable createTask(String name) {
        return () -> {
            System.out.println("Started " + name + " task on thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(TASK_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Finished " + name + " task on thread " + Thread.currentThread().getName());
        };
    }
}
