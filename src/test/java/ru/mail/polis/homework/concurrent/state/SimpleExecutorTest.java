package ru.mail.polis.homework.concurrent.state;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

    private final Runnable runnable = () -> {
        System.out.println("Starting is " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending is " + Thread.currentThread().getName());
    };

    /**
     * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
     */
    @Test
    public void Test1() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < 10; i++) {
            simpleExecutor.execute(runnable);
            try {
                Thread.sleep(1228);
            } catch (InterruptedException e) {
                System.out.println("Явно произошла ошибка");
            }
            if (simpleExecutor.isJob()) {
                Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
            } else {
                System.out.println("Не все задачи выполнены");
                Assert.fail();
            }
        }
        simpleExecutor.shutDown();
    }
    /**
     * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
     *  * примерно одновременно
     */

    @Test
    public void Test2() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int count = 10;
        for (int i = 0; i < count; i++) {
            for (int j = 1; j < count; j++) {
                simpleExecutor.execute(runnable);
            }
            try {
                Thread.sleep(1228);
            } catch (InterruptedException e) {
                System.out.println("Явно произошла ошибка");
            }
            if (simpleExecutor.isJob()) {
                Assert.assertEquals(count - 1, simpleExecutor.getLiveThreadsCount());
            } else {
                System.out.println("Не все задачи выполнены");
                Assert.fail();
            }
        }
        simpleExecutor.shutDown();
    }



    /**
     * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
     *  * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
     **/

    @Test
    public void Test3() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        int count = 10;
        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < count; j++) {
                simpleExecutor.execute(runnable);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            for (int j = 0; j < 5; j++) {
                simpleExecutor.execute(runnable);
            }
            try {
                Thread.sleep(1228);
            } catch (InterruptedException e) {
                System.out.println("Явно произошла ошибка");
            }
            if (simpleExecutor.isJob()) {
                Assert.assertEquals(count + 5, simpleExecutor.getLiveThreadsCount());
            } else {
                System.out.println("Не все задачи выполнены");
                Assert.fail();
            }
        }
        simpleExecutor.shutDown();
    }
}
