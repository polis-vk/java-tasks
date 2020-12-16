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

    private final AtomicReference<State> state;
    private final AtomicBoolean finished;
    private final AtomicReference<T> result;

    public CalculateContainer(T result) {
        this.result = new AtomicReference<>(result);
        this.state = new AtomicReference<>(State.START);
        this.finished = new AtomicBoolean(false);
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        if (finished.get()) {
            System.out.println("Error, calling method after close");
            return;
        }
        synchronized (state) {
            synchronized (result) {
                if (state.compareAndSet(State.START, State.INIT) || state.compareAndSet(State.FINISH, State.INIT)) {
                    System.out.println("In init: " + Thread.currentThread().getName());
                    result.set(initOperator.apply(result.get()));
                }
            }
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public synchronized void run(BinaryOperator<T> runOperator, T value) {
        if (finished.get()) {
            System.out.println("Error, calling method after close");
            return;
        }
        if (state.compareAndSet(State.INIT, State.RUN)) {
            result.set(runOperator.apply(result.get(), value));
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public synchronized void finish(Consumer<T> finishConsumer) {
        if (finished.get()) {
            System.out.println("Error, calling method after close");
            return;
        }
        if (state.compareAndSet(State.RUN, State.FINISH)) {
            finishConsumer.accept(result.get());
        }
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public synchronized void close(Consumer<T> closeConsumer) {
        if (state.compareAndSet(State.FINISH, State.CLOSE)) {
            closeConsumer.accept(result.get());
            finished.set(true);
        }
    }


    public AtomicReference<T> getResult() {
        return result;
    }

    public AtomicReference<State> getState() {
        return state;
    }
}
