package ru.mail.polis.homework.concurrency.executor;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private final BlockingQueue<Runnable> tasks;
    private final List<Worker> workers;
    private volatile boolean isRunning = true;


    public SimpleExecutor(int limitOfThreads) {
        if (limitOfThreads < 1) {
            throw new IllegalArgumentException();
        }
        this.limitOfThreads = limitOfThreads;
        tasks = new LinkedBlockingQueue<>();
        workers = new ArrayList<>(limitOfThreads);
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            Worker freeWorker = null;
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                for (Worker worker : workers) {
                    if (worker.getState() == Thread.State.WAITING) {
                        freeWorker = worker;
                        worker.notifyAll();
                        break;
                    }
                }
                if (freeWorker == null && limitOfThreads > workers.size()) {
                    addNewWorker();
                }
            } finally {
                lock.unlock();
            }
            tasks.offer(command);
        }
    }

    private synchronized void addNewWorker() {
        Worker worker = new Worker();
        worker.start();
        workers.add(worker);
    }

    public void shutDown() {
        isRunning = false;
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
                Runnable task = tasks.poll();
                if(task != null) {
                    task.run();
                }
            }
        }
    }
}
