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
 * из состояния FINISH можно перейти или в состояние INIT или в состояние CLOSE.
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
public class    CalculateContainer<T> {

    private final AtomicReference<State> state;
    private final AtomicReference<T> result;

    public CalculateContainer(T result) {
        this.result = new AtomicReference<>(result);
        this.state = new AtomicReference<>(State.START);
    }

    /**
     * инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        while (!state.compareAndSet(State.START, State.START) && !state.compareAndSet(State.FINISH, State.FINISH)) {
            if (state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.out.println(Thread.currentThread().getName() + " init failed: container is closed");
                return;
            }
        }

        if (state.compareAndSet(State.START, State.START)) {
            result.compareAndSet(result.get(), initOperator.apply(result.get()));
            state.compareAndSet(State.START, State.INIT);
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        while (!state.compareAndSet(State.INIT, State.INIT)) {
            if (state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.out.println(Thread.currentThread().getName() + " run failed: container is closed");
                return;
            }
        }

        if (state.compareAndSet(State.INIT, State.INIT)) {
            result.compareAndSet(result.get(), runOperator.apply(result.get(), value));
            state.compareAndSet(State.INIT, State.RUN);
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        while (!state.compareAndSet(State.RUN, State.RUN)) {
            if (state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.out.println(Thread.currentThread().getName() + " finish failed: container is closed");
                return;
            }
        }

        if (state.compareAndSet(State.RUN, State.RUN)) {
            finishConsumer.accept(result.get());
            state.compareAndSet(State.RUN, State.FINISH);
        }
    }


    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        while (!state.compareAndSet(State.FINISH, State.FINISH)) {
            if (state.compareAndSet(State.CLOSE, State.CLOSE)) {
                System.out.println(Thread.currentThread().getName() + " close failed: container is closed");
                return;
            }
        }

        if (state.compareAndSet(State.FINISH, State.FINISH)) {
            closeConsumer.accept(result.get());
            state.compareAndSet(State.FINISH, State.CLOSE);
        }
    }


    public T getResult() {
        return result.get();
    }

    public State getState() {
        return state.get();
    }
}
