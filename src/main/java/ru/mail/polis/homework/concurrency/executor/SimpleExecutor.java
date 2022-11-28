package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasksQueue;
    private final List<Thread> threads;
    private final AtomicInteger freeThreadsCount;
    private final int maxThreadCount;
    private volatile boolean running;

    public SimpleExecutor(int maxThreadCount) {
        this.tasksQueue = new LinkedBlockingQueue<>();
        this.threads = new ArrayList<>();
        this.freeThreadsCount = new AtomicInteger();
        this.maxThreadCount = maxThreadCount;
        this.running = true;
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
        if (!running) {
            throw new RejectedExecutionException();
        }
        synchronized (this) {
            if (threads.size() < maxThreadCount && freeThreadsCount.get() == 0) {
                Thread thread = new SimpleThread();
                threads.add(thread);
                thread.start();
            }
        }
        tasksQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        running = false;
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
        synchronized (this) {
            return threads.size();
        }
    }

    private class SimpleThread extends Thread {
        @Override
        public void run() {
            try {
                while (running || !tasksQueue.isEmpty()) {
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
