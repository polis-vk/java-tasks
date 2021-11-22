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
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final Queue<Runnable> tasks = new LinkedList<>();
    private final Thread thread = new Worker();

    private boolean inProcess = true;

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (!inProcess) {
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
        inProcess = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        inProcess = false;
        thread.interrupt();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (inProcess || !tasks.isEmpty()) {
                if (tasks.peek() != null) {
                    tasks.poll().run();
                }
            }
            super.run();
        }
    }
}
