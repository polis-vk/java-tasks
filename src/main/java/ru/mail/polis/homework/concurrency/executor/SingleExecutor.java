package ru.mail.polis.homework.concurrency.executor;

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
    private final LinkedBlockingQueue<Runnable> queue;
    private volatile boolean shutDown;
    private final EternalTread thread;

    public SingleExecutor() {
        queue = new LinkedBlockingQueue<>();
        thread = new EternalTread();
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (shutDown) {
            throw new RejectedExecutionException();
        }
        this.queue.add(command);
    }

    private class EternalTread extends Thread {
        @Override
        public void run() {
            while (!shutDown || !queue.isEmpty()) {
                Runnable command;
                try {
                    command = queue.take();
                } catch (InterruptedException e) {
                    return;
                }
                command.run();
            }
        }
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
        shutdown();
        thread.interrupt();
    }
}
