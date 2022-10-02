package ru.mail.polis.homework.concurrency.nio;

/**
 * Возможные состояния для серверной операции:
 * 1) LOADING -> WAITING_CALCULATE -> CALCULATING -> WAITING_TO_SEND -> SENDING -> DONE
 * 2) Из любого состояния (кроме DONE) в CANCEL
 * 3) Из любого состояния (кроме DONE) в CLOSE
 *
 * Аккуратнее с состоянием SENDING, там тонкая грань, когда он в DONE переходит, а когда в CANCEL или CLOSE
 */
public enum ServerState {
    LOADING,
    WAITING_CALCULATE,
    CALCULATING,
    WAITING_TO_SEND,
    SENDING,
    DONE,
    CANCEL,
    CLOSE
}
