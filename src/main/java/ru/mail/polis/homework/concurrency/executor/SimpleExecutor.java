package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.*;
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

    private final List<Thread> threadList = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final int maxThreadCount;
    private volatile boolean isActive = true;
    private final AtomicInteger freeThreads = new AtomicInteger();

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (!isActive) {
            throw new RejectedExecutionException();
        }
        if (freeThreads.get() == 0 && threadList.size() < maxThreadCount ) {
            synchronized (freeThreads) {
                if (!isActive) {
                    return;
                }
                if (freeThreads.get() == 0 && threadList.size() < maxThreadCount) {
                    Thread thread = new CustomThread();
                    threadList.add(thread);
                    thread.start();
                }
            }
        }
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isActive = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isActive = false;
        threadList.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadList.size();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            freeThreads.incrementAndGet();
            while (!Thread.currentThread().isInterrupted() && (!queue.isEmpty() || isActive)) {
                try {
                    Runnable deal = queue.take();
                    freeThreads.decrementAndGet();
                    deal.run();
                    freeThreads.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
