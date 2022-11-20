package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final MyThread thread = new MyThread();
    private final Queue<Runnable> tasks = new LinkedList<>();
    private boolean terminated;

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (terminated) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        terminated = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        thread.interrupt();
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (!isInterrupted() && (!terminated || !tasks.isEmpty())) {
                task = tasks.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}

