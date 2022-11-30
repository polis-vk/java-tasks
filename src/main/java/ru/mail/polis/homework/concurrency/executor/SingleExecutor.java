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
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Thread thread;
    private final BlockingQueue<Runnable> commands;
    private volatile boolean isShutDown;

    public SingleExecutor() {
        this.isShutDown = false;
        this.commands = new LinkedBlockingQueue<>();
        this.thread = new Thread(() -> {
            while (!isShutDown || !commands.isEmpty()) {
                if (commands.peek() != null)
                    commands.poll().run();
            }
        });
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }
        if (!isShutDown) {
            try {
                commands.put(command);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new RejectedExecutionException();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        thread.interrupt();
    }
}