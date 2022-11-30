package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

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
    private final BlockingQueue<Runnable> blockingQueue;
    private AtomicInteger activeCountThreads;
    private final int maxThreadCount;
    private final List<MyThread> threadList;
    private volatile boolean flag;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        blockingQueue = new LinkedBlockingQueue<>(maxThreadCount);
        threadList = new ArrayList<>(maxThreadCount);
        activeCountThreads = new AtomicInteger();
        flag = true;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        checkIsShutDown();
        synchronized (SimpleExecutor.class) {
            if (activeCountThreads.get() == 0 && threadList.size() < maxThreadCount) {
                MyThread newThread = new MyThread();
                newThread.start();
                threadList.add(newThread);
            }
        }
        blockingQueue.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        flag = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (SimpleExecutor.class) {
            for (Thread thread : threadList) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadList.size();
    }

    private void checkIsShutDown() {
        if (!flag) {
            throw new RejectedExecutionException();
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            try {
                while (flag || !blockingQueue.isEmpty()) {
                    activeCountThreads.incrementAndGet();
                    Runnable task = blockingQueue.take();
                    activeCountThreads.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException exception) {
                exception.getMessage();
            }
        }
    }
}
