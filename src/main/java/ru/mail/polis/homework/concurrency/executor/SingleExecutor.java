package ru.mail.polis.homework.concurrency.executor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    private final Thread thread = new Thread(this::run);
    private final Queue<Runnable> commands = new ConcurrentLinkedQueue<>();
    private boolean isShutDown = false;
    private boolean isFree = true;

    SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        if (command == null) {
            throw new NullPointerException();
        }

        commands.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        thread.interrupt();
    }

    public boolean isFree() {
        return isFree;
    }

    public int getCommandCount() {
        return commands.size();
    }

    private void run() {
        while (!isShutDown || !commands.isEmpty()) {
            if (!commands.isEmpty()) {
                isFree = false;
                commands.poll().run();
                isFree = commands.isEmpty();
            }
        }
    }
}
