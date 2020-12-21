package ru.mail.polis.homework.concurrency.executor;


import java.util.*;
import java.util.concurrent.*;

/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 * <p>
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */
public class SimpleExecutor implements Executor {

    private final int limitOfThreads;
    private final Queue<Runnable> tasks;
    private final List<Worker> workers;
    private volatile boolean isRunning = true;


    public SimpleExecutor(int limitOfThreads) {
        if (limitOfThreads < 1) {
            throw new IllegalArgumentException();
        }
        this.limitOfThreads = limitOfThreads;
        tasks = new ConcurrentLinkedQueue<>();
        workers = new ArrayList<>(limitOfThreads);
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            Worker freeWorker = getWorker();
            if (freeWorker == null && limitOfThreads > workers.size()) {
                tasks.offer(command);
                addNewWorker();
            } else {
                tasks.offer(command);
                while ((freeWorker = getWorker()) != null) {
                    freeWorker.notifyWorker();
                }
            }
        }
    }

    private Worker getWorker() {
        Worker freeWorker = null;
        for (Worker worker : workers) {
            if (worker.getState() == Thread.State.WAITING) {
                freeWorker = worker;
                break;
            }
        }
        return freeWorker;
    }

    private synchronized void addNewWorker() {
        Worker worker = new Worker();
        worker.start();
        workers.add(worker);
    }

    public void shutDown() {
        isRunning = false;
        synchronized (this) {
            notifyAll();
        }
    }

    public boolean isDone() {
        for (Worker worker : workers) {
            if (worker.getState() != Thread.State.WAITING) {
                return false;
            }
        }
        return true;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private final class Worker extends Thread {
        @Override
        public void run() {
            while (isRunning) {
                try {
                    if (tasks.isEmpty()) {
                        wait();
                    } else {
                        tasks.poll().run();
                    }
                } catch (Exception ignored) {

                }
            }
        }

        public void notifyWorker() {
            synchronized (this) {
                notifyAll();
            }
        }
    }
}

