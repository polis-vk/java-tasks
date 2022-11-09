package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class GeneralInformationExternalizable implements Externalizable {
    private Habitat habitat;
    private long populationSize;
    private boolean isListedInTheRedBook;
    private boolean isDangerous;

    public GeneralInformationExternalizable() {
    }

    public GeneralInformationExternalizable(Habitat habitat, long populationSize, boolean isListedInTheRedBook, boolean isDangerous) {
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
        GeneralInformationExternalizable anotherInformation = (GeneralInformationExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        if (habitat == null) {
            out.writeBoolean(false);
        }
        else {
            out.writeBoolean(true);
            out.writeUTF(String .valueOf(habitat));
        }
        out.writeLong(populationSize);
        out.writeBoolean(isListedInTheRedBook);
        out.writeBoolean(isDangerous);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        if (in.readBoolean()){
            habitat = Habitat.valueOf(in.readUTF());
        }
        else {
            habitat = null;
        }
        populationSize = in.readLong();
        isListedInTheRedBook = in.readBoolean();
        isDangerous = in.readBoolean();
    }
}
