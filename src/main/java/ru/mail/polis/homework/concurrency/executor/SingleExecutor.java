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

    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(5);
    private Runnable active;
    private final Thread thread = new Thread(() -> {
        System.out.println("Is in Runnable object");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                active = blockingQueue.take();
            } catch (InterruptedException e) {
                return;
            }
            System.out.println("RUNNING begin");
            active.run();
            System.out.println("RUNNING end");
        }
    });

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        System.out.println("Is in execute");
        if (active != null || thread.isInterrupted()) {
            throw new RejectedExecutionException("Adding new tasks while running others");
        }
        try {
            blockingQueue.put(command);
            System.out.println("Added new command");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        System.out.println("Start");
        thread.start();
        thread.interrupt();
        System.out.println("End");
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        System.out.println("ShutdownNow");
        thread.interrupt();
    }

}
