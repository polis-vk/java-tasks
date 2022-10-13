package ru.mail.polis.homework.oop.vet.animals;

/**
 * Интерфейс обозначающий что данное живаотное является диким.
 * Содержит методы которые говорят какая организация владеет этим животным.
 */
public interface WildAnimal {
    String getOrganizationName();

    void setOrganizationName(String organizationName);
}
