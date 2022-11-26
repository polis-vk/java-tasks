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
    private final int maxThreadCount;
    private final AtomicInteger freeThreadCount = new AtomicInteger();
    private final BlockingQueue<Runnable> commandQueue = new LinkedBlockingQueue<>();
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean isShutdown = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
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
        commandQueue.add(command);
        if (freeThreadCount.get() == 0 && threads.size() < maxThreadCount) {
            Thread thread = new MyThread();
            threads.add(thread);
            thread.start();
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

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !commandQueue.isEmpty()) {
                freeThreadCount.incrementAndGet();
                Runnable command;
                try {
                    command = commandQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                freeThreadCount.decrementAndGet();
                command.run();
            }
        }
    }

}
