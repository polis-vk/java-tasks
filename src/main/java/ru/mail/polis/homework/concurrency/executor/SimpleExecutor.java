package ru.mail.polis.homework.concurrency.executor;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final Deque<Thread> threads = new LinkedList<>();
    private final BlockingDeque<Runnable> tasks = new LinkedBlockingDeque<>();
    private final AtomicBoolean stop = new AtomicBoolean(false);
    private final int maxThreadAmount;

    public SimpleExecutor(int threadsAmount) {
        maxThreadAmount = threadsAmount;
    }

    private Thread initNewThread() {
        return new Thread(() -> {
            while (!stop.get()) {
                try {
                    tasks.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        AtomicInteger runningThreadCounter = new AtomicInteger(0);
        for (Thread thread : threads) {
            if (!thread.getState().equals(Thread.State.WAITING)) {
                runningThreadCounter.incrementAndGet();
            }
        }
        if (runningThreadCounter.get() == threads.size() && threads.size() < maxThreadAmount) {
            threads.offer(initNewThread());
            threads.getLast().start();
        }
        tasks.offer(command);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }
}
