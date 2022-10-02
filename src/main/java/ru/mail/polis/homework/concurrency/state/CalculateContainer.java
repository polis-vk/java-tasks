package ru.mail.polis.homework.concurrency.state;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * ���������������� ��������� ��� ����������. ��������� ��������� � �������� ��������� ��������.
 * ����� �������� ����������������, ����������� � �������� � �����������. � ������ ������ ��������� ������ ���������
 * � ������ ��������� ���������� (������� �������� ��� � ������������ �����)
 *
 * ������������������ ��������� �� ��������� � ��������� ������ ����������:
 * START -> INIT -> RUN -> FINISH
 * �� ��������� FINISH ����� ������� ��� � ��������� INIT ��� � ��������� CLOSE.
 * CLOSE - �������� ���������.
 *
 * ���� �����-���� ����� ���������� ����� �������� � ��������� CLOSE
 * �� ������ �������� ������ (�� �������) � ����� �����.
 * ���� ������ �����, ������� �� ������������� �������� ��������� - �� ����,
 * ���� ��������� �� ������ ���������� ��� ���� (��� ���� ��������� CLOSE, ����� �������� ������ � �����)
 *
 *
 * ���� ��� �������� ������� ���� ������.
 * 1) ����� ������ wait and notify - 5 ������
 * 2) ����� Lock and Condition - 5 ������
 * 3) ����� �������� compareAndSet �� Atomic ������� - 9 ������
 * ����� �� ������ �� �����������, ������� ���������� ���� �� ���� �������. (�� ����, ���� �� ������� 1 ����� �� 4 �����,
 * � 3 ����� �� 3 �����, � �������� ����� ������ 4 �����)
 *
 * Max 8 ������
 */
public class CalculateContainer<T> {

    private State state = State.START;

    private T result;

    public CalculateContainer(T result) {
        this.result = result;
    }

    /**
     * �������������� ��������� � ��������� ��������� � ��������� INIT (�������� ������ �� ��������� START � FINISH)
     */
    public void init(UnaryOperator<T> initOperator) {

    }

    /**
     * ��������� ��������� � ��������� ��������� � ��������� RUN (�������� ������ �� ��������� INIT)
     */
    public void run(BinaryOperator<T> runOperator, T value) {

    }


    /**
     * �������� ��������� ����������� � ��������� ��������� � ��������� FINISH (�������� ������ �� ��������� RUN)
     */
    public void finish(Consumer<T> finishConsumer) {

    }


    /**
     * ��������� ��������� � �������� ��������� �����������. ��������� ��������� � ��������� CLOSE
     * (�������� ������ �� ��������� FINISH)
     */
    public void close(Consumer<T> closeConsumer) {

    }


    public T getResult() {
        return result;
    }

    public State getState() {
        return state;
    }
}
