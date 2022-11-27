package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
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

    private final int maxThreadCount;
    private boolean isShutDown = false;
    private boolean isShutDownNow = false;
    private final AtomicInteger readyThreadsCount = new AtomicInteger(0);
    private final ConcurrentLinkedDeque<Runnable> commandDeque = new ConcurrentLinkedDeque<>();
    private final CopyOnWriteArrayList<SimpleThread> threadPool = new CopyOnWriteArrayList<>();

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    private class SimpleThread extends Thread {
        public SimpleThread() {
            readyThreadsCount.incrementAndGet();
            start();
        }

        @Override
        public void run() {
            while (true) {
                if (!commandDeque.isEmpty()) {
                    readyThreadsCount.decrementAndGet();
                    try {
                        commandDeque.pollLast().run();
                    } catch (NullPointerException ignored) {
                    }
                    readyThreadsCount.incrementAndGet();
                }
                if (isShutDownNow) {
                    this.interrupt();
                }
            }
        }
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown || isShutDownNow) {
            throw new RejectedExecutionException();
        }
        commandDeque.addFirst(command);
        synchronized (this) {
            if (readyThreadsCount.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
                threadPool.add(new SimpleThread());
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isShutDownNow = true;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }
}
