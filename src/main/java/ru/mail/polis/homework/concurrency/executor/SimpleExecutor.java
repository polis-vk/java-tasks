package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
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
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingDeque<>();
    private final List<Thread> pool;
    private final int maximumPoolSize;
    private final AtomicInteger busyThreadsCount = new AtomicInteger();
    private volatile boolean isShutDown;

    public SimpleExecutor(int maxThreadCount) {
        maximumPoolSize = maxThreadCount;
        pool = Collections.synchronizedList(new ArrayList<>(maxThreadCount));
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
        // mb sync???
        if (busyThreadsCount.get() == maximumPoolSize) {
            Thread thread = new Worker();
            pool.add(thread);
            thread.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        for (Thread thread : pool) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return pool.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!isShutDown || !tasks.isEmpty()) {
                try {
                    busyThreadsCount.incrementAndGet();
                    tasks.take().run();
                    busyThreadsCount.decrementAndGet();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}
