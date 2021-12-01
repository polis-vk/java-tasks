package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private volatile boolean isStopped = false;
    private final List<Thread> threads = new ArrayList<>();
    private final AtomicInteger freeThreadCount = new AtomicInteger();
    private final int maxThreadCount;
    private final Lock lock = new ReentrantLock();

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException();
        }

        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        lock.lock();
        try {
            if ((freeThreadCount.get() == 0) && (threads.size() < maxThreadCount)) {
                createThread(command);
            } else {
                queue.put(command);
            }
        } catch (InterruptedException ignored) {
        } finally {
            lock.unlock();
        }
    }

    private void createThread(Runnable command) {
        Thread tempThread = new Thread(
                () -> {
                    command.run();
                    try {
                        while (!isStopped) {
                            freeThreadCount.incrementAndGet();
                            Runnable task = queue.take();
                            freeThreadCount.decrementAndGet();
                            task.run();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }, "worker"
        );
        threads.add(tempThread);
        tempThread.start();
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
        lock.lock();
        try {
            isStopped = true;
            for (Thread thread : threads) {
                thread.interrupt();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }
}
