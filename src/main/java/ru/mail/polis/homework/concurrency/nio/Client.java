package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.util.List;

/**
 * Клиент, который принимает список операций и как можно быстрее отправляет это на сервер. (ClientSocket)
 * Нужно отправлять в несколько потоков. Важный момент, одно задание на 1 порт.
 *
 * Так же, он передает информацию серверу, на каком порте он слушает результат (ServerSocket).
 * При этом слушатель серверСокета только 1
 *
 * Каждому запросу присваивается уникальная интовая айдишка. Клиент может быть закрыт.
 *
 */
public class Client {

    /**
     * @param clientsPort массив портов для отправки
     * @param serverPort порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     * Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend) throws IOException {

    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) {
        return null;
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        return null;
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() {

    }

}
