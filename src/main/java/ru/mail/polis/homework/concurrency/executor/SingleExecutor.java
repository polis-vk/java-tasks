package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Queue<Runnable> commands;
    private final Thread eternalThread;
    private boolean shutdownMode;

    public SingleExecutor() {
        commands = new LinkedList<>();
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
        shutdownMode = true;
        eternalThread.interrupt();
    }

    private class EternalThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted() && (!shutdownMode || !commands.isEmpty())) {
                if (!commands.isEmpty()) {
                    commands.poll().run();
                }
            }
        }
    }
}
