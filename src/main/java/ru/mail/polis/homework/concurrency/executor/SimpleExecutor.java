package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final int maxThreads;
    private volatile boolean finish = false;
    private boolean canCreateMoreThreads = true;

    private class ThreadInformation {
        public AtomicBoolean isBusy;
        public Runnable task;
        Thread thread;

        public ThreadInformation(boolean isBusy, Runnable task, Thread thread) {
            this.isBusy = new AtomicBoolean(isBusy);
            this.task = task;
            this.thread = thread;
        }
    }

    // doesn't represent the amount of created threads
    private AtomicInteger threadCountHelper = new AtomicInteger(0);
    private Map<Long, ThreadInformation> threads = new ConcurrentHashMap<>();

    public SimpleExecutor(int maxThreadCount) {
        maxThreads = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (finish) {
            throw new RejectedExecutionException();
        }

        if (!peekWaitingThread(command)) {
            if (canCreateMoreThreads && threadCountHelper.incrementAndGet() <= maxThreads) {
                createThread(command);
            } else {
                canCreateMoreThreads = false;
                tasks.add(command);
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        finish = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        finish = true;
        for (ThreadInformation thread : threads.values()) {
            thread.thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private void createThread(Runnable startTask) {
        Thread newThread = new Thread(() -> {
            try {
                Runnable task = startTask;
                ThreadInformation currentThreadInfo = threads.get(Thread.currentThread().getId());
                while (true) {
                    if (task != null) {
                        task.run();
                    }

                    task = currentThreadInfo.task;
                    if (task == null) {
                        task = tasks.poll();
                        if (task == null) {
                            synchronized (currentThreadInfo) {
                                currentThreadInfo.isBusy.set(false);
                                currentThreadInfo.wait(100L);
                            }
                        }
                    } else {
                        currentThreadInfo.task = null;
                    }
                }
            } catch (InterruptedException e) {}
        });

        threads.put(newThread.getId(), new ThreadInformation(true, null, newThread));
        newThread.start();
    }

    private boolean peekWaitingThread(Runnable task) {
        for (ThreadInformation thread : threads.values()) {
            if (thread.isBusy.compareAndSet(false, true)) {
                thread.task = task;
                synchronized (thread) {
                    thread.notify();
                }
                return true;
            }
        }

        return false;
    }
}

