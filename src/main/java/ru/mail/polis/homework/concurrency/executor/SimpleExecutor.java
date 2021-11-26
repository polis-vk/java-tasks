package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

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

    private class Task implements Runnable {
        @Override
        public void run() {
            Runnable active;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    active = getTask();
                    if (active == null) {
                        break;
                    }
                    active.run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    private final int maxThreadCount;
    private final List<Thread> threads;
    private final Lock lock = new ReentrantLock();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private boolean isShutdown = false;
    private volatile int threadsSize = 0;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException("maxThreadCount must be a positive number");
        }
        this.maxThreadCount = maxThreadCount;
        threads = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        lock.lock();
        if (isShutdown) {
            throw new RejectedExecutionException("Adding new tasks while running others");
        }
        lock.unlock();
        if (command == null) {
            throw new IllegalArgumentException("Illegal null-command");
        }
        addTask(command);
        Utils.pause(10); // Wait for notifying
        if (!tasks.isEmpty() && threadsSize < maxThreadCount) {
            ++threadsSize;
            Thread t = new Thread(new Task());
            threads.add(t);
            t.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        lock.lock();
        isShutdown = true;
        lock.unlock();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        for (Thread t: threads) {
            t.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsSize;
    }

    private void addTask(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException ignored) {
        }
    }

    private Runnable getTask() throws InterruptedException {
        return isShutdown ? tasks.poll() : tasks.take();
    }

}
