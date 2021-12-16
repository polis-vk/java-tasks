package ru.mail.polis.homework.concurrency.nio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Оболочка результата. Содержит уникальный айдишник, состояние запроса и результат (когда он появится)
 */
public class Result {

    private final int id;
    private ClientState state = ClientState.START;
    private volatile Double result;
    private final List<Consumer<Double>> listeners = new ArrayList<>();

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
        listeners.add(listener);
    }

    /**
     * Блокирующий метод. Возвращает результат
     */
    public synchronized double get() throws InterruptedException {
        while (!state.equals(ClientState.DONE) && !state.equals(ClientState.CANCELLING) && !state.equals(ClientState.CANCEL)) {
            wait();
        }

        return result;
    }


    /**
     * Неблокирующий метод. Возвращает результат или null, если его еще нет
     */
    public Double getNonBlocking() {
        return result;
    }

    /**
     * Показывает текущее состояние запроса
     */
    public synchronized ClientState getState() {
        return state;
    }

    /**
     * Закрывает запрос. На сервер должно отправиться сообщение, что запрос больше не актуален.
     * В момент вызова этого метода, состояние должно перейти в CANCELLING (Если возможно).
     * При этом, сервер может не успеть обработать закрытие и все равно прислать ответ.
     */
    public void cancel() {
        state = ClientState.CANCEL;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setState(ClientState state) {
        if (state.equals(ClientState.DONE)) {
            for (Consumer<Double> listener : listeners) {
                listener.accept(result);
            }
            listeners.clear();
        }
        this.state = state;
    }
}
