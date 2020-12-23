package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.atomic.AtomicReference;
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

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public synchronized void init(UnaryOperator<T> initOperator) {
        while (!isNeededState(State.START) && !isNeededState(State.FINISH)) {
            if (needStop()) {
                return;
            }

            waitForStateChanges();
        }

        result = initOperator.apply(result);
        changeState(State.INIT);
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public synchronized void run(BinaryOperator<T> runOperator, T value) {
        while (!isNeededState(State.INIT)) {
            if (needStop()) {
                return;
            }

            waitForStateChanges();
        }

        result = runOperator.apply(value, result);
        changeState(State.RUN);
    }

    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public synchronized void finish(Consumer<T> finishConsumer) {
        while (!isNeededState(State.RUN)) {
            if (needStop()) {
                return;
            }

            waitForStateChanges();
        }

        finishConsumer.accept(result);
        changeState(State.FINISH);
    }

    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public synchronized void close(Consumer<T> closeConsumer) {
        while (!isNeededState(State.FINISH)) {
            if (needStop()) {
                return;
            }

            waitForStateChanges();
        }

        closeConsumer.accept(result);
        changeState(State.CLOSE);
    }

    public T getResult() {
        return result;
    }

    public State getState() {
        return state;
    }

    private boolean needStop() {
        if (state.equals(State.CLOSE)) {
            System.err.println("ОШИБКА");
            return true;
        }

        return false;
    }

    private synchronized void waitForStateChanges() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isNeededState(State state) {
        return this.state.equals(state);
    }

    private synchronized void changeState(State state) {
        this.state = state;
        this.notifyAll();
    }
}
