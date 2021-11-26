package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
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

    private final Lock lock = new ReentrantLock();
    private final Thread thread = new Thread(new Task());
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>(5);

    private boolean isShutdown = false;

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        lock.lock();
        if (isShutdown) {
            throw new RejectedExecutionException("Adding new tasks while running others");
        }
        lock.unlock();
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
        lock.lock();
        isShutdown = true;
        lock.unlock();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
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
