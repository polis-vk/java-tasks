package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
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

    private volatile boolean isTerminated;
    private AtomicInteger usedThreads = new AtomicInteger();
    private AtomicInteger totalThreads = new AtomicInteger();
    private final BlockingQueue<Runnable> tasks;
    private final ArrayList<CustomThread> threads;

    public SimpleExecutor(int maxThreadCount) {
        totalThreads.set(maxThreadCount);
        tasks = new LinkedBlockingQueue<>();
        threads = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }
        if (isTerminated) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
        synchronized (this) {
            if (threads.size() < totalThreads.get() && usedThreads.get() < tasks.size()) {
                CustomThread customThread = new CustomThread();
                threads.add(customThread);
                customThread.start();
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isTerminated = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isTerminated = true;
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

    private class CustomThread extends Thread {
        @Override
        public void run() {
            while (!(isTerminated && tasks.isEmpty())) {
                try {
                    usedThreads.incrementAndGet();
                    Runnable task = tasks.take();
                    usedThreads.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
