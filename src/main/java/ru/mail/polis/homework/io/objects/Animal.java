package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей✓ с примитивами✓, строками✓, энамами✓ и некоторыми сапомисными объектами✓.
 * Важно, чтобы хотя бы одно поле каждого типа присутствовало в классе в качестве поля✓
 * 1 балл
 */
public class Animal implements Serializable {

    private final String name;                          //строка✓
    private int age;                                    //примитив✓
    private final boolean isFlying;
    private final boolean isSwimming;
    private Animal mom;//меняются!                     //сапомисный объект✓
    private Animal dad;
    private List<Animal> babies = new ArrayList<>();    //sic!
    private final Food food;                            //энамам✓

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFlying() {
        return isFlying;
    }

    public boolean isSwimming() {
        return isSwimming;
    }

    public Animal getMom() {
        return mom;
    }

    public Animal getDad() {
        return dad;
    }

    public List<Animal> getBabies() {
        return babies;
    }

    public Food getFood() {
        return food;
    }

    public void aging() {
        age++;
    }

    public boolean isMyBaby(Animal baby) {
        return babies.contains(baby);
    }

    public boolean isMyParent(Animal parent) {
        return mom == parent || dad == parent;
    }

    public void addBaby(Animal baby) {
        babies.add(baby);
    }

    public Animal(String name, int age, boolean isFlying, boolean isSwimming, Food food, Animal mom, Animal dad) {
        this.name = name;
        this.age = age;
        this.isFlying = isFlying;
        this.isSwimming = isSwimming;
        this.food = food;
        if (mom != null) {
            this.mom = mom;
            mom.addBaby(this);
        } else {
            this.mom = NullAnimal.getInstance(); //нет инфы по родителям
        }
        if (dad != null) {
            this.dad = dad;
            dad.addBaby(this);
        } else {
            this.dad = NullAnimal.getInstance(); //нет инфы по родителям
        }

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Animal animal = (Animal) o;
        if (equalsAnimal(this, animal)                     //сверяем основные поля
                && this.dad.equals(animal.dad)                  //сверяем пап
                && this.mom.equals(animal.mom)                  //сверяем мам
                && equalsBabies(animal.babies)                  //сверяем детей
        ) {
            return true;
        }
        return false;
    }

    private boolean equalsAnimal(Animal one, Animal two) {   //проверка основных полей
        if (one.food == two.food                             //сверяем еду
                && one.age == two.age                       //сверяем возраст
                && one.name.equals(two.name)                //сверяем имя
                && one.isFlying == two.isFlying             //сверяем полет
                && one.isSwimming == two.isSwimming         //сверяем плавание
        ) {
            return true; //совпадают
        }
        return false;    //разные
    }

    private boolean equalsBabies(List<Animal> thatBabies) {                         //сверяем детей
        if (this.babies.size() == thatBabies.size()) {                              //количество детей

            for (int i = 0; i < thatBabies.size(); i++) {                           //сверяем каждого
                Animal thisBaby = this.babies.get(i);
                Animal thatBaby = thatBabies.get(i);

                if (!equalsAnimal(thisBaby, thatBaby)                               //поля?
                        && !thatBaby.isMyParent(this)                               //признает?
                ) {
                    return false;   //как минимум один чужой
                }
            }
            return true; //все свои, родные
        }
        return false; //количество не совпало
    }

    public void changeParentLink(Animal animal){  //востановление перекрестной ссылки
        if(equalsAnimal(dad,animal)){
            for (Animal a : dad.getBabies()) {
                animal.addBaby(a);
            }
            dad = animal;
        }
        if(equalsAnimal(mom,animal)){
            for (Animal a : mom.getBabies()) {
                animal.addBaby(a);
            }
            mom = animal;
        }
    }
}

