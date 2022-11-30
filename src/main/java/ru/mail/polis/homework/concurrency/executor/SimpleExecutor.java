package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
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

    private final LinkedBlockingQueue<Runnable> tasks;
    private volatile boolean isStopped;
    private final CopyOnWriteArrayList<Thread> threadList;
    private final AtomicInteger freeThreads;
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.tasks = new LinkedBlockingQueue<>();
        this.threadList = new CopyOnWriteArrayList<>(new ArrayList<>(maxThreadCount));
        this.freeThreads = new AtomicInteger(0);
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        if (command == null) {
            return;
        }
        tasks.add(command);
        synchronized (this) {
            if (freeThreads.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
                Thread thread = new Thread(() -> {
                    try {
                        while (!isStopped) {
                            freeThreads.incrementAndGet();
                            if (!tasks.isEmpty()) {
                                freeThreads.decrementAndGet();
                                tasks.take().run();
                            }
                        }
                    } catch (InterruptedException ignored) {

                    }
                });
                threadList.add(thread);
                thread.start();
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isStopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isStopped = true;
        for (Thread thread : threadList) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadList.size();
    }
}
