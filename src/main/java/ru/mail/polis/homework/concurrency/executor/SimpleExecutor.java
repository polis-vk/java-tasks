package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 * <p>
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */
public class SimpleExecutor implements Executor {
    private final int AMOUNT;
    private final BlockingQueue<Runnable> threads;
    private final AtomicInteger size = new AtomicInteger(0);
    private final AtomicBoolean working = new AtomicBoolean(false);
    private volatile boolean isFinished = false;

    public SimpleExecutor(int amount) {
        this.AMOUNT = amount - 1;
        threads = new ArrayBlockingQueue<>(this.AMOUNT);

        Thread demon = new Thread(() -> {
            while (!isFinished || !threads.isEmpty()) {
                if (size.get() < AMOUNT) {
                    Thread t = new Thread(() -> {
                        try {
                            threads.take().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            size.decrementAndGet();
                        }
                    });

                    t.start();
                }
            }
        });

        demon.start();
    }

    @Override
    public void execute(Runnable command) {
        try {
            if (!working.get()) {
                Thread t = new Thread(() -> {
                    working.set(true);
                    command.run();
                    working.set(false);

                    size.decrementAndGet();
                });

                t.start();
            } else {
                threads.put(command);
            }

            size.incrementAndGet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isFinished = true;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return size.get();
    }
}
