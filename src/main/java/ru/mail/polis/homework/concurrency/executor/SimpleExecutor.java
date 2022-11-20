package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
    private final List<MyThread> threads = new ArrayList<>();
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final AtomicInteger accessibleThreadCount = new AtomicInteger();
    private final int maxThreadCount;
    private volatile boolean terminated;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (terminated) {
            throw new RejectedExecutionException();
        }

        tasks.add(command);
        if (accessibleThreadCount.get() == 0) {
            synchronized (this) {
                if (!terminated && accessibleThreadCount.get() < tasks.size() && threads.size() < maxThreadCount) {
                    MyThread thread = new MyThread();
                    threads.add(thread);
                    thread.start();
                }
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        terminated = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (this) {
            threads.forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        synchronized (this) {
            return threads.size();
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (!isInterrupted() && (!terminated || !tasks.isEmpty())) {
                accessibleThreadCount.incrementAndGet();
                task = tasks.poll();
                accessibleThreadCount.decrementAndGet();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}

