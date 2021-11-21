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

    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final CustomThread thread = new CustomThread();

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (thread.getRunningStatus()) {
            queue.add(command);
        } else {
            throw new RejectedExecutionException();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        thread.setFlushTrue();
        thread.setRunningFalse();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        thread.setRunningFalse();
        thread.interrupt();
    }

    private class CustomThread extends Thread {

        private boolean running = true;
        private boolean flush = false;

        @Override
        public void run() {
            while (running) {
                if (!queue.isEmpty()) {
                    queue.poll().run();
                }
            }
            if (flush) {
                for (Runnable runnable : queue) {
                    runnable.run();
                }
            }
        }

        public void setRunningFalse() {
            running = false;
        }

        public boolean getRunningStatus() {
            return running;
        }

        public void setFlushTrue() {
            flush = true;
        }
    }
}
