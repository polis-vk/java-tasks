package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingDeque<Runnable> tasksQueue;
    private boolean isShutdown;
    private MainThread mainThread;

    public SingleExecutor() {
        tasksQueue = new LinkedBlockingDeque<>();
        mainThread = new MainThread();
        mainThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException("NULL command");
        }
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        tasksQueue.offer(command);
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
    public void shutdownNow() {
        isShutdown = true;
        mainThread.interrupt();
    }

    private class MainThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isShutdown || !tasksQueue.isEmpty() || !isInterrupted()) {
                    tasksQueue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
