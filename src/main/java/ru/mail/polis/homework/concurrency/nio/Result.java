package ru.mail.polis.homework.concurrency.nio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * Оболочка результата. Содержит уникальный айдишник, состояние запроса и результат (когда он появится)
 */
public class Result {

    private final int id;
    private ClientState state;
    private Double resultValue = null;
    private final List<Consumer<Double>> listeners = new ArrayList<>();
    private final Thread listenersExecutor = new Thread(() -> {
        synchronized (listeners) {
            for (Consumer<Double> listener : listeners) {
                listener.accept(resultValue);
            }
        }
    });

    public Result(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Позволяет добавить слушателя, который вызовется при получении результата
     */
    public void addListener(Consumer<Double> listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    /**
     * Блокирующий метод. Возвращает результат
     */
    public double get() throws InterruptedException {
        synchronized (state) {
            while (state != ClientState.DONE && state != ClientState.CANCEL && state != ClientState.CLOSE) {
                state.wait();
            }
        }
        return resultValue;
    }


    /**
     * Неблокирующий метод. Возвращает результат или null, если его еще нет
     */
    public Double getNonBlocking() {
        return resultValue;
    }

    /**
     * Показывает текущее состояние запроса
     */
    public ClientState getState() {
        synchronized (state) {
            return state;
        }
    }

    /**
     * Закрывает запрос. На сервер должно отправиться сообщение, что запрос больше не актуален.
     * В момент вызова этого метода, состояние должно перейти в CANCELLING (Если возможно).
     * При этом, сервер может не успеть обработать закрытие и все равно прислать ответ.
     */
    public void cancel() {
        setState(ClientState.CANCEL);
    }

    public void setState(ClientState state) {
        synchronized (this.state) {
            this.state = state;
        }
    }

    public void setValue(Double resultValue) {
        synchronized (this.resultValue) {
            this.resultValue = resultValue;
        }
    }


    public void notifyListeners() {
        listenersExecutor.start();
    }
}
