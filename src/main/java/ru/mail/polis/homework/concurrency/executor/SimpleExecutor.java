package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>();
    private final HashSet<Worker> pool = new HashSet<>();

    private final AtomicInteger countLiveThreads = new AtomicInteger(0);
    private final int capacity;
    private volatile boolean running;

    private final long TIMEOUT = 40;
    private final Thread.State WAITING = Thread.State.WAITING;

    public SimpleExecutor(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("capacity < 1");
        }
        this.capacity = capacity;
        running = true;
    }

    public void off() {
        running = false;
    }

    @Override
    public void execute(Runnable command) {
        if (running) {
            if (command == null) {
                throw new NullPointerException();
            }

            boolean existFreeWorker = false;
            for (Worker worker : pool) {
                if (worker.getState() == WAITING) {
                    worker.notifyThreads();
                    existFreeWorker = true;
                    break;
                }
            }

            if (!existFreeWorker && pool.size() < capacity) {
                addWorker();
            }
            queue.offer(command);
        }
    }

    private void addWorker() {
        Worker w = new Worker();
        pool.add(w);
        w.start();
    }

    /*private Worker getFreeWorker() {
        for (Worker worker : pool) {
            if (worker.getState() == WAITING) {
                return worker;
            }
        }
        return null;
    }*/

    private class Worker extends Thread {
        private final int num;

        Worker() {
            num = countLiveThreads.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                while (running) {
                    queue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void notifyThreads() {
            notifyAll();
        }
    }

    public int getLiveThreadsCount() {
        return countLiveThreads.get();
    }
}
