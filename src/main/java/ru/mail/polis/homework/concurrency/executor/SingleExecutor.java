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
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final Worker worker = new Worker();
    private volatile boolean acceptingNew = true;

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (!acceptingNew) {
            throw new RejectedExecutionException();
        }

        workQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        acceptingNew = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        acceptingNew = false;
        worker.thread.interrupt();
    }

    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        try {
            while ((acceptingNew || w.task != null || (w.task = workQueue.poll()) != null)
                    && (w.task != null || (w.task = getTask()) != null)) {
                try {
                    w.task.run();
                } finally {
                    w.task = null;
                }
            }
            w.thread.interrupt();
        } catch (InterruptedException e) {
            w.thread.interrupt();
        }
    }

    // Here may be realization with workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS)
    private Runnable getTask() throws InterruptedException {
        return workQueue.take();
    }

    private final class Worker implements Runnable {
        final Thread thread;
        Runnable task;

        public Worker() {
            this.thread = Executors.defaultThreadFactory().newThread(this);
            task = null;
            thread.start();
        }

        private Worker(Runnable r) {
            this.thread = Executors.defaultThreadFactory().newThread(this);
            task = r;
            thread.start();
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }
}