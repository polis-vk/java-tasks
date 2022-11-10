package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private int legs;
    private boolean isWild;
    private boolean isDomesticated;
    private String nickname;
    private MoveType moveType;
    private AnimalDescription characteristic;

    public AnimalExternalizable(int legs, boolean isWild, boolean isDomesticated, String nickname, MoveType moveType, AnimalDescription characteristic) {
        this.legs = legs;
        this.isWild = isWild;
        this.isDomesticated = isDomesticated;
        this.nickname = nickname;
        this.moveType = moveType;
        this.characteristic = characteristic;
    }

    public AnimalExternalizable() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return legs == that.legs && isWild == that.isWild && isDomesticated == that.isDomesticated && Objects.equals(nickname, that.nickname) && moveType == that.moveType && Objects.equals(characteristic, that.characteristic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, isWild, isDomesticated, nickname, moveType, characteristic);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(legs);
        out.writeBoolean(isWild);
        out.writeBoolean(isDomesticated);
        out.writeUTF(nickname);
        out.writeUTF(moveType.toString());
        out.writeObject(characteristic);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        legs = in.readInt();
        isWild = in.readBoolean();
        isDomesticated = in.readBoolean();
        nickname = in.readUTF();
        moveType = MoveType.valueOf(in.readUTF());
        characteristic = (AnimalDescription) in.readObject();
    }

    static class AnimalDescription implements Externalizable {
        private int age;
        private boolean isFriendly;
        private String ownerName;

        public AnimalDescription(int age, boolean isFriendly, String ownerName) {
            this.age = age;
            this.isFriendly = isFriendly;
            this.ownerName = ownerName;
        }

        public AnimalDescription() {
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AnimalDescription that = (AnimalDescription) o;
            return age == that.age && isFriendly == that.isFriendly && Objects.equals(ownerName, that.ownerName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age, isFriendly, ownerName);
        }

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(age);
            out.writeBoolean(isFriendly);
            out.writeUTF(ownerName);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            age = in.readInt();
            isFriendly = in.readBoolean();
            ownerName = in.readUTF();
        }
    }
}
