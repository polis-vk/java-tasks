package ru.mail.polis.homework.concurrency.nio;

/**
 * ��������� ��������� ��� ����������� Result
 * ��������� ��������:
 * 1) START -> SENDING -> SENT -> RECEIVING -> DONE;
 * 2) �� ������ ��������� � CLOSE
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
