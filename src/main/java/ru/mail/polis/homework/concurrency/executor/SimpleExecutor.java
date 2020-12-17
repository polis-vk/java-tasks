package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final AtomicInteger numberOfThreads = new AtomicInteger(0);
    private final BlockingQueue<Runnable> tasksToDoQueue = new LinkedBlockingQueue<>();
    private final List<SimpleThread> threadsList = new ArrayList<>();

    public SimpleExecutor(int limit) {
        numberOfThreadsLimit = limit;
    }

    public SimpleExecutor() {
        numberOfThreadsLimit = 50;
    }

    @Override
    public void execute(Runnable task) {
        tasksToDoQueue.offer(task);
        SimpleThread freeThread = null;
        for (SimpleThread thread : threadsList) {
            if (thread.getState() == Thread.State.WAITING) {
                freeThread = thread;
                freeThread.notifySimpleThread();
                break;
            }
        }
        if (freeThread == null) {
            if (numberOfThreads.get() < numberOfThreadsLimit) {
                SimpleThread newThread = new SimpleThread();
                threadsList.add(newThread);
                newThread.start();
            } else {
                for (SimpleThread thread : threadsList) {
                    if (thread.getState() == Thread.State.WAITING) {
                        thread.notifySimpleThread();
                    }
                }
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return numberOfThreads.get();
    }

    private final class SimpleThread extends Thread {

        SimpleThread() {
            numberOfThreads.incrementAndGet();
        }

        public void notifySimpleThread() {
            synchronized (this) {
                this.notifyAll();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        Runnable task;
                        while ((task = tasksToDoQueue.poll()) == null) {
                            wait();
                        }
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
