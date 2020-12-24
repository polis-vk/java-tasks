package ru.mail.polis.homework.concurrency.state;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Потокобезопасный контейнер для вычислений. Контейнер создается с некторым дэфолтным значеним.
 * Далее значение инициализируется, вычисляется и отдается к потребителю. В каждом методе контейнер меняет состояние
 * и делает некоторое вычисление (которое передано ему в определенный метод)
 *
 * Последовательность переходов из состояния в состояние строго определена:
 * START -> INIT -> RUN -> FINISH
 * Из состояния FINISH можно перейти или в состояние INIT или в состояние CLOSE.
 * CLOSE - конченое состояние.
 *
 * Если какой-либо метод вызывается после перехода в состояние CLOSE
 * он должен написать ошибку (НЕ бросить) и сразу выйти.
 * Если вызван метод, который не соответствует текущему состоянию - он ждет,
 * пока состояние не станет подходящим для него (или ждет состояние CLOSE, чтобы написать ошибку и выйти)
 *
 *
 * Есть три варианта решения этой задачи.
 * 1) через методы wait and notify - 5 баллов
 * 2) через Lock and Condition - 5 баллов
 * 3) через операцию compareAndSet на Atomic классах - 9 баллов
 * Баллы за методы не суммируются, берется наибольший балл из всех методов. (то есть, если вы сделали 1 метод на 4 балла,
 * и 3 метод на 3 балла, я поставлю баллы только 4 балла)
 *
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
        try {
            if (!waitForStateAndSet(State.INIT, state -> state == State.START || state == State.FINISH)) {
                System.err.printf("Error: Cannot proceed from state CLOSE to state INIT (inside `init` method of %s container).\n", this);
                return;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to initialize.");
            return;
        }
        result = initOperator.apply(result);
        this.notifyAll();
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public synchronized void run(BinaryOperator<T> runOperator, T value) {
        try {
            if (!waitForStateAndSet(State.RUN, state -> state == State.INIT)) {
                System.err.printf("Error: Cannot proceed from state CLOSE to state RUN (inside `run` method of %s container).\n", this);
                return;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to run.");
            return;
        }
        result = runOperator.apply(result, value);
        this.notifyAll();
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public synchronized void finish(Consumer<T> finishConsumer) {
        try {
            if (!waitForStateAndSet(State.FINISH, state -> state == State.RUN)) {
                System.err.printf("Error: Cannot proceed from state CLOSE to state FINISH (inside `finish` method of %s container).\n", this);
                return;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to finish.");
            return;
        }
        finishConsumer.accept(result);
        this.notifyAll();
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        try {
            if (!waitForStateAndSet(State.CLOSE, state -> state == State.FINISH)) {
                System.err.printf("Error: Cannot perform close method twice (inside `close` method of %s container).\n", this);
                return;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to close.");
            return;
        }
        closeConsumer.accept(result);
        this.notifyAll();
    }


    public T getResult() {
        return result;
    }

    public State getState() {
        return state;
    }

    private synchronized boolean waitForStateAndSet(State newState, Predicate<State> conditionToWait) throws InterruptedException {
        while (!conditionToWait.test(state) && state != State.CLOSE) {
            this.wait();
        }
        if (state == State.CLOSE) {
            System.err.printf("Error: Cannot proceed from state CLOSE to state INIT (inside `init` method of %s container).\n", this);
            return false;
        }
        state = newState;
        return true;
    }
}
