package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> workersTasks = new LinkedBlockingQueue<>();
    private final ReentrantLock executeLock = new ReentrantLock();
    private final ReentrantLock shutDownLock = new ReentrantLock();
    private final AtomicInteger freeWorkers = new AtomicInteger(0);
    private final AtomicInteger workersCounter = new AtomicInteger(0);
    private final List<Thread> workers;
    private final int maxThreadCount;
    private volatile boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.workers = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        workersTasks.add(command);
        if (workersCounter.get() < maxThreadCount && freeWorkers.get() == 0) {
            executeLock.lock();
            if (workersCounter.get() < maxThreadCount && freeWorkers.get() == 0) {
                Thread worker = new Thread(new Worker());
                workersCounter.incrementAndGet();
                workers.add(worker);
                worker.start();
            }
            executeLock.unlock();
        }
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
        isShutdown = true;
        shutDownLock.lock();
        for (Thread worker : workers) {
            worker.interrupt();
        }
        shutDownLock.unlock();
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workersCounter.get();
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (!isShutdown) {
                try {
                    freeWorkers.incrementAndGet();
                    Runnable task = workersTasks.take();
                    freeWorkers.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
