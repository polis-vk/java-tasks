package ru.mail.polis.homework.oop.vet;

import java.util.List;

/**
 * Класс описывающий ветеренара.
 * принимает поля:
 * costForPet - ценник на лечение домашних животных
 * costForWild - ценник на лечение диких животных
 * organizationWithGrant - список организаций с которыми сотрудничает и может
 * при помощи грантов организции делать скидку на лечение
 */
public class Veterinary {

    private final int costForPet;
    private final int costForWild;

    private final List<String> organizationWithGrant;

    public Veterinary(int costForPet, int costForWild, List<String> organizationWithGrant) {
        this.costForPet = costForPet;
        this.costForWild = costForWild;
        this.organizationWithGrant = organizationWithGrant;
    }

    // подсчет стоймости лечения всех переданных животных
    public int calculateCostTherapy(Animal[] animals) {
        int cost = 0;
        for (Animal animal : animals) {
            if (animal instanceof Pet && animal instanceof WildAnimal) {
                cost += Math.max(costForPet, costForWild);
                cost = saleByOrganizationGrant((WildAnimal) animal, cost);
            } else if (animal instanceof Pet) {
                cost += costForPet;
            } else if (animal instanceof WildAnimal) {
                cost += costForWild;
                cost = saleByOrganizationGrant((WildAnimal) animal, cost);
            }
        }
        return cost;
    }

    private int saleByOrganizationGrant(WildAnimal animal, int cost) {
        if (organizationWithGrant.contains(animal.getOrganizationName())) {
            return --cost;
        }
        return cost;
    }
}
