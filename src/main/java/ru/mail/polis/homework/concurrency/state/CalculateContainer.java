package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.atomic.AtomicReference;
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
    private final AtomicReference<State> state = new AtomicReference<>(State.START);

    private final AtomicReference<T> result = new AtomicReference<>();

    public CalculateContainer(T result) {
        this.result.set(result);
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        if (!waitForStateAndSet(State.INIT, state -> state == State.START || state == State.FINISH)) {
            System.err.printf("Error: Cannot proceed from state CLOSE to state INIT (inside `init` method of %s container).\n", this);
            return;
        }

        T readResult = result.get();
        while (!result.compareAndSet(readResult, initOperator.apply(readResult))) {
            readResult = result.get();
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        if (!waitForStateAndSet(State.RUN, state -> state == State.INIT)) {
            System.err.printf("Error: Cannot proceed from state CLOSE to state RUN (inside `run` method of %s container).\n", this);
            return;
        }

        T readResult = result.get();
        while (!result.compareAndSet(readResult, runOperator.apply(readResult, runOperator.apply(readResult, value)))) {
            readResult = result.get();
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        if (!waitForStateAndSet(State.FINISH, state -> state == State.RUN)) {
            System.err.printf("Error: Cannot proceed from state CLOSE to state FINISH (inside `finish` method of %s container).\n", this);
            return;
        }

        finishConsumer.accept(result.get());
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        if (!waitForStateAndSet(State.CLOSE, state -> state == State.FINISH)) {
            System.err.printf("Error: Cannot perform close method twice (inside `close` method of %s container).\n", this);
            return;
        }

        closeConsumer.accept(result.get());
    }


    public T getResult() {
        return result.get();
    }

    public State getState() {
        return state.get();
    }

    private boolean waitForStateAndSet(State newState, Predicate<State> conditionToWait) {
        State readState = state.get();
        while (true) {
            while (!conditionToWait.test(readState)) {
                readState = state.get();
                if (readState == State.CLOSE) {
                    return false;
                }
            }
            if (state.compareAndSet(readState, newState)) {
                break;
            }
        }
        return true;
    }
}
