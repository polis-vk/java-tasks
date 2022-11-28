package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {

    private volatile boolean stopped = false;
    private final List<CustomThread> threadList;
    private final LinkedBlockingQueue<Runnable> runnableQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger freeThreadsCnt = new AtomicInteger(0);
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.threadList = new ArrayList<>(maxThreadCount);

    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (stopped) {
            throw new RejectedExecutionException();
        }
        if (runnableQueue.size() >= freeThreadsCnt.get() && threadList.size() < maxThreadCount) {
            synchronized (this) {
                if (runnableQueue.size() >= freeThreadsCnt.get() && threadList.size() < maxThreadCount) {
                    freeThreadsCnt.incrementAndGet();
                    CustomThread newThread = new CustomThread();
                    threadList.add(newThread);
                    newThread.start();
                }
            }
        }
        runnableQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        stopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        stopped = true;
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

    private class CustomThread extends Thread {
        @Override
        public void run() {
            while (!stopped) {
                Runnable task = SimpleExecutor.this.runnableQueue.poll();
                if (task != null) {
                    freeThreadsCnt.decrementAndGet();
                    task.run();
                    freeThreadsCnt.incrementAndGet();
                }
            }
        }
    }

}
