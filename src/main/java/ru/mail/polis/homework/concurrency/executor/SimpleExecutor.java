package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.Set;
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
    private int capacity;
    private int nowThreads = 0;
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Set<Worker> workers = new HashSet<>();

    public SimpleExecutor(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.capacity = capacity;
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) throw new NullPointerException();
        boolean freeWorker = false;
        for (Worker worker : workers) {
            if (worker.getState() == Thread.State.WAITING) {
                freeWorker = true;
                break;
            }
        }
        if (!freeWorker && nowThreads < capacity) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
            nowThreads++;
        }
        queue.offer(command);
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    queue.take().run();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return nowThreads;
    }
}
