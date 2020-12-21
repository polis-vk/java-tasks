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

    private class Node {
        public State state;
        public T result;

        Node(State state, T result) {
            this.state = state;
            this.result = result;
        }
    }

    private final AtomicReference<Node> data = new AtomicReference<>();

    public CalculateContainer(T result) {
        data.set(new Node(State.START, result));
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        Node cur;
        Node newData;
        do {
            cur = data.get();
            while ((cur.state != State.START) && (cur.state != State.FINISH)) {
                cur = data.get();
                if (cur.state == State.CLOSE) {
                    System.out.println("Closed");
                    return;
                }
            }
            T newResult = initOperator.apply(cur.result);
            newData = new Node(State.INIT, newResult);

        } while (!data.compareAndSet(cur, newData));
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        Node cur;
        Node newData;
        do {
            cur = data.get();
            while (cur.state != State.INIT) {
                cur = data.get();
                if (cur.state == State.CLOSE) {
                    System.out.println("Closed");
                    return;
                }
            }
            T newResult = runOperator.apply(cur.result, value);
            newData = new Node(State.RUN, newResult);

        } while (!data.compareAndSet(cur, newData));
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        Node cur;
        Node newData;
        do {
            cur = data.get();
            while (cur.state != State.RUN) {
                cur = data.get();
                if (cur.state == State.CLOSE) {
                    System.out.println("Closed");
                    return;
                }
            }
            newData = new Node(State.FINISH, cur.result);
        } while (!data.compareAndSet(cur, newData));

        finishConsumer.accept(newData.result);
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        Node cur;
        Node newData;
        do {
            cur = data.get();
            while (cur.state != State.FINISH) {
                cur = data.get();
                if (cur.state == State.CLOSE) {
                    System.out.println("Closed");
                    return;
                }
            }
            newData = new Node(State.CLOSE, cur.result);
        } while (!data.compareAndSet(cur, newData));

        closeConsumer.accept(newData.result);
    }

    public T getResult() {
        return data.get().result;
    }

    public State getState() {
        synchronized (data) {
            return data.get().state;
        }
    }
}
