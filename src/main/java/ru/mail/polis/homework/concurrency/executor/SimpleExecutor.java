package ru.mail.polis.homework.concurrency.executor;

import java.util.Arrays;
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
    private final Thread[] threads;
    private final BlockingQueue<Runnable> commands;
    private final int maxThreadCount;
    private volatile boolean isShutDown;
    private final AtomicInteger liveThreadsCount;
    private final AtomicInteger availableThreadsCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        commands = new LinkedBlockingQueue<>();
        threads = new Thread[maxThreadCount];
        liveThreadsCount = new AtomicInteger(0);
        availableThreadsCount = new AtomicInteger(0);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        if (liveThreadsCount.intValue() < maxThreadCount && availableThreadsCount.intValue() == 0) {
            synchronized (threads) {
                if (!isShutDown && liveThreadsCount.intValue() != maxThreadCount && availableThreadsCount.intValue() == 0) {
                    MyThread myThread = new MyThread();
                    threads[liveThreadsCount.intValue()] = myThread;
                    liveThreadsCount.incrementAndGet();
                    myThread.start();
                }
            }
        }
        commands.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При` добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        synchronized (this) {
            Arrays.stream(threads).forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return liveThreadsCount.get();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (!isShutDown || !commands.isEmpty()) {
                availableThreadsCount.incrementAndGet();
                try {
                    Runnable command = commands.take();
                    availableThreadsCount.decrementAndGet();
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
