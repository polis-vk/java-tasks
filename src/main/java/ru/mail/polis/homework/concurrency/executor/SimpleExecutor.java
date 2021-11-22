package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
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

    private final List<Worker> threadPool;
    private boolean shutdown = false;
    private int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        this.maxThreadCount = maxThreadCount;
        threadPool = new ArrayList<>(maxThreadCount);
    }


    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (shutdown) {
            throw new RejectedExecutionException();
        }
        Optional<Worker> freeWorker = threadPool.stream().filter(Worker::isFree).findAny();
        if (freeWorker.isEmpty() && threadPool.size() < maxThreadCount) {
            Worker newWorker = new Worker();
            newWorker.addCommand(command);
            newWorker.start();
            threadPool.add(newWorker);
        } else if (freeWorker.isPresent()) {
            freeWorker.get().addCommand(command);
        } else {
            threadPool.stream().min(Comparator.comparingInt(Worker::commandsNumber)).get().addCommand(command);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdown = true;
        for (Worker worker : threadPool) {
            worker.shutdown();
        }
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown = true;
        for (Worker worker : threadPool) {
            worker.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }

    private static class Worker extends Thread {

        private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
        private boolean isWorking = false;
        private boolean shutdown = false;

        @Override
        public void run() {
            while (!shutdown) {
                while (!commands.isEmpty()) {
                    isWorking = true;
                    takeCommand();
                    isWorking = !commands.isEmpty();
                }
            }
            while (!commands.isEmpty()) {
                takeCommand();
            }
        }

        public void addCommand(Runnable command) {
            try {
                commands.put(command);
                isWorking = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void takeCommand() {
            try {
                commands.take().run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void shutdown() {
            this.shutdown = true;
        }

        public boolean isFree() {
            return !isWorking;
        }

        public int commandsNumber() {
            return commands.size();
        }
    }
}
