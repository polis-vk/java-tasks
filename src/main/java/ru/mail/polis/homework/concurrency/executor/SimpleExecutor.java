package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

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
            Thread.currentThread().interrupt();
        }
        Worker freeWorker = getFreeThread();
        if (freeWorker == null) {
            freeWorker = new Worker();
            threadPool.add(freeWorker);
            freeWorker.start();
        } else {
            freeWorker.notifyWorker();
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

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadPool.size();
    }

    public class Worker extends Thread {

        private synchronized void notifyWorker() {
            notifyAll();
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        while (tasks.peek() == null) {
                            wait();
                        }
                        tasks.take().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }
}
