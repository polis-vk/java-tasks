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

    private final int maxThreadCount;
    private final AtomicInteger availableThreadsCount;
    private final BlockingQueue<Runnable> workQueue;
    private final List<Worker> workers;
    private volatile boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        availableThreadsCount = new AtomicInteger(0);
        workQueue = new LinkedBlockingQueue<>();
        workers = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException("Incorrect runnable object.");
        }
        if (isShutdown) {
            throw new RejectedExecutionException();
        }

        synchronized (workers) {
            if (availableThreadsCount.get() == 0 && workers.size() < maxThreadCount) {
                Worker worker = new Worker();
                worker.start();
                workers.add(worker);
            }
        }
        workQueue.offer(command);
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
        synchronized (workers) {
            for (Worker worker : workers) {
                worker.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            availableThreadsCount.incrementAndGet();
            while ((!isShutdown || !workQueue.isEmpty()) && !isInterrupted()) {
                try {
                    Runnable command = workQueue.take();
                    availableThreadsCount.decrementAndGet();
                    command.run();
                    availableThreadsCount.incrementAndGet();
                } catch (InterruptedException e) {
                    availableThreadsCount.decrementAndGet();
                    workers.remove(this);
                    return;
                }
            }
            availableThreadsCount.decrementAndGet();
            workers.remove(this);
        }
    }
}
