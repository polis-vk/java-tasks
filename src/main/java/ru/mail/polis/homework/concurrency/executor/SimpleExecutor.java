package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.List;
import java.util.concurrent.*;
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
    private final BlockingQueue<Runnable> tasksQueue = new LinkedBlockingQueue<>();
    private final List<Thread> threadsArray = new CopyOnWriteArrayList<>();
    private volatile ExecutorState executorState = new ExecutorState(0, 0, false);
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException();
        }

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

        if (executorState.isShutdownThreads.get()) {
            throw new RejectedExecutionException();
        }

        tasksQueue.offer(command);

        if (executorState.sizeThreadList.get() < maxThreadCount && executorState.cntFreeThreads.get() < tasksQueue.size()) {
            int currentSizeThreadList = executorState.sizeThreadList.incrementAndGet();
            if (currentSizeThreadList <= maxThreadCount && !executorState.isShutdownThreads.get()) {
                Thread newThread = new SimpleThread();
                newThread.start();
                threadsArray.add(newThread);
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        executorState = new ExecutorState(executorState.cntFreeThreads.get(), executorState.sizeThreadList.get(), true);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        int instanceThreadListSize = executorState.sizeThreadList.get();

        for (Thread currentThread : threadsArray) {
            currentThread.interrupt();
        }

        while (instanceThreadListSize != executorState.sizeThreadList.get()) {
            int currentThreadListSize = executorState.sizeThreadList.get();
            for (int i = instanceThreadListSize; i < executorState.sizeThreadList.get(); i++) {
                threadsArray.get(i).interrupt();
            }

            instanceThreadListSize = currentThreadListSize;
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsArray.size();
    }

    class SimpleThread extends Thread {
        @Override
        public void run() {
            executorState.cntFreeThreads.incrementAndGet();
            while ((executorState.isShutdownThreads.get() == false || !tasksQueue.isEmpty())
                    && !Thread.currentThread().isInterrupted()) {
                try {
                    Runnable currentTask = tasksQueue.take();
                    executorState.cntFreeThreads.decrementAndGet();
                    currentTask.run();
                    executorState.cntFreeThreads.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ExecutorState {
        private final AtomicInteger cntFreeThreads;
        private final AtomicInteger sizeThreadList;
        private final AtomicBoolean isShutdownThreads;

        public ExecutorState(int cntFreeThreads, int sizeThreadList, boolean isShutdownThreads) {
            this.cntFreeThreads = new AtomicInteger(cntFreeThreads);
            this.sizeThreadList = new AtomicInteger(sizeThreadList);
            this.isShutdownThreads = new AtomicBoolean(isShutdownThreads);
        }
    }
}