package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Objects;

public class GeneralInformationWithMethods implements Serializable {
    private static final byte HABITAT_BYTE = 0b100;
    private static final byte IS_LISTED_IN_RED_BOOK_BYTE = 0b010;
    private static final byte IS_DANGEROUS_BYTE = 0b001;

    private Habitat habitat;
    private long populationSize;
    private boolean isListedInTheRedBook;
    private boolean isDangerous;

    public GeneralInformationWithMethods(Habitat habitat, long populationSize, boolean isListedInTheRedBook, boolean isDangerous) {
        this.habitat = habitat;
        this.populationSize = populationSize;
        this.isListedInTheRedBook = isListedInTheRedBook;
        this.isDangerous = isDangerous;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public long getPopulationSize() {
        return populationSize;
    }

    public boolean isListedInTheRedBook() {
        return isListedInTheRedBook;
    }

    public boolean isDangerous() {
        return isDangerous;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralInformationWithMethods anotherInformation = (GeneralInformationWithMethods) o;
        return Objects.equals(habitat, anotherInformation.getHabitat())
                && populationSize == anotherInformation.getPopulationSize()
                && isDangerous == anotherInformation.isDangerous()
                && isListedInTheRedBook == anotherInformation.isListedInTheRedBook();
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitat, populationSize, isDangerous, isListedInTheRedBook);
    }

    @Override
    public String toString() {
        return "GeneralInformationWithMethods{" +
                "habitat:" + habitat +
                ", populationSize:" + populationSize +
                ", listedInTheRedBook:" + isListedInTheRedBook +
                ", isDangerous:" + isDangerous + '}';
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(getByteFromData());
        if (habitat != null) {
            out.writeUTF(String.valueOf(habitat));
        }
        out.writeLong(populationSize);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte byteDataFromInput = in.readByte();
        if ((byteDataFromInput & HABITAT_BYTE) != 0) {
            habitat = Habitat.valueOf(in.readUTF());
        }
        isListedInTheRedBook = (byteDataFromInput & IS_LISTED_IN_RED_BOOK_BYTE) != 0;
        isDangerous = (byteDataFromInput & IS_DANGEROUS_BYTE) != 0;
        populationSize = in.readLong();
    }

    private byte getByteFromData() {
        return (byte) (getBooleanData() | getNullableElements());
    }

    private byte getBooleanData() {
        byte result = 0;
        if (isListedInTheRedBook) {
            result |= IS_LISTED_IN_RED_BOOK_BYTE;
        }
        if (isDangerous) {
            result |= IS_DANGEROUS_BYTE;
        }
        return result;
    }

    private byte getNullableElements() {
        byte result = 0;
        if (habitat != null) {
            result |= HABITAT_BYTE;
        }
        return result;
    }
}
