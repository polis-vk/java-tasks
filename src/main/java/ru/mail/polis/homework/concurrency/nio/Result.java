package ru.mail.polis.homework.concurrency.nio;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Оболочка результата. Содержит уникальный айдишник, состояние запроса и результат (когда он появится)
 */
public class Result {

    private final List<Consumer<Double>> resultListeners = new ArrayList<>();
    private Double result;
    private final int id;

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
        resultListeners.add(listener);
    }

    /**
     * Блокирующий метод. Возвращает результат
     */
    public double get() {
        for (; ; ) {
            if (result != null) {
                callListeners();
                return result;
            }
        }
    }

    private void callListeners() {
        resultListeners.forEach(doubleConsumer -> doubleConsumer.accept(result));
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
    public ClientState getState() {
        return null;
    }

    /**
     * Закрывает запрос. На сервер должно отправиться сообщение, что запрос больше не актуален.
     * В момент вызова этого метода, состояние должно перейти в CANCELLING (Если возможно).
     * При этом, сервер может не успеть обработать закрытие и все равно прислать ответ.
     */
    public void cancel() {

    }

    public void setResult(double result) {
        this.result = result;
    }
}
