package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 *
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */
public class SimpleExecutor implements Executor {
    private final int numberOfThreadsLimit;
    private final AtomicInteger numberOfThreads = new AtomicInteger();
    private final BlockingQueue<Runnable> tasksToDoQueue;
    private final Lock lock = new ReentrantLock();

    public SimpleExecutor(int limit) {
        numberOfThreadsLimit = limit;
        tasksToDoQueue = new LinkedBlockingQueue<>();
    }

    public SimpleExecutor() {
        numberOfThreadsLimit = 50;
        tasksToDoQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void execute(Runnable task) {
        tasksToDoQueue.offer(task);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (numberOfThreads.get() < numberOfThreadsLimit && tasksToDoQueue.size() > 0) {
            tryAddThread();
        }
    }

    private void tryAddThread() {
        lock.lock();
        try {
            if (numberOfThreads.get() == numberOfThreadsLimit || tasksToDoQueue.size() == 0) {
                return;
            }
            Thread thread = new SimpleThread();
            numberOfThreads.incrementAndGet();
            thread.start();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return numberOfThreads.get();
    }

    private final class SimpleThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = tasksToDoQueue.poll(5, TimeUnit.MINUTES);
                    if (task == null) {
                        numberOfThreads.decrementAndGet();
                        return;
                    }
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }


    }
}
