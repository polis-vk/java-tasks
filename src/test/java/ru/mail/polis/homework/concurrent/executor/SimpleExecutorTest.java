package ru.mail.polis.homework.concurrent.executor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

/*
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */

    private SimpleExecutor simpleExecutor;
    public SimpleExecutorTest() {
        this.simpleExecutor = new SimpleExecutor(5);
    }

    @Test
    public void SimpleExecutorOnlyOneThread() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int numTask = i + 1;
            Runnable task = () -> {
                System.out.println("Start task  " + numTask);
                System.out.println("Finish task " + numTask);
            };

            simpleExecutor.execute(task);
            Thread.sleep(100);
            Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        }
    }

/*
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 */
    @Test
    public void SimpleExecutorManyThreads() throws InterruptedException {
        Runnable task1 = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Start task  " + 1);
            System.out.println("Finish task " + 1);
        };
        Runnable task2 = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Start task  " + 2);
            System.out.println("Finish task " + 2);
        };
        Runnable task3 = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Start task  " + 3);
            System.out.println("Finish task " + 3);
        };
        Runnable task4 = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Start task  " + 4);
            System.out.println("Finish task " + 4);
        };

        simpleExecutor.execute(task1);
        simpleExecutor.execute(task2);
        simpleExecutor.execute(task3);
        simpleExecutor.execute(task4);
        Thread.sleep(1000);
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());

    }


/*
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */
    @Test
    public void SimpleExecutorNPlusMThreads() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int numTask = i + 1;
            Runnable task1 = () -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Start task  " + 1 + " " + numTask);
                System.out.println("Finish task " + 1 + " " + numTask);
            };
            Runnable task2 = () -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Start task  " + 2 + " " + numTask);
                System.out.println("Finish task " + 2 + " " + numTask);
            };
            Runnable task3 = () -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Start task  " + 3 + " " + numTask);
                System.out.println("Finish task " + 3 + " " + numTask);
            };
            Runnable task4 = () -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Start task  " + 4 + " " + numTask);
                System.out.println("Finish task " + 4 + " " + numTask);
            };

            simpleExecutor.execute(task1);
            simpleExecutor.execute(task2);
            simpleExecutor.execute(task3);
            simpleExecutor.execute(task4);
            Thread.sleep(100);
            Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());

        }
    }


}
