package ru.mail.polis.homework.concurrency.executor;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> workerTasks = new LinkedBlockingQueue<>();
    private boolean shutDown;

    public SingleExecutor() {
        Thread worker = new Thread(new Worker());
        worker.start();
    }


    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutDown) {
            throw new RejectedExecutionException();
        }
        workerTasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutDown = true;
        Thread.currentThread().interrupt();
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    workerTasks.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
