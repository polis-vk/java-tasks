package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> tasksQueue;
    private final AtomicInteger freeThreadsCount;
    private final List<Thread> threads;
    private final ReentrantLock lock;
    private final int maxThreadCount;
    private volatile boolean isAlive;

    public SimpleExecutor(int maxThreadCount) {
        tasksQueue = new LinkedBlockingDeque<>();
        freeThreadsCount = new AtomicInteger();
        threads = new CopyOnWriteArrayList<>();
        lock = new ReentrantLock();
        this.maxThreadCount = Math.max(maxThreadCount, 0);
        isAlive = true;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (!isAlive) {
            throw new RejectedExecutionException();
        }
        try {
            lock.lock();
            if (threads.size() < maxThreadCount && freeThreadsCount.get() == 0) {
                Thread thread = new CustomThread();
                threads.add(thread);
                thread.start();
            }
        } finally {
            lock.unlock();
        }
        tasksQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isAlive = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isAlive = false;
        for (Thread thread : threads) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            try {
                while (isAlive || !tasksQueue.isEmpty()) {
                    freeThreadsCount.incrementAndGet();
                    Runnable task = tasksQueue.take();
                    freeThreadsCount.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
