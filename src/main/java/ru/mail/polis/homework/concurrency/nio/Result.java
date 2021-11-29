package ru.mail.polis.homework.concurrency.nio;

import java.util.function.Consumer;

/**
 * Оболочка результата. Содержит уникальный айдишник, состояние запроса и результат (когда он появится)
 */
public class Result {

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

    }

    /**
     * Блокирующий метод. Возвращает результат
     */
    public double get() {
        return 0;
    }


    /**
     * Неблокирующий метод. Возвращает результат или null, если его еще нет
     */
    public Double getNonBlocking() {
        return null;
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
}
