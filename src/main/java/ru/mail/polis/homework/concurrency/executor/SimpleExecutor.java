package ru.mail.polis.homework.concurrency.executor;

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
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final int maxThreadCount;
    private final Thread[] threads;
    private final BlockingQueue<Runnable> commands;
    private AtomicInteger waitingThreadsCount;
    private AtomicInteger threadsInitialized;
    private volatile boolean shutdownMode;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        threads = new EternalThread[maxThreadCount];
        commands = new LinkedBlockingQueue<>();
        waitingThreadsCount = new AtomicInteger(0);
        threadsInitialized = new AtomicInteger(0);
        shutdownMode = false;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownMode) {
            throw new RejectedExecutionException();
        }
        synchronized (threadsInitialized) {
            if (waitingThreadsCount.get() == 0 && threadsInitialized.get() < maxThreadCount) {
                Thread eternalThread = new EternalThread();
                threads[threadsInitialized.getAndIncrement()] = eternalThread;
                eternalThread.start();
            }
        }
        commands.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        shutdownMode = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        for (int i = 0; i < threadsInitialized.get(); i++) {
            threads[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsInitialized.get();
    }

    private class EternalThread extends Thread {

        @Override
        public void run() {
            while (!shutdownMode || !commands.isEmpty()) {
                waitingThreadsCount.incrementAndGet();
                Runnable command = null;
                try {
                    command = commands.take();
                } catch (InterruptedException e) {
                    return;
                } finally {
                    waitingThreadsCount.decrementAndGet();
                }
                command.run();
            }
        }
    }
}
