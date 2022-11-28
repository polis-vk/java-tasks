package ru.mail.polis.homework.concurrency.executor;

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
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> commandsQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger currentThreadCount = new AtomicInteger();
    private final AtomicInteger availableThreadCount = new AtomicInteger();
    private final Worker[] workersPool;
    private final int maxThreadCount;
    private volatile boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
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
        if (availableThreadCount.get() == 0 && currentThreadCount.get() < maxThreadCount) {
            synchronized (this) {
                if (availableThreadCount.get() == 0 && currentThreadCount.get() < maxThreadCount) {
                    Worker worker = new Worker();
                    worker.start();
                    workersPool[currentThreadCount.getAndIncrement()] = worker;
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
                for (int i = 0; i < currentThreadCount.get(); i++) {
                    workersPool[i].interrupt();
                }
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return currentThreadCount.get();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !commandsQueue.isEmpty()) {
                try {
                    availableThreadCount.incrementAndGet();
                    Runnable commandToExecute = commandsQueue.take();
                    availableThreadCount.decrementAndGet();
                    commandToExecute.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
