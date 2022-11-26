package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private ArrayDeque<Runnable> commands = new ArrayDeque<>();
    private volatile boolean isRunning = true;
    private Thread thread;

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            commands.addLast(command);
        } else {
            throw new RejectedExecutionException();
        }
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
        thread.interrupt();
    }

    public SingleExecutor() {
        thread = new Thread(() -> existCommand());
        thread.start();
    }

    private void existCommand () {
        while (true) {
            if (commands.isEmpty() && !isRunning) {
                break;
            }

            if (!commands.isEmpty()) {
                commands.pollFirst().run();
            }
        }
    }
}
