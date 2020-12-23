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
 * Есть три варианта решения этой задачи.<br>
 * 1) через методы wait and notify - 5 баллов<br>
 * 2) через Lock and Condition - 5 баллов<br>
 * 3) через операцию compareAndSet на Atomic классах - 9 баллов<br>
 * Баллы за методы не суммируются, берется наибольший балл из всех методов. (то есть, если вы сделали 1 метод на 4 балла,
 * и 3 метод на 3 балла, я поставлю баллы только 4 балла)
 * <p>
 * Max 8 баллов
 */
public class CalculateContainer<T> {
    private final AtomicReference<State> state = new AtomicReference<>(State.START);

    private volatile T result;

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * Инициализирует результат и переводит контейнер в состояние INIT (Возможно только из состояния START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {
        synchronized (state) {
            System.out.println("IN INIT "  + this.toString());
            try {
                while (true) {
                    if (state.get() == State.CLOSE) {
                        System.out.println("Container has closed");
                        break;
                    }
                    if (state.get() == State.START || state.get() == State.FINISH) {
                        result = initOperator.apply(result);
                        state.set(State.INIT);
                        break;
                    }
                    state.wait();
                }
                state.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Вычисляет результат и переводит контейнер в состояние RUN (Возможно только из состояния INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {
        synchronized (state) {
            System.out.println("IN RUN "  + this.toString());
            try {
                while (true) {
                    if (state.get() == State.CLOSE) {
                        System.out.println("Container has closed");
                        break;
                    }
                    if (state.get() == State.INIT) {
                        result = runOperator.apply(result, value);
                        state.set(State.RUN);
                        break;
                    }
                    state.wait();
                }
                state.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Передает результат потребителю и переводит контейнер в состояние FINISH (Возможно только из состояния RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        synchronized (state) {
            try {
                while (true) {
                    System.out.println("IN FINISH: " + this.toString());
                    if (state.get() == State.CLOSE) {
                        System.out.println("Container has closed");
                        break;
                    }
                    if (state.get() == State.RUN) {
                        System.out.println("finish set");
                        finishConsumer.accept(result);
                        state.set(State.FINISH);
                        break;
                    }
                    state.wait();
                }
                state.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Закрывает контейнер и передает результат потребителю. Переводит контейнер в состояние CLOSE
     * (Возможно только из состояния FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        synchronized (state) {
            try {
                while (true) {
                    if (state.get() == State.CLOSE) {
                        System.out.println("Container has closed");
                        break;
                    }
                    if (state.get() == State.FINISH) {
                        closeConsumer.accept(result);
                        state.set(State.CLOSE);
                        break;
                    }
                    state.wait();
                }
                state.notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public T getResult() {
        return result;
    }

    public State getState() {
        return state.get();
    }

    @Override
    public String toString() {
        return "CalculateContainer{" +
                "state=" + state.get() +
                ", result=" + result +
                '}';
    }
}
