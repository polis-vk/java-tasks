package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
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
    private final BlockingQueue<Runnable> commandQueue = new LinkedBlockingQueue<>();
    private volatile boolean isShutdown = false;
    private final MyThread thread = new MyThread();

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        commandQueue.add(command);
        if(!thread.isAlive()){
            thread.start();
        }
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
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isShutdown = true;
        thread.interrupt();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !commandQueue.isEmpty()) {
                Runnable command;
                try {
                    command = commandQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                command.run();
            }
        }
    }
}
