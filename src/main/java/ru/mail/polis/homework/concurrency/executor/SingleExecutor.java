package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Thread thread;
    private final BlockingQueue<Runnable> blockingQueue;
    private volatile boolean flag = false;

    public SingleExecutor() {
        blockingQueue = new LinkedBlockingQueue<>();
        thread = new Thread(() -> {
            try {
                while (flag || !blockingQueue.isEmpty()) {
                    Runnable task = blockingQueue.take();
                    task.run();
                }
            } catch (InterruptedException exception) {
                exception.getMessage();
            }
        });
        flag = true;
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        checkIsShutDown();
        blockingQueue.offer(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        flag = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        thread.interrupt();
    }

    private void checkIsShutDown() {
        if (!flag) {
            throw new RejectedExecutionException();
        }
    }
}
