package ru.mail.polis.homework.concurrency.executor;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;
import static org.junit.Assert.assertEquals;


public class SimpleExecutorTest {

    // исполнение одной задачи с интервалами во времени
    @Test
    public void oneTaskTest() throws InterruptedException {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Runnable task = () -> {
            System.out.println("task in progress");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task is done");
        };

        // запускаем по очереди 5 задач, давая фору на исполнение в примерно 500 мс
        for (int i = 0; i < 5; i++) {
            simpleExecutor.execute(task);
            Thread.sleep(2500);
            Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        }

        simpleExecutor.shutdown();
    }

    // исполнение одновременно n, где n - максимальное число потоков исполнителя
    @Test
    public void lessThanLimitTasksTest() throws InterruptedException {
        int n = 10;
        SimpleExecutor simpleExecutor = new SimpleExecutor(10);
        Runnable task = () -> {
            System.out.println("task in progress");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task is done");
        };

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < n - 1; i++) {
                simpleExecutor.execute(task);
            }
            // запускаем n - 1 задач и проверяем количество воркеров сначала
            // во время исполнения, затем после конца исполнения
            Thread.sleep(1250);
            System.out.println("assertion1");
            Assert.assertEquals(n - 1, simpleExecutor.getLiveThreadsCount());
            Thread.sleep(1250);
            System.out.println("assertion2\n");
            Assert.assertEquals(n - 1, simpleExecutor.getLiveThreadsCount());
        }

        simpleExecutor.shutdown();
    }

    // исполнение одновременно n + m задач, где n - максимальное число потоков исполнителя
    @Test
    public void moreThanLimitTasksTest() throws InterruptedException {
        int n = 10;
        int m = 5;
        SimpleExecutor simpleExecutor = new SimpleExecutor(10);
        Runnable task = () -> {
            System.out.println("task in progress");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task is done");
        };

        for (int i = 0; i < n + m; i++) {
            simpleExecutor.execute(task);
        }
        Thread.sleep(1500);
        Assert.assertEquals(n, simpleExecutor.getLiveThreadsCount());
        System.out.println("assertion1");
        Thread.sleep(1500);
        Assert.assertEquals(n, simpleExecutor.getLiveThreadsCount());
        System.out.println("assertion2");
        Thread.sleep(1500);

        simpleExecutor.shutdown();
    }

}