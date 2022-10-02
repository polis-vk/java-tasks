package ru.mail.polis.homework.io.objects;


import java.util.Collections;
import java.util.List;

/**
 * ����� ����������� ������ ����� ������ � ����������� ������������ 4-�� (2-��) �������� ������.
 * ��� ������������ ���� ������� ������ �� 10 ������ �������� (��������� ������� ����� ��������,
 * � ������� ������ Random ��� ������ ��������� ������ �������� ����������).
 * ����� ������������ ������ �������� � ���� � ��� �� ���� 10 ��� (100 ��� � �����, ���� � ��� ��� ���������� ����� ������).
 * ������ ����� ����������, ����� � ��� �� ���� ������ �� ���� � �� �� �������.
 *
 * ����� ���� ������ ���� ��������� �� �����.
 * ���������� � ������������ ���� ����� � ������� ������������ ������������ ��� �������� �������
 *
 * ����������� ����� ������ ���� ���������: ������ �����, ����� ������ � ����� ������.
 * ����� ������� ����� System.currentTimeMillis().
 * � �������� ����������� ������ ���� ���������� �� ���� ��������� ��� ������� �����. (����� 4 �����/2 �����,
 * �� ������ ���� 1 ���� � 1 ��� �� ���������� ���������� �����������)
 * ��� ������ �������� ������ � ��������������� ������ � ����� ������. ����������� ������ �����, ��� �������.
 *
 * � ����� ����� �� ������ ������, �� ��������� ������� �����
 */
public class Serializer {

    /**
     * 1 ����
     * ����������� ������� ������������, � ������� ������������ ������ ��� ������������ ��������
     * @param animals ������ �������� ��� ������������
     * @param fileName ���� � ������� "�����" ��������
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {

    }

    /**
     * 1 ����
     * ����������� ������� ��������������, � ������� ������������ ������ ��� �������������� ��������
     *
     * @param fileName ���� �� �������� "������" ��������
     * @return ������ ��������
     */
    public List<Animal> defaultDeserialize(String fileName) {
        return Collections.emptyList();
    }


    /**
     * 1 ����
     * ����������� ������� ������ ������������, � ������� ������������ ������ ��� ������������ �������� � ����������� �������
     * @param animals ������ �������� ��� ������������
     * @param fileName ���� � ������� "�����" ��������
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {

    }

    /**
     * 1 ����
     * ����������� ������� ������ ��������������, � ������� ������������ ������ ��� �������������� ��������
     * � ����������� �������
     *
     * @param fileName ���� �� �������� "������" ��������
     * @return ������ ��������
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        return Collections.emptyList();
    }

    /**
     * 1 ����
     * ����������� ������� ������ ������������, � ������� ������������ ������ ��� ������������ �������� � ���������� Externalizable
     * @param animals ������ �������� ��� ������������
     * @param fileName ���� � ������� "�����" ��������
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {

    }

    /**
     * 1 ����
     * ����������� ������� ������ ��������������, � ������� ������������ ������ ��� �������������� ��������
     * � ���������� Externalizable
     *
     * @param fileName ���� �� �������� "������" ��������
     * @return ������ ��������
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        return Collections.emptyList();
    }

    /**
     * 2 �����
     * ����������� ������ ������������, � ������� ��������������� �������. ���� ������� ����� ����,
     * ��� ������������� ������� writeObject
     *
     * @param animals  ������ �������� ��� ������������
     * @param fileName ����, � ������� "�����" ��������
     */
    public void customSerialize(List<Animal> animals, String fileName) {

    }

    /**
     * 2 �����
     * ����������� ������ ��������������, � ������� ��������������� �������. ���� ������� ������ ����,
     * ��� ������������� ������� readObject
     *
     * @param fileName ���� �� �������� "������" ��������
     * @return ������ ��������
     */
    public List<Animal> customDeserialize(String fileName) {
        return Collections.emptyList();
    }
}
