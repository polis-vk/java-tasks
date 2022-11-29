package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
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

    private final List<Thread> threadList;
    private final BlockingQueue<Runnable> queue;
    private final int maxThreadCount;
    private volatile boolean exit;
    private final AtomicInteger freeThreads;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        freeThreads = new AtomicInteger(0);
        queue = new LinkedBlockingQueue<>();
        threadList = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (exit) {
            throw new RejectedExecutionException();
        }
        createNewThreadOrNot();
        queue.offer(command);
    }

    private synchronized void createNewThreadOrNot() {
        if (threadList.size() < maxThreadCount && freeThreads.get() == 0) {
            threadList.add(new CustomThread());
            threadList.get(threadList.size() - 1).start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        exit = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
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
            Runnable deal;
            try {
                while ((!queue.isEmpty() || !exit) && !isInterrupted()) {
                    freeThreads.incrementAndGet();
                    deal = queue.take();
                    freeThreads.decrementAndGet();
                    deal.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
