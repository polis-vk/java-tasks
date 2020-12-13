package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

/**
 * Нужно сделать свой экзекьютор с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
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

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Worker> threadPool = new ArrayList<>();


    @Override
    public void execute(Runnable command) {
        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Worker freeWorker = getFreeThread();
        if (freeWorker == null) {
            freeWorker = new Worker();
            threadPool.add(freeWorker);
            freeWorker.start();
        }
    }

    private Worker getFreeThread() {
        for (Worker worker : threadPool) {
            if (worker.getState() == Thread.State.WAITING) {
                return worker;
            }
        }
        return null;
    }

    public void waitAll() {
        boolean finished = false;
        while (true) {
            for (Thread executor : threadPool) {
                if (executor.getState() != Thread.State.WAITING) {
                    finished = false;
                    break;
                }
                finished = true;
            }
            if (finished)
                break;
        }
    }

    public List<Worker> getThreadPool() {
        return threadPool;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }

    public class Worker extends Thread {

        public Worker() {

        }

        public Worker(String name) {
            super(name);
        }

        public void run() {
            while (true) {
                try {
                    tasks.take().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
