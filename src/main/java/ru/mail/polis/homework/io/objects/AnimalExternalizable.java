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

    private String name;
    private boolean isDomestic;
    private boolean haveClaws;
    private int legsCount;
    private AnimalTypeExternalizable animalTypeExternalizable;
    private OrganizationExternalizable organizationExternalizable;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, boolean isDomestic, boolean haveClaws, int legsCount,
                                AnimalTypeExternalizable animalTypeExternalizable,
                                OrganizationExternalizable organizationExternalizable) {
        this.name = name;
        this.isDomestic = isDomestic;
        this.haveClaws = haveClaws;
        this.legsCount = legsCount;
        this.animalTypeExternalizable = animalTypeExternalizable;
        this.organizationExternalizable = organizationExternalizable;
    }

    public String getName() {
        return name;
    }

    public boolean isDomestic() {
        return isDomestic;
    }

    public boolean isHaveClaws() {
        return haveClaws;
    }

    public int getLegsCount() {
        return legsCount;
    }

    public AnimalTypeExternalizable getAnimalTypeExternalizable() {
        return animalTypeExternalizable;
    }

    public OrganizationExternalizable getOrganizationExternalizable() {
        return organizationExternalizable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable animalExternalizable = (AnimalExternalizable) o;
        return isDomestic == animalExternalizable.isDomestic && haveClaws == animalExternalizable.haveClaws &&
                legsCount == animalExternalizable.legsCount && Objects.equals(name, animalExternalizable.name) &&
                animalTypeExternalizable == animalExternalizable.animalTypeExternalizable &&
                Objects.equals(organizationExternalizable, animalExternalizable.organizationExternalizable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isDomestic, haveClaws, legsCount, animalTypeExternalizable,
                organizationExternalizable);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", isDomestic=" + isDomestic +
                ", haveClaws=" + haveClaws +
                ", legsCount=" + legsCount +
                ", animalTypeExternalizable=" + animalTypeExternalizable +
                ", organizationExternalizable=" + organizationExternalizable +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        AnimalDataByte animalDataByte = new AnimalDataByte(this);
        out.writeByte(animalDataByte.getByte());

        if (animalDataByte.isNameNotNull()) {
            out.writeUTF(name);
        }
        out.writeInt(legsCount);
        if (animalDataByte.isTypeNotNull()) {
            out.writeUTF(animalTypeExternalizable.name());
        }
        if (animalDataByte.isOrgNotNull()) {
            out.writeObject(organizationExternalizable);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        AnimalDataByte animalDataByte = new AnimalDataByte(in.readByte());

        name = animalDataByte.isNameNotNull() ? in.readUTF() : null;
        isDomestic = animalDataByte.isDomestic();
        haveClaws = animalDataByte.isHaveClaws();
        legsCount = in.readInt();
        animalTypeExternalizable = animalDataByte.isTypeNotNull() ? AnimalTypeExternalizable.valueOf(in.readUTF()) : null;
        organizationExternalizable = animalDataByte.isOrgNotNull() ? (OrganizationExternalizable) in.readObject() : null;
    }
}

enum AnimalTypeExternalizable {

    FISH,
    BIRD,
    MAMMAL,
    REPTILE,
    AMPHIBIAN,
    INVERTEBRATE
}

class OrganizationExternalizable implements Externalizable{

    private String title;
    private boolean isCommercial;
    private int animalsCount;

    public OrganizationExternalizable(String title, boolean isCommercial, int animalsCount) {
        this.title = title;
        this.isCommercial = isCommercial;
        this.animalsCount = animalsCount;
    }

    public OrganizationExternalizable() {
    }

    public String getTitle() {
        return title;
    }

    public boolean isCommercial() {
        return isCommercial;
    }

    public int getAnimalsCount() {
        return animalsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationExternalizable that = (OrganizationExternalizable) o;
        return isCommercial == that.isCommercial && animalsCount == that.animalsCount && Objects.equals(title,
                that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isCommercial, animalsCount);
    }

    @Override
    public String toString() {
        return "OrganizationExternalizable{" +
                "title='" + title + '\'' +
                ", isCommercial=" + isCommercial +
                ", animalsCount=" + animalsCount +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        OrganizationDataByte organizationDataByte = new OrganizationDataByte(this);
        out.writeByte(organizationDataByte.getByte());

        if (organizationDataByte.isTitleNotNull()) {
            out.writeUTF(title);
        }
        out.writeInt(animalsCount);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        OrganizationDataByte organizationDataByte = new OrganizationDataByte(in.readByte());

        title = organizationDataByte.isTitleNotNull() ? in.readUTF() : null;
        isCommercial = organizationDataByte.isCommercial();
        animalsCount = in.readInt();
    }
}

