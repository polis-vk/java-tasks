package ru.mail.polis.homework.concurrency.executor;

import java.util.Vector;
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
    private final AtomicInteger vacantThreadCount;
    private final LinkedBlockingQueue<Runnable> queue;
    private final Vector<TreadOfTreadPool> threadPool;
    private final int maxThreadCount;
    private volatile boolean isNotShutDown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        queue = new LinkedBlockingQueue<>();
        threadPool = new Vector<>(maxThreadCount);
        isNotShutDown = true;
        vacantThreadCount = new AtomicInteger(0);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (!isNotShutDown) {
            throw new RejectedExecutionException();
        }
        queue.add(command);
        synchronized (vacantThreadCount) {
            if (vacantThreadCount.get() == 0 && threadPool.size() < maxThreadCount) {
                threadPool.add(new TreadOfTreadPool());
            }
        }
    }

    private class TreadOfTreadPool extends Thread {
        TreadOfTreadPool() {
            this.start();
        }

        @Override
        public void run() {
            Runnable command;
            while (isNotShutDown || !queue.isEmpty()) {
                vacantThreadCount.incrementAndGet();
                try {
                    command = queue.take();
                    vacantThreadCount.decrementAndGet();
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isNotShutDown = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        for (TreadOfTreadPool tread : threadPool) {
            tread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }
}
