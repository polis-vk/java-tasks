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

    private volatile boolean addMode = true;
    private final CustomThread thread = new CustomThread();
    private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (addMode) {
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
        addMode = false;
        thread.setStop();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        addMode = false;
        thread.interrupt();
    }

    private class CustomThread extends Thread {

        private boolean stop = false;

        @Override
        public void run() {
            try {
                while (!stop || !commands.isEmpty()) {
                    commands.take().run();
                }
            } catch (InterruptedException e) {
                System.out.println("ShutdownNow");
            }
        }

        public void setStop() {
            stop = true;
        }
    }
}
