package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
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
    private final Queue<Runnable> commandsQueue = new ConcurrentLinkedQueue<>();
    private final int maxThreadCount;
    private boolean isShuttingDown;

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
        if (getThreadsAvailableCount() == 0 && threads.size() < maxThreadCount) {
            if (threadsCount.incrementAndGet() <= maxThreadCount) {
                ExecutorThread newThread = new ExecutorThread();
                newThread.setCurrentJob(command);
                newThread.start();
                threads.add(newThread);
            } else {
                commandsQueue.offer(command);
            }
        } else {
            commandsQueue.offer(command);
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
        return threads.size();
    }

    private int getThreadsAvailableCount() {
        int count = 0;
        for (ExecutorThread thread : threads) {
            if (thread.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    private class ExecutorThread extends Thread {
        private boolean isAvailable = true;
        private Runnable currentJob;

        @Override
        public void run() {
            while (!isInterrupted()) {
                if (currentJob != null) {
                    isAvailable = false;
                    currentJob.run();
                    isAvailable = true;
                    currentJob = null;
                }
                if (!commandsQueue.isEmpty()) {
                    setCurrentJob(commandsQueue.poll());
                }
            }
        }

        public boolean isAvailable() {
            return isAvailable;
        }

        public void setCurrentJob(Runnable newJob) {
            currentJob = newJob;
        }
    }
}
