package ru.mail.polis.homework.concurrency.state;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * ѕотокобезопасный контейнер дл€ вычислений.  онтейнер создаетс€ с некторым дэфолтным значеним.
 * ƒалее значение инициализируетс€, вычисл€етс€ и отдаетс€ к потребителю. ¬ каждом методе контейнер мен€ет состо€ние
 * и делает некоторое вычисление (которое передано ему в определенный метод)
 *
 * ѕоследовательность переходов из состо€ни€ в состо€ние строго определена:
 * START -> INIT -> RUN -> FINISH
 * »з состо€ни€ FINISH можно перейти или в состо€ние INIT или в состо€ние CLOSE.
 * CLOSE - конченое состо€ние.
 *
 * ≈сли какой-либо метод вызываетс€ после перехода в состо€ние CLOSE
 * он должен написать ошибку (Ќ≈ бросить) и сразу выйти.
 * ≈сли вызван метод, который не соответствует текущему состо€нию - он ждет,
 * пока состо€ние не станет подход€щим дл€ него (или ждет состо€ние CLOSE, чтобы написать ошибку и выйти)
 *
 *
 * ≈сть три варианта решени€ этой задачи.
 * 1) через методы wait and notify - 5 баллов
 * 2) через Lock and Condition - 5 баллов
 * 3) через операцию compareAndSet на Atomic классах - 9 баллов
 * Ѕаллы за методы не суммируютс€, беретс€ наибольший балл из всех методов. (то есть, если вы сделали 1 метод на 4 балла,
 * и 3 метод на 3 балла, € поставлю баллы только 4 балла)
 *
 * Max 8 баллов
 */
public class CalculateContainer<T> {
    private final AtomicReference<State> state = new AtomicReference<>(State.START);

    private T result;

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * »нициализирует результат и переводит контейнер в состо€ние INIT (¬озможно только из состо€ни€ START и FINISH)
     */
    public void init(UnaryOperator<T> initOperator)  {
        do {
            if (state.get() == State.CLOSE) {
                System.out.println("Error in init! State was closed!");
                return;
            }

            if ((state.get() == State.START) || (state.get() == State.FINISH)) {
                T oldResult = result;
                result = initOperator.apply(result);
                if (!(state.compareAndSet(State.START, State.INIT)) && !(state.compareAndSet(State.FINISH, State.INIT))) {
                    result = oldResult;
                } else {
                    return;
                }
            }

        } while(true);

    }

    /**
     * ¬ычисл€ет результат и переводит контейнер в состо€ние RUN (¬озможно только из состо€ни€ INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value)  {
        do {
            if (state.get() == State.CLOSE) {
                System.out.println("Error in run! State was closed!");
                return;
            }

            if (state.get() == State.INIT) {
                T oldResult = result;
                result = runOperator.apply(result, value);
                if (!state.compareAndSet(State.INIT, State.RUN)) {
                    result = oldResult;
                } else {
                    return;
                }
            }

        } while(true);
    }


    /**
     * ѕередает результат потребителю и переводит контейнер в состо€ние FINISH (¬озможно только из состо€ни€ RUN)
     */
    public void finish(Consumer<T> finishConsumer) {
        do {
            if (state.get() == State.CLOSE) {
                System.out.println("Error in finish! State was closed!");
                return;
            }

            if (state.get() == State.RUN) {
                T oldResult = result;
                if (state.compareAndSet(State.RUN, State.FINISH)) {
                    finishConsumer.accept(oldResult);
                    return;
                }
            }

        } while(true);

    }


    /**
     * «акрывает контейнер и передает результат потребителю. ѕереводит контейнер в состо€ние CLOSE
     * (¬озможно только из состо€ни€ FINISH)
     */
    public void close(Consumer<T> closeConsumer) {
        do {
            if (state.get() == State.CLOSE) {
                System.out.println("Error in close! State was closed!");
                return;
            }

            if (state.get() == State.FINISH) {
                T oldResult = result;
                if (state.compareAndSet(State.FINISH, State.CLOSE)) {
                    closeConsumer.accept(oldResult);
                    return;
                }
            }

        } while(true);

    }

    public T getResult() {
        return result;
    }

    public State getState() {
        return state.get();
    }
}