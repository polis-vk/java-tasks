package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;


/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 *
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */
public class SimpleExecutor implements Executor {

    private static final int DEFAULT_MAX_WORKERS_NUMBER = 5;

    private final Queue<Runnable> workQueue;
    private final List<Worker> workersList;
    private boolean shutDowned;
    private final int maxWorkersNumber;

    public SimpleExecutor(int maxWorkersNumber) {
        workQueue = new ArrayDeque<>();
        workersList = new ArrayList<>(maxWorkersNumber);
        shutDowned = false;
        this.maxWorkersNumber = maxWorkersNumber;
    }

    public SimpleExecutor() {
        this(DEFAULT_MAX_WORKERS_NUMBER);
    }

    @Override
    public void execute(Runnable command) {
        workQueue.offer(command);
        Worker worker = getWorkerFromList();
        if (worker == null && maxWorkersNumber > workersList.size()) {
            worker = new Worker();
            workersList.add(worker);
            worker.start();
        } else {
            while ((worker = getWorkerFromList()) != null) {
                worker.notifyAllWorkers();
            }
        }
    }

    private Worker getWorkerFromList() {
        Worker worker = null;
        for (Worker w : workersList) {
            if (w.getState() == Thread.State.WAITING) {
                worker = w;
                break;
            }
        }
        return worker;
    }

    public void shutdown() {
        shutDowned = true;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workersList.size();
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            while (!shutDowned) {
                synchronized (this) {
                    try {
                        Runnable nextTask = workQueue.poll();
                        while (nextTask == null) {
                            wait();
                            nextTask = workQueue.poll();
                        }
                        nextTask.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public synchronized void notifyAllWorkers() {
            notifyAll();
        }
    }
}
