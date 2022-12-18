package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import sun.security.provider.NativePRNG;

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

    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final List<Thread> threads = new CopyOnWriteArrayList<>();
    private final int maxThreadCount;

    private final AtomicBoolean isShutdown = new AtomicBoolean(false);
    private final AtomicInteger freeThreads = new AtomicInteger(0);
    private final AtomicInteger size = new AtomicInteger(0);

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
            return;
        }

        if (isShutdown.get()) {
            throw new RejectedExecutionException();
        }

        if (freeThreads.get() == 0 && size.get() < maxThreadCount && !isShutdown.get()) {
            size.incrementAndGet();
            Thread t = new CustomThread();
            threads.add(t);
            t.start();
        }

        if (!isShutdown.get()) {
            queue.add(command);
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
        shutdown();
        threads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return size.get();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            freeThreads.getAndIncrement();
            while (!Thread.currentThread().isInterrupted() && (!queue.isEmpty() || !isShutdown.get())) {
                try {
                    Runnable runnable = queue.take();
                    freeThreads.decrementAndGet();
                    runnable.run();
                    freeThreads.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
