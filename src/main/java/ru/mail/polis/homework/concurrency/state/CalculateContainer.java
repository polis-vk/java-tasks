package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
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

    private class Pair {
        State state;
        T result;

        Pair(State state, T result) {
            this.state = state;
            this.result = result;
        }
    }

    private AtomicReference<Pair> pair = new AtomicReference<>();

    public CalculateContainer(T result) {
        pair.set(new Pair(State.START, result));
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        Pair curVal;
        Pair newVal;
        do {
            curVal = pair.get();
            if (curVal.state == State.CLOSE) {
                System.out.println("Error. Container is closed.");
                return;
            }

            newVal = new Pair(State.INIT, initOperator.apply(curVal.result));
        }   while (curVal.state != State.START && curVal.state != State.FINISH
                    || !pair.compareAndSet(curVal, newVal));
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        Pair curVal;
        Pair newVal;
        do {
            curVal = pair.get();
            if (curVal.state == State.CLOSE) {
                System.out.println("Error. Container is closed.");
                return;
            }

            newVal = new Pair(State.RUN, runOperator.apply(curVal.result, value));
        }   while (curVal.state != State.INIT || !pair.compareAndSet(curVal, newVal));
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        Pair curVal;
        Pair newVal;
        do {
            curVal = pair.get();
            if (curVal.state == State.CLOSE) {
                System.out.println("Error. Container is closed.");
                return;
            }

            newVal = new Pair(State.FINISH, curVal.result);
        }   while (curVal.state != State.RUN || !pair.compareAndSet(curVal, newVal));
        finishConsumer.accept(curVal.result);
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        Pair curVal;
        Pair newVal;
        do {
            curVal = pair.get();
            if (curVal.state == State.CLOSE) {
                System.out.println("Error. Container is closed.");
                return;
            }

            newVal = new Pair(State.CLOSE, curVal.result);
        }   while (curVal.state != State.FINISH || !pair.compareAndSet(curVal, newVal));
        closeConsumer.accept(curVal.result);
    }

    public T getResult() {
        return pair.get().result;
    }

    public State getState() {
        return pair.get().state;
    }
}
