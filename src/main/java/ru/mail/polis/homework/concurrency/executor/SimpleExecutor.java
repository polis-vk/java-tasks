package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private final BlockingQueue<Runnable> workQueue;
    private final AtomicInteger freeWorkers;
    private final AtomicInteger threadsCount;
    private final List<Thread> threads;
    private final Lock lock;
    private volatile boolean isShutdown;
    private final int maxThreadCount;


    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        workQueue = new LinkedBlockingQueue<>();
        freeWorkers = new AtomicInteger();
        threadsCount = new AtomicInteger();
        threads = new ArrayList<>();
        lock = new ReentrantLock();
        this.maxThreadCount = maxThreadCount;
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
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        workQueue.add(command);
        lock.lock();
        if (freeWorkers.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
            Thread thread = new Thread(new Worker());
            thread.start();
            threads.add(thread);
            threadsCount.incrementAndGet();
        }
        lock.unlock();
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (this){
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount.intValue();
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!workQueue.isEmpty() || !isShutdown) {
                    freeWorkers.incrementAndGet();
                    Runnable task = workQueue.take();
                    freeWorkers.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
