package ru.mail.polis.homework.concurrency.nio;

/**
 * Возможные состояния для клиентского Result
 * Возможные переходы:
 * 1) START -> SENDING -> SENT -> RECEIVING -> DONE;
 * 2) Из любого состояния в CLOSE
 * 3) START -> CANCEL
 * 4) SENDING -> CANCELLING -> CANCEL
 * 5a) (SENT, RECEIVING) -> CANCELLING -> CANCEL
 * 5b) (SENT, RECEIVING) -> CANCELLING -> DONE
 *
 */
public enum ClientState {
    START,
    SENDING,
    SENT,
    RECEIVING,
    DONE,
    CANCELLING,
    CANCEL,
    CLOSE
}
