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
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Thread thread = new Worker();
    private boolean inWork = true;

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (!inWork) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        inWork = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        inWork = false;
        thread.interrupt();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (inWork || !tasks.isEmpty()) {
                if (tasks.peek() != null) {
                    tasks.poll().run();
                }
            }
            super.run();
        }
    }
}