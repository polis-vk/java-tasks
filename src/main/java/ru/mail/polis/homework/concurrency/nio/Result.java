package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Оболочка результата. Содержит уникальный айдишник, состояние запроса и результат (когда он появится)
 */
public class Result implements Serializable {

    private final int id;
    private volatile double result;
    private volatile boolean isWaiting = false;
    private volatile Client client;
    private volatile ClientState state = ClientState.START;
    private final ExecutorService calculatingExecutor = Executors.newFixedThreadPool(10);


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
        calculatingExecutor.execute(() -> {
            try {
                listener.accept(get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Блокирующий метод. Возвращает результат
     */
    public synchronized double get() throws InterruptedException {
        if (state.equals(ClientState.CANCEL)) {
            return Double.NaN;
        }
        if (!state.equals(ClientState.DONE)) {
            isWaiting = true;
            wait();
            isWaiting = false;
        }
        return result;
    }

    public void set(double result) {
        this.result = result;
    }

    /**
     * Неблокирующий метод. Возвращает результат или null, если его еще нет
     */
    public Double getNonBlocking() {
        return state.equals(ClientState.DONE) ? result : null;
    }

    /**
     * Показывает текущее состояние запроса
     */
    public ClientState getState() {
        return state;
    }

    /**
     * Закрывает запрос. На сервер должно отправиться сообщение, что запрос больше не актуален.
     * В момент вызова этого метода, состояние должно перейти в CANCELLING (Если возможно).
     * При этом, сервер может не успеть обработать закрытие и все равно прислать ответ.
     */
    public void cancel() {
        try {
            client.cancelResult(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
