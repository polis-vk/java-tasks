package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private final List<Thread> threads;
    private final AtomicBoolean isShutdown;
    private final BlockingQueue<Runnable> tasks;
    private final int maxThreadCount;
    private final AtomicInteger freeThreadsCount;

    public SimpleExecutor(int maxThreadCount) {
        this.isShutdown = new AtomicBoolean(false);
        this.tasks = new LinkedBlockingQueue<>();
        this.threads = Collections.synchronizedList(new ArrayList<>());
        this.maxThreadCount = maxThreadCount;
        this.freeThreadsCount = new AtomicInteger(0);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown.get()) {
            throw new RejectedExecutionException();
        }

        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (threads) {
            if (freeThreadsCount.get() == 0 && threads.size() < maxThreadCount) {
                Thread newThread = new Thread(() -> {
                    while (!isShutdown.get() || (!tasks.isEmpty() && !Thread.currentThread().isInterrupted())) {
                        freeThreadsCount.getAndIncrement();
                        try {
                            Runnable task = tasks.take();
                            freeThreadsCount.getAndDecrement();
                            task.run();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                newThread.start();
                threads.add(newThread);
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown.compareAndSet(false, true);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        isShutdown.compareAndSet(false, true);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }
}
