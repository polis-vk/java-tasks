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

   // private State state = State.START;
    private volatile AtomicReference<State> atomicReference;
    private volatile AtomicReference<T> result;

    public CalculateContainer(T result) {
        this.result = new AtomicReference<>(result);
        atomicReference = new AtomicReference(State.START);;
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        while (true) {
            if (atomicReference.get() == State.CLOSE) {
                System.out.println("Ошибочка");
                return;
            }
            if (atomicReference.compareAndSet(State.START, State.INIT) || atomicReference.compareAndSet(State.FINISH, State.INIT)) {
                result.set(initOperator.apply(result.get()));
                return;
            }
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        while (true) {
            if (atomicReference.get() == State.CLOSE) {
                System.out.println("Ошибочка");
                return;
            }
            if (atomicReference.compareAndSet(State.INIT, State.RUN)) {
                result.set(runOperator.apply(result.get(), value));
                return;
            }
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        while (true) {
            if (atomicReference.get() == State.CLOSE) {
                System.out.println("Ошибочка");
                return;
            }
             if (atomicReference.compareAndSet(State.RUN, State.FINISH)) {
                 finishConsumer.accept(result.get());
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
            if (atomicReference.get() == State.CLOSE) {
                System.out.println("Ошибочка");
                return;
            }
            if (atomicReference.compareAndSet(State.FINISH, State.CLOSE)) {
                closeConsumer.accept(result.get());
                return;
            }
        }
    }


    public T getResult() {
        return result.get();
    }

    public State getState() {
        return atomicReference.get();
    }
}
