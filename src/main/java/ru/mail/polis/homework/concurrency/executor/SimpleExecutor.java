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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> queueOfTasks = new LinkedBlockingQueue<>();
    private final List<Worker> poolOfThreads = new ArrayList<>();
    private volatile boolean isRunning = true;
    private final AtomicInteger countOfFreeThread = new AtomicInteger();
    private final int maxCountOfThreads;

    public SimpleExecutor(int maxCountOfThreads) {
        if (maxCountOfThreads < 1) {
            throw new IllegalArgumentException();
        }
        this.maxCountOfThreads = maxCountOfThreads;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (!isRunning) {
            throw new RejectedExecutionException();
        }

        queueOfTasks.offer(command);
        if (countOfFreeThread.get() == 0 && !queueOfTasks.isEmpty()) {
            synchronized (poolOfThreads) {
                if (poolOfThreads.size() < maxCountOfThreads) {
                    Worker worker = new Worker();
                    poolOfThreads.add(worker);
                    worker.start();
                }
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isRunning = false;
        synchronized (poolOfThreads) {
            poolOfThreads.forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return poolOfThreads.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                while (isRunning || !queueOfTasks.isEmpty()) {
                    countOfFreeThread.incrementAndGet();
                    Runnable task = queueOfTasks.take();
                    countOfFreeThread.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}