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
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final AtomicInteger vacantThreadCount;
    private final LinkedBlockingQueue<Runnable> queue;
    private final Vector<TreadOfTreadPool> threadPool;
    private final int maxThreadCount;
    private volatile boolean shutDown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        queue = new LinkedBlockingQueue<>();
        threadPool = new Vector<>(maxThreadCount);
        vacantThreadCount = new AtomicInteger(0);
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
        if (shutDown) {
            throw new RejectedExecutionException();
        }
        synchronized (vacantThreadCount) {
            if (vacantThreadCount.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
                TreadOfTreadPool thread = new TreadOfTreadPool();
                threadPool.add(thread);
                thread.start();
            }
        }
        queue.add(command);
    }

    private class TreadOfTreadPool extends Thread {
        @Override
        public void run() {
            while (!shutDown || !queue.isEmpty()) {
                vacantThreadCount.incrementAndGet();
                Runnable command;
                try {
                    command = queue.take();
                } catch (InterruptedException e) {
                    return;
                } finally {
                    vacantThreadCount.decrementAndGet();
                }
                command.run();
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        shutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        for (TreadOfTreadPool thread : threadPool) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }
}
