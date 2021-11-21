package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.Executor;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final Queue<Runnable> queueOfTasks = new ArrayDeque<>();
    private boolean canAdd = true;
    private final EternalThread singleThread = new EternalThread();

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!canAdd) {
            throw new RejectedExecutionException();
        }
        queueOfTasks.add(command);
        if (singleThread.getState() == Thread.State.NEW) {
            singleThread.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        canAdd = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        canAdd = false;
        singleThread.interrupt();
    }

    private class EternalThread extends Thread {

        @Override
        public void run() {
            while (canAdd || !queueOfTasks.isEmpty()) {
                if (!queueOfTasks.isEmpty()) {
                    queueOfTasks.poll().run();
                }
            }
        }
    }
}
