package ru.mail.polis.homework.objects;

import java.util.Iterator;

/**
 * 15 ��������
 * ����������� ��� ������ ������������ ������.
 */
public class CustomLinkedList implements Iterable<Integer> {

    private Node head;

    /**
     * 1 ������
     * ���������� ���������� ��������� � ������
     *
     * @return size
     */
    public int size() {
        return 0;
    }

    /**
     * 2 �������
     * ����������� �����:
     * ��������� ������� � ����������� ������.
     *
     * @param value - data for create Node.
     */
    public void add(int value) {

    }

    /**
     * 2 �������
     * ����� ������ ������� ����� �� ��������������� �������.
     *
     * @param index
     */
    public int get(int index) {
       return 0;
    }

    /**
     * 2 �������
     * ����������� �����:
     * ��������� ������� � ����������� ������ �� �������� �������.
     * ���� ��� ������� ���������� index - ���� �������� ���������� IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param i - index
     * @param value - data for create Node.
     */
    public void add(int i, int value) {

    }

    /**
     * 2 �������
     * ����������� �����:
     * ������� ������� � ��������� �������, ��� ��� �������� ��� �������� �������� ���� � ������.
     * ���� ��� ������� ���������� index - ���� �������� ���������� IndexOutOfBoundsException.
     * throw new IndexOutOfBoundsException(i);
     *
     * @param index - position what element need remove.
     */
    public void removeElement(int index) {

    }

    /**
     * 2 �������
     * ����������� �����:
     * �������������� ��� �������� ������.
     * ������:
     *  �������� ������������������ ������ "1 -> 2 -> 3 -> 4 -> null"
     *  ����� ���������� ������ ������������������ ������ ���� ����� "4 -> 3 -> 2 -> 1 -> null"
     */
    public void revertList() {

    }

    /**
     * 1 ������
     * ����� ������� ��� ������������������ ���������� � ������ ������� � head.
     * ������ ������:
     *  - �������� ������ Node ������ ����������� " -> "
     *  - ������������������ ������ ������������� �� null
     *  - ���� � ������ ��� ��������� - ������� ������ "null"
     *
     * @return - String with description all list
     */
    @Override
    public String toString() {
        return "1 -> 2 -> 3 -> null";
    }

    /**
     * 3 �������
     * ���������� ��������, ������� ����� ������ �������������. ��� ��������!
     *
     * @return iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    private static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
