package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasksQueue = new LinkedBlockingQueue<>();
    private final List<Thread> threadsArray = new CopyOnWriteArrayList<>();
    private final AtomicReference<ExecutorState> executorState = new AtomicReference<>(new ExecutorState(0, 0, false));
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

        if (executorState.get().isShutdownThreads.get()) {
            throw new RejectedExecutionException();
        }

        if (executorState.get().sizeThreadList.get() < maxThreadCount && executorState.get().cntFreeThreads.compareAndSet(0, executorState.get().cntFreeThreads.get())) {
            executorState.get().sizeThreadList.incrementAndGet();
            Thread newThread = new SimpleThread();
            newThread.start();
            threadsArray.add(newThread);
        }

        tasksQueue.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        executorState.set(new ExecutorState(executorState.get().cntFreeThreads.get(), executorState.get().sizeThreadList.get(), true));
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        int currentThreadListSize = executorState.get().sizeThreadList.get();

        while (true) {
            for (Thread currentThread : threadsArray) {
                currentThread.interrupt();
            }

            if (currentThreadListSize == executorState.get().sizeThreadList.get()) {
                break;
            }
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
            while ((executorState.get().isShutdownThreads.compareAndSet(false, executorState.get().isShutdownThreads.get()) || !tasksQueue.isEmpty())
                    && !Thread.currentThread().isInterrupted()) {
                try {
                    executorState.get().cntFreeThreads.incrementAndGet();
                    Runnable currentTask = tasksQueue.take();
                    executorState.get().cntFreeThreads.decrementAndGet();
                    currentTask.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExecutorState {
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