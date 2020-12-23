package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

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
    private final int MAX_THREADS;
    private final Queue<Runnable> queue = new LinkedBlockingQueue<>();
    private final List<Worker> pool;
    private boolean isRunning = true;

    public SimpleExecutor(int threadsAmount) {
        MAX_THREADS = threadsAmount;
        pool = new ArrayList<>(threadsAmount);
    }

    @Override
    public void execute(Runnable command) {
        if (isRunning) {
            queue.offer(command);
            for (Worker worker : pool) {
                if (worker.getState() == Thread.State.WAITING) {
                    worker.myNotify();
                    return;
                }
            }


            if (pool.size() < MAX_THREADS) {
                Worker temp = new Worker();
                pool.add(temp);
                temp.start();
            } else {
                notifyThreads();
            }
        }
    }

    private void notifyThreads() {
        for (Worker worker : pool) {
            if (worker.getState() == Thread.State.WAITING) {
                worker.myNotify();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return pool.size();
    }

    public void shutdown() {
        isRunning = false;
        notifyThreads();
    }

    private final class Worker extends Thread {

        @Override
        public synchronized void run() {
            while (isRunning) {
                Runnable task = queue.poll();
                if (task != null) {
                    task.run();
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private synchronized void myNotify() {
            this.notify();
        }
    }
}
