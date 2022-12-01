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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final List<Thread> threads;
    private final BlockingQueue<Runnable> tasks;
    private AtomicInteger freeThreadsCount;
    private final int maxThreadCount;
    private volatile boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        threads = new ArrayList<>(maxThreadCount);
        tasks = new LinkedBlockingQueue<>();
        freeThreadsCount = new AtomicInteger();
        isShutdown = false;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        synchronized (this) {
            if (freeThreadsCount.get() == 0 && getFreeThreadsCount() < maxThreadCount) {
                Thread newThread = new MyThread();
                threads.add(newThread);
                newThread.start();
            }
        }
        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (this) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getFreeThreadsCount() {
        return threads.size();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !tasks.isEmpty()) {
                freeThreadsCount.incrementAndGet();
                Runnable task;
                try {
                    task = tasks.take();
                    freeThreadsCount.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
