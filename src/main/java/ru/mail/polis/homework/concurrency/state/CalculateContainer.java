package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Потокобезопасный контейнер для вычислений. Контейнер создается с некторым дэфолтным значеним.
 * Далее значение инициализируется, вычисляется и отдается к потребителю. В каждом методе контейнер меняет состояние
 * и делает некоторое вычисление (которое передано ему в определенный метод)
 * <p>
 * Последовательность переходов из состояния в состояние строго определена:
 * START -> INIT -> RUN -> FINISH
 * Из состояния FINISH можно перейти или в состояние INIT или в состояние CLOSE.
 * CLOSE - конченое состояние.
 * <p>
 * Если какой-либо метод вызывается после перехода в состояние CLOSE
 * он должен написать ошибку (НЕ бросить) и сразу выйти.
 * Если вызван метод, который не соответствует текущему состоянию - он ждет,
 * пока состояние не станет подходящим для него (или ждет состояние CLOSE, чтобы написать ошибку и выйти)
 * <p>
 * <p>
 * Есть три варианта решения этой задачи.
 * 1) через методы wait and notify - 5 баллов
 * 2) через Lock and Condition - 5 баллов
 * 3) через операцию compareAndSet на Atomic классах - 9 баллов
 * Баллы за методы не суммируются, берется наибольший балл из всех методов. (то есть, если вы сделали 1 метод на 4 балла,
 * и 3 метод на 3 балла, я поставлю баллы только 4 балла)
 * <p>
 * Max 8 баллов
 */
public class CalculateContainer<T> {

    private volatile State state = State.START;

    private volatile T result;

    private final Lock lock = new ReentrantLock();

    private final Condition isStartOrFinishStatus = lock.newCondition();
    private final Condition isRunStatus = lock.newCondition();
    private final Condition isFinishStatus = lock.newCondition();
    private final Condition isCloseStatus = lock.newCondition();

    private final String errorMessage = "ERROR";

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        lock.lock();
        try {
            while (state != State.START && state != State.FINISH) {
                if (isCloseStatus()) {
                    return;
                }
                isStartOrFinishStatus.await();
            }
            result = initOperator.apply(result);
            changeStatus(State.INIT);

            isRunStatus.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        lock.lock();

        try {
            while (state != State.INIT) {
                if (isCloseStatus()) {
                    return;
                }
                isRunStatus.await();
            }

            result = runOperator.apply(result, value);
            changeStatus(State.RUN);

            isFinishStatus.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        lock.lock();
        try {
            while (state != State.RUN) {
                if (isCloseStatus()) {
                    return;
                }
                isFinishStatus.await();
            }
            finishConsumer.accept(result);
            changeStatus(State.FINISH);

            isStartOrFinishStatus.signal();
            isCloseStatus.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        lock.lock();

        try {
            while (state != State.FINISH) {
                if (isCloseStatus()) {
                    return;
                }
                isCloseStatus.await();
            }

            closeConsumer.accept(result);
            changeStatus(State.CLOSE);

            isStartOrFinishStatus.signal();
            isRunStatus.signal();
            isFinishStatus.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private boolean isCloseStatus() {
        if (state == State.CLOSE) {
            System.out.println(errorMessage);
            return true;
        }
        return false;
    }

    private void changeStatus(State newState)
    {
        state = newState;
    }

    public T getResult() {
        return result;
    }

    public State getState() {
        return state;
    }
}
