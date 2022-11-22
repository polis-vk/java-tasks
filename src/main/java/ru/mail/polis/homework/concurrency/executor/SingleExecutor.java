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
    private final BlockingQueue<Runnable> commands;
    private final Thread eternalThread;
    private boolean shutdownMode;

    public SingleExecutor() {
        commands = new LinkedBlockingQueue<>();
        shutdownMode = false;
        eternalThread = new EternalThread();
        eternalThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownMode) {
            throw new RejectedExecutionException();
        }
        commands.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        shutdownMode = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        while (!eternalThread.isInterrupted()) {
            eternalThread.interrupt();
        }
    }

    private class EternalThread extends Thread {
        @Override
        public void run() {
            while (!shutdownMode || !commands.isEmpty()) {
                Runnable command = null;
                try {
                    command = commands.take();
                } catch (InterruptedException e) {
                    return;
                }
                command.run();
            }
        }
    }
}
