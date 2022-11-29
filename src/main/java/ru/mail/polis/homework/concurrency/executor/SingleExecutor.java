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

    private final BlockingQueue<Runnable> commands;
    private final Thread thread;
    private volatile boolean running;

    public SingleExecutor() {
        commands = new LinkedBlockingQueue<>();
        thread = new Thread(this::runCommand);
        thread.start();
        running = true;
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (running) {
            commands.add(command);
        } else {
            throw new RejectedExecutionException();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        running = false;
        thread.interrupt();
    }

    private void runCommand () {
        while (!commands.isEmpty() || running) {
            try {
                if (!commands.isEmpty()) {
                    commands.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
