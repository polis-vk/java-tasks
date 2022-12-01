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
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private final List<Thread> threads = new ArrayList<>();
    private final AtomicInteger waitThreadCount = new AtomicInteger(0);
    private final int maxThreadCount;
    private volatile boolean closeQueue;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
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
        if (closeQueue) {
            throw new RejectedExecutionException();
        }
        synchronized (this) {
            if (threads.size() < maxThreadCount && waitThreadCount.get() == 0) {
                SimpleThread thread = new SimpleThread();
                threads.add(thread);
                thread.start();
            }
            taskQueue.add(command);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        closeQueue = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public synchronized void shutdownNow() {
        shutdown();
        threads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public synchronized int getLiveThreadsCount() {
        return threads.size();
    }

    private class SimpleThread extends Thread {

        @Override
        public void run() {
            try {
                while (!closeQueue || !taskQueue.isEmpty()) {
                    waitThreadCount.incrementAndGet();
                    Runnable task = taskQueue.take();
                    waitThreadCount.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                waitThreadCount.decrementAndGet();
                e.printStackTrace();
            }
        }
    }
}
