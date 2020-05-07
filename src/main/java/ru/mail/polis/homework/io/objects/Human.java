package ru.mail.polis.homework.io.objects;

class Human {   //Я не забыл написать модификатор доступа, он просто package visible/default
    private final String name;
    private final int age;
    public Human(String name, int age){
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}