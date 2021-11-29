package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;
import java.util.concurrent.Executor;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final BlockingQueue<Runnable> queueOfTasks = new LinkedBlockingQueue<>();
    private volatile boolean isRunning = true;
    private final EternalThread singleThread = new EternalThread();

    public SingleExecutor() {
        singleThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!isRunning) {
            throw new RejectedExecutionException();
        }
        queueOfTasks.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        isRunning = false;
        singleThread.interrupt();
    }

    private class EternalThread extends Thread {

        @Override
        public void run() {
            try {
                while (isRunning || !queueOfTasks.isEmpty()) {
                    queueOfTasks.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
