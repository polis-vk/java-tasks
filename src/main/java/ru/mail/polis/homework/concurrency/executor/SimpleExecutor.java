package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> tasks;
    private final List<Thread> threads;
    private final int maxThreads;
    private volatile boolean finish = false;

    public SimpleExecutor(int maxThreadCount) {
        maxThreads = maxThreadCount;
        tasks = new LinkedBlockingQueue<>();
        threads = new ArrayList<>();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (finish) {
            throw new RejectedExecutionException();
        }
        if (!doWeHaveWaitingThreads() && canWeCreateThread()) {
            createThread();
        }
        synchronized (tasks) {
            tasks.add(command);
            tasks.notify();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        finish = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        finish = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        synchronized (threads) {
            return threads.size();
        }
    }

    private boolean doWeHaveWaitingThreads() {
        synchronized (threads) {
            for (Thread thread : threads) {
                if (thread.getState() == Thread.State.WAITING) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canWeCreateThread() {
        synchronized (threads) {
            return threads.size() < maxThreads;
        }
    }

    private void createThread() {
        Thread newThread = new Thread(() -> {
            while (true) {
                Runnable task;

                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            return;
                        }
                    }

                    task = tasks.poll();
                }

                task.run();
            }
        });

        synchronized (threads) {
            threads.add(newThread);
        }

        newThread.start();
    }
}
