package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final Thread thread = new Thread(new Worker());
    private volatile boolean shutdownCalled = false;

    public SingleExecutor() {
        thread.start();
    }


    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownCalled) {
            throw new RejectedExecutionException();
        }

        workQueue.add(command);
    }


    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownCalled = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdownCalled = true;
        thread.interrupt();
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!shutdownCalled || !workQueue.isEmpty()) {
                    workQueue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}