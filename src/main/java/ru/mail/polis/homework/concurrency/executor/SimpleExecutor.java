package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
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
 * Max 10 баллов
 */

public class SimpleExecutor implements Executor {
    private final int maxThreadCount;
    private volatile boolean isStopped;
    private final AtomicInteger freeThreads = new AtomicInteger();
    private final List<Thread> threads;
    private final BlockingQueue<Runnable> timetable = new LinkedBlockingQueue<>();

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException("Invalid quantity value.\n");
        }
        this.maxThreadCount = maxThreadCount;
        this.threads = Collections.synchronizedList(new ArrayList<>(maxThreadCount));
    }

    @Override
    public void execute(Runnable command) {
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        if (threads.size() < maxThreadCount && freeThreads.get() == 0) {
            synchronized (threads) {
                if (threads.size() < maxThreadCount) {
                    Thread thread = new Thread(this::run);
                    thread.start();
                    threads.add(thread);
                }
            }
        }
        try {
            timetable.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isStopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (threads) {
            threads.forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private void run() {
        try {
            while (!timetable.isEmpty() || !isStopped) {
                freeThreads.incrementAndGet();
                Runnable runnable = timetable.take();
                freeThreads.decrementAndGet();
                runnable.run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

