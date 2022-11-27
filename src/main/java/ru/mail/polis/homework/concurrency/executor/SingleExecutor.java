package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final LinkedBlockingQueue<Runnable> queue;
    private volatile boolean isNotShutDown;
    private final EternalTread tread;
    SingleExecutor() {
        queue = new LinkedBlockingQueue<>();
        isNotShutDown = true;
        tread = new EternalTread();
        tread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!isNotShutDown) {
            throw new RejectedExecutionException();
        }
        queue.add(command);
    }
    private class EternalTread extends Thread {
        @Override
        public void run() {
            Runnable command;
            while (isNotShutDown || !queue.isEmpty()) {
                try {
                    command = queue.take();
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isNotShutDown = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        tread.interrupt();
    }
}
