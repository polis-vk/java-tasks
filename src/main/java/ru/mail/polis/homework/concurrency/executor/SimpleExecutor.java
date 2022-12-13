package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
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

    BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    List<Thread> threads;
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    private AtomicInteger freeThreadCount = new AtomicInteger(0);
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        threads = new CopyOnWriteArrayList<>();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (!isActive.get()) {
            throw new RejectedExecutionException();
        }

        if (freeThreadCount.intValue() == 0 && threads.size() < maxThreadCount) {
            Thread thread = new SimpleThread();
            threads.add(thread);
            thread.start();
        }

        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isActive.set(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        isActive.set(false);
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private class SimpleThread extends Thread {
       @Override
       public void run(){
            freeThreadCount.incrementAndGet();
            while (!Thread.currentThread().isInterrupted() && (!queue.isEmpty() || isActive.get())) {
                try {
                    Runnable task = queue.take();
                    freeThreadCount.decrementAndGet();
                    task.run();
                    freeThreadCount.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
       }
    }
}
