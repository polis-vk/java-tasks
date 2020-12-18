package ru.mail.polis.homework.concurrency.state;

import org.hamcrest.Condition;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Потокобезопасный контейнер для вычислений. Контейнер создается с некоторым дэфолтным значением.
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

    private AtomicReference<State> state = new AtomicReference<>(State.START);
    private AtomicReference<T> result = new AtomicReference<>();

    public CalculateContainer(T result) {
        this.result.set(result);
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        while(!state.compareAndSet(State.START, State.INIT) && !state.compareAndSet(State.FINISH, State.INIT)) {
            if(state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.err.println("Close state in init!");
                return;
            }
        }
        result.set(initOperator.apply(result.get()));
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        while(!state.compareAndSet(State.INIT, State.RUN)) {
            if(state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.err.println("Close state in run!");
                return;
            }
        }
        result.set(runOperator.apply(result.get(), value));
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        while(!state.compareAndSet(State.RUN, State.FINISH)) {
            if(state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.err.println("Close state in finish!");
                return;
            }
        }
        finishConsumer.accept(result.get());
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        while(!state.compareAndSet(State.FINISH, State.CLOSE)) {
            if(state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.err.println("Close state in close!");
                return;
            }
        }
        closeConsumer.accept(result.get());
    }


    public T getResult() {
        return result.get();
    }

    public State getState() {
        return state.get();
    }

}
