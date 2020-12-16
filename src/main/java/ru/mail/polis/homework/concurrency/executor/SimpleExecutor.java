package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

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
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final List<Worker> workers = new ArrayList<>();
    private boolean isRunnable = true;
    private final int size;

    public SimpleExecutor() {
        size = 10;
    }

    public SimpleExecutor(int n) {
        this.size = n;
    }

    @Override
    public void execute(Runnable command) {
        workQueue.offer(command);
        Worker freeWorker = getFreeWorker();
        if (freeWorker == null && size > workers.size()) {
            Worker newWorker = new Worker();
            workers.add(newWorker);
            newWorker.start();
        } else {
            while ((freeWorker = getFreeWorker()) != null) {
                freeWorker.notifyWorker();
            }
        }
    }

    private Worker getFreeWorker() {
        Worker free = null;
        for (Worker worker : workers) {
            if (worker.getState() == Thread.State.WAITING) {
                free = worker;
                break;
            }
        }
        return free;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    public void shutdown() {
        isRunnable = false;
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            while (isRunnable) {
                try {
                    synchronized (this) {
                        Runnable nextTask ;
                        while ((nextTask = workQueue.poll()) == null) {
                            wait();
                        }
                        nextTask.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void notifyWorker() {
            synchronized (this) {
                this.notifyAll();
            }
        }
    }
}
