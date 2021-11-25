package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final List<Worker> workers = new LinkedList<>();
    private final int maxThreadCount;
    private boolean running = true;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (!running) {
            throw new RejectedExecutionException();
        }
        for (Worker worker : workers) {
            if (worker.commands.isEmpty()) {
                worker.commands.add(command);
                return;
            }
        }
        if (maxThreadCount > workers.size()) {
            Execution execution = new Execution();
            execution.commands.add(command);
            Worker worker = new Worker(execution);
            worker.runner.start();
            workers.add(worker);
            return;
        }
        Worker freestWorker = workers.get(0);
        for (Worker worker : workers) {
            if (worker.commands.size() < freestWorker.commands.size()) {
                freestWorker = worker;
            }
        }
        freestWorker.commands.add(command);
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
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        for (Worker worker : workers) {
            worker.runner.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private static class Worker {
        public final Thread runner;
        public final Queue<Runnable> commands;

        public Worker(Execution executor) {
            runner = new Thread(executor);
            commands = executor.commands;
        }
    }

    private class Execution implements Runnable {
        public final Queue<Runnable> commands = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
            while (running) {
                while (!commands.isEmpty()) {
                    commands.peek().run();
                    commands.remove();
                }
            }
        }
    }
}
