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
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final List<ExecutorThread> threads;
    private final AtomicInteger threadsCount = new AtomicInteger(0);
    private final BlockingQueue<Runnable> commandsQueue = new LinkedBlockingQueue<>();
    private final int maxThreadCount;
    private volatile boolean isShuttingDown;
    private final AtomicInteger threadsAvailableCount = new AtomicInteger(0);

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        threads = new ArrayList<>(maxThreadCount);
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
        if (isShuttingDown) {
            throw new RejectedExecutionException();
        }
        if (threadsAvailableCount.get() == 0 && threadsCount.get() < maxThreadCount) {
            if (threadsCount.incrementAndGet() <= maxThreadCount) {
                ExecutorThread newThread = new ExecutorThread(command);
                newThread.start();
                threads.add(newThread);
            } else {
                commandsQueue.add(command);
            }
        } else {
            commandsQueue.add(command);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShuttingDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount.get();
    }

    private class ExecutorThread extends Thread {
        private Runnable firstJob;

        public ExecutorThread(Runnable firstJob) {
            this.firstJob = firstJob;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                threadsAvailableCount.incrementAndGet();
                if (firstJob != null) {
                    threadsAvailableCount.decrementAndGet();
                    firstJob.run();
                    threadsAvailableCount.incrementAndGet();
                    firstJob = null;
                }
                try {
                    Runnable command = commandsQueue.take();
                    threadsAvailableCount.decrementAndGet();
                    command.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
