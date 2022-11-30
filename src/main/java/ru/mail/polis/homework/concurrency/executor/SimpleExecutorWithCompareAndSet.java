package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

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
public class SimpleExecutorWithCompareAndSet implements Executor {
    private final BlockingQueue<Runnable> commandsQueue = new LinkedBlockingQueue<>();
    private final Worker[] workersPool;
    private final int maxThreadCount;
    private volatile int currentThreadCount;
    private volatile int availableThreadCount;
    private volatile boolean isShutdown;

    public SimpleExecutorWithCompareAndSet(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.workersPool = new Worker[maxThreadCount];
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        if (command == null) {
            throw new NullPointerException();
        }
        if (availableThreadCount == 0 && currentThreadCount < maxThreadCount) {
            synchronized (this) {
                if (availableThreadCount == 0 && currentThreadCount < maxThreadCount) {
                    Worker worker = new Worker();
                    worker.start();
                    workersPool[currentThreadCount] = worker;
                    incrementCurrentThreadCount();
                }
            }
        }
        commandsQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        if (!isShutdown) {
            isShutdown = true;
            synchronized (this) {
                for (int i = 0; i < currentThreadCount; i++) {
                    workersPool[i].interrupt();
                }
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return currentThreadCount;
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !commandsQueue.isEmpty()) {
                try {
                    addToAvailableThreadCount(1);
                    Runnable commandToExecute = commandsQueue.take();
                    addToAvailableThreadCount(-1);
                    commandToExecute.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean compareAndSetCurrentThreadCount(int expected, int set) {
        if (currentThreadCount != expected) {
            return false;
        }
        currentThreadCount = set;
        return true;
    }

    private void incrementCurrentThreadCount() {
        int received;
        do {
            received = currentThreadCount;
        } while (!compareAndSetCurrentThreadCount(received, ++received));
    }

    private boolean compareAndSetAvailableThreadCount(int expected, int set) {
        if (availableThreadCount != expected) {
            return false;
        }
        availableThreadCount = set;
        return true;
    }

    private void addToAvailableThreadCount(int n) {
        int received;
        do {
            received = availableThreadCount;
        } while (!compareAndSetAvailableThreadCount(received, received + n));
    }
}
