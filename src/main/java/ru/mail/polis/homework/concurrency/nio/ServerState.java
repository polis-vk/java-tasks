package ru.mail.polis.homework.concurrency.nio;

/**
 * ��������� ��������� ��� ��������� ��������:
 * 1) LOADING -> WAITING_CALCULATE -> CALCULATING -> WAITING_TO_SEND -> SENDING -> DONE
 * 2) �� ������ ��������� (����� DONE) � CANCEL
 * 3) �� ������ ��������� (����� DONE) � CLOSE
 *
 * ���������� � ���������� SENDING, ��� ������ �����, ����� �� � DONE ���������, � ����� � CANCEL ��� CLOSE
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
