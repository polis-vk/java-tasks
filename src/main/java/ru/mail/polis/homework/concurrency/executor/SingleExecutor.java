package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
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
    private volatile boolean isStopped;
    private final Thread thread = new Thread(this::run);
    private final BlockingQueue<Runnable> timetable = new LinkedBlockingQueue<>();

    public SingleExecutor() {
        thread.start();
    }

    @Override
    public void execute(Runnable command) {
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        try {
            timetable.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isStopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        timetable.clear();
        thread.interrupt();
    }

    private void run() {
        while (!timetable.isEmpty() || !isStopped) {
            try {
                timetable.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
