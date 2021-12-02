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
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final Thread thread = new Thread(new Task());
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private volatile boolean isShutdown = false;

    private class Task implements Runnable {
        @Override
        public void run() {
            Runnable active;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    active = getTask();
                    if (active == null) {
                        break;
                    }
                    active.run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException("Adding new tasks while running others");
        }

        if (command == null) {
            throw new IllegalArgumentException("Illegal null-command");
        }

        addTask(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public synchronized void shutdownNow() {
        if (isShutdown) {
            return;
        }
        shutdown();
        thread.interrupt();
    }

    private void addTask(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException ignored) {
        }
    }

    private Runnable getTask() throws InterruptedException {
        return isShutdown ? tasks.poll() : tasks.take();
    }

}
