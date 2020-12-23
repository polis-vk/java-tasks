package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.atomic.AtomicBoolean;
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

    private T result;
    private final AtomicReference<State> state = new AtomicReference<>(State.START);

    private final AtomicBoolean changedInit = new AtomicBoolean(true);
    private final AtomicBoolean changedRun = new AtomicBoolean(false);
    private final AtomicBoolean changedFinish = new AtomicBoolean(false);
    private final AtomicBoolean changedClose = new AtomicBoolean(false);

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        while (true) {
            if (changedInit.get() && (state.compareAndSet(State.FINISH, State.INIT) || state.compareAndSet(State.START, State.INIT))) {
                result = initOperator.apply(result);
                changedInit.set(false);
                changedRun.set(true);
                return;
            } else if (state.get() == State.CLOSE) {
                System.err.println("'init' called when the state is 'CLOSE'");
                return;
            }
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        while (true) {
            if (changedRun.get() && state.compareAndSet(State.INIT, State.RUN)) {
                result = runOperator.apply(result, value);
                changedRun.set(false);
                changedFinish.set(true);
                return;
            } else if (state.get() == State.CLOSE) {
                System.err.println("'close' called when the state is 'CLOSE'");
                return;
            }
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        while (true) {
            if (changedFinish.get() && state.compareAndSet(State.RUN, State.FINISH)) {
                finishConsumer.accept(result);
                changedFinish.set(false);
                changedClose.set(true);
                changedInit.set(true);
                return;
            } else if (state.get() == State.CLOSE) {
                System.err.println("'close' called when the state is 'CLOSE'");
                return;
            }
        }
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        while (true) {
            if (changedClose.get() && state.compareAndSet(State.FINISH, State.CLOSE)) {
                closeConsumer.accept(result);
                changedInit.set(false);
                changedRun.set(false);
                changedFinish.set(false);
                changedClose.set(false);
                return;
            } else if (state.get() == State.CLOSE) {
                System.err.println("'close' called when the state is 'CLOSE'");
                return;
            }
        }
    }

    public T getResult() {
        return result;
    }

    public State getState() {
        return state.get();
    }
}
