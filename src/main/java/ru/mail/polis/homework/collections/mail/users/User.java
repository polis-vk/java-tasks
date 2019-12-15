package ru.mail.polis.homework.collections.mail.users;

import ru.mail.polis.homework.collections.mail.transmissions.TrasmissionObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements ReceiveFromUser, SendToUser{

    private List<TrasmissionObject<?>> listOfReceivedObjects;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String gender;
    private final int age;

    public User(String firstName, String lastName, String middleName, String gender, int age) {
        this.listOfReceivedObjects = new ArrayList<>();
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.age = age;
    }

    @Override
    public void receive(TrasmissionObject<?> object) {
        listOfReceivedObjects.add(object);
    }

    @Override
    public TrasmissionObject<?> send(TrasmissionObject<?> object) {
        return object;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public List<TrasmissionObject<?>> getListOfReceivedObjects() {
        return listOfReceivedObjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName)
                && lastName.equals(user.lastName)
                && middleName.equals(user.middleName)
                && gender.equals(user.gender)
                && age == user.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getMiddleName(), getGender(), getAge());
    }

}
