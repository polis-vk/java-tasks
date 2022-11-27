package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
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
    private BlockingQueue<Runnable> tasksQueue;
    private ArrayList<Thread> threadsArray;
    private AtomicInteger cntFreeThreads;
    private volatile boolean isShutdownThreads;
    private int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        tasksQueue = new LinkedBlockingQueue<>();
        threadsArray = new ArrayList<>(maxThreadCount);
        cntFreeThreads = new AtomicInteger(0);
        isShutdownThreads = false;
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }

        if (isShutdownThreads) {
            throw new RejectedExecutionException();
        }

        synchronized (threadsArray) {
            if (threadsArray.size() < maxThreadCount && cntFreeThreads.compareAndSet(0, 0)) {
                Thread newThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!isShutdownThreads || !tasksQueue.isEmpty()) {
                            try {
                                cntFreeThreads.incrementAndGet();
                                Runnable currentTask = tasksQueue.take();
                                cntFreeThreads.decrementAndGet();
                                currentTask.run();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                newThread.start();
                threadsArray.add(newThread);
            }
        }

        tasksQueue.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdownThreads = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShutdownThreads = true;
        synchronized (threadsArray) {
            for (Thread currentThread : threadsArray) {
                currentThread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsArray.size();
    }
}
