package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final ConcurrentLinkedQueue<Runnable> queueOfTasks = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean canAdd = new AtomicBoolean(true);
    private final EternalThread singleThread = new EternalThread();

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!canAdd.get()) {
            throw new RejectedExecutionException();
        }
        queueOfTasks.offer(command);
        if (singleThread.getState() == Thread.State.NEW) {
            singleThread.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        canAdd.set(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        canAdd.set(false);
        singleThread.interrupt();
    }

    private class EternalThread extends Thread {

        @Override
        public void run() {
            while (canAdd.get() || !queueOfTasks.isEmpty()) {
                if (!queueOfTasks.isEmpty()) {
                    queueOfTasks.poll().run();
                }
            }
        }
    }
}
