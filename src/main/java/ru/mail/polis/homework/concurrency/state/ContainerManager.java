package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;


/**
 * �����, ������� ��������� ������������ � ����� �� ����������������, ���������, ������������ � ���������.
 * ������ ��� ��� �����������, �� ���� ����� �������������� �� ������ �������.
 *
 * ��� ������� �� 2 ����� �� ������ �����. ����������� �� 1 ����
 * Max 11 ������
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;

    /**
     * �������� ������ �� �������� �����������
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = null;
    }


    /**
     * ����������� executor c ����������� ����������� �������,
     * ������� ����� ������������ ��� ����������, �����-������ �������������� ��������� 1_000_000 ���.
     * (��� ����� ����������� ��������������� ����� operation)
     *
     * ������ ��������� ���� ��������� ��������.
     */
    public void initContainers() {

    }


    /**
     * ����������� executor c 2 �������� (����� � ��������� finish),
     * ������� ����� ��������� ��� ���������� �����-������ �������������� ��������� 1_000_000 ���
     * (��� ����� ����������� ��������������� ����� operation)
     *
     * ������ ��������� ���� ��������� ��������.
     */
    public void runContainers() {

    }


    /**
     * ����������� executor c 2 �������� (����� � ��������� run), ������� ����� ���������
     * ������� �� ����������� � �������� �� � ��������������� ������� �� ����������� ���������
     *
     * ������ ��������� ���� ��������� ��������.
     */
    public void finishContainers() {

    }


    /**
     * ����������� executor c 1 �������, ������� ����� ��������� ������� �� �����������
     * � �������� �� � ��������������� ������� � ��������.
     *
     * ������ ��������� ���� ��������� ��������.
     *
     * ��� ��� ���� ����� ��������� ��������� � �������� ���������,
     * �� ����� �������� ��������� �������������, ������� ��������������,
     * ��� ������ ��������� ��� 10 ����������
     */
    public void closeContainers() {

    }

    /**
     * ���� ����� ������ �����, ���� ��� ���������� �� ��������� ��� ���� �� ���������� �����.
     * ���� ����� �����, �� ����� ������ ������� false, ����� true.
     *
     * ����� ��� ������ ��������, ������� ����������� � Java ����� ����� � ���������.
     * ������, ��� ����� ���������� � ������������.
     */
    public boolean await(long timeoutMillis) throws Exception {
        return false;
    }

    public List<CalculateContainer<Double>> getCalculateContainers() {
        return calculateContainers;
    }

    private  <T> UnaryOperator<T> operation(UnaryOperator<T> operator) {
        return param -> {
            T result = param;

            for (int i = 0; i < 1000; i++) {
                result = operator.apply(result);
            }
            return result;
        };
    }

    private <T> BinaryOperator<T> operation(BinaryOperator<T> operator) {
        return (start, delta) -> {
            T result = start;
            for (int i = 0; i < 1000; i++) {
                result = operator.apply(result, delta);
            }
            return result;
        };
    }

}
