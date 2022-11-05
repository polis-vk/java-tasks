package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {

    private String name;
    private boolean isDomestic;
    private boolean haveClaws;
    private int legsCount;
    private AnimalTypeWithMethods animalTypeWithMethods;
    private OrganizationWithMethods organizationWithMethods;

    public AnimalWithMethods(String name, boolean isDomestic, boolean haveClaws, int legsCount,
                             AnimalTypeWithMethods animalTypeWithMethods,
                             OrganizationWithMethods organizationWithMethods) {
        this.name = name;
        this.isDomestic = isDomestic;
        this.haveClaws = haveClaws;
        this.legsCount = legsCount;
        this.animalTypeWithMethods = animalTypeWithMethods;
        this.organizationWithMethods = organizationWithMethods;
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

    public AnimalTypeWithMethods getAnimalTypeWithMethods() {
        return animalTypeWithMethods;
    }

    public OrganizationWithMethods getOrganizationWithMethods() {
        return organizationWithMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animalWithMethods = (AnimalWithMethods) o;
        return isDomestic == animalWithMethods.isDomestic && haveClaws == animalWithMethods.haveClaws &&
                legsCount == animalWithMethods.legsCount && Objects.equals(name, animalWithMethods.name) &&
                animalTypeWithMethods == animalWithMethods.animalTypeWithMethods &&
                Objects.equals(organizationWithMethods, animalWithMethods.organizationWithMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isDomestic, haveClaws, legsCount, animalTypeWithMethods, organizationWithMethods);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", isDomestic=" + isDomestic +
                ", haveClaws=" + haveClaws +
                ", legsCount=" + legsCount +
                ", animalTypeWithMethods=" + animalTypeWithMethods +
                ", organizationWithMethods=" + organizationWithMethods +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        AnimalDataByte animalDataByte = new AnimalDataByte(this);
        out.writeByte(animalDataByte.getByte());

        if (animalDataByte.isAnimalNotNull()) {
            if (animalDataByte.isNameNotNull()) {
                out.writeUTF(name);
            }
            out.writeInt(legsCount);
            if (animalDataByte.isTypeNotNull()) {
                out.writeUTF(animalTypeWithMethods.name());
            }
            if (animalDataByte.isOrgNotNull()) {
                out.writeObject(organizationWithMethods);
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        AnimalDataByte animalDataByte = new AnimalDataByte(in.readByte());

        name = animalDataByte.isNameNotNull() ? in.readUTF() : null;
        isDomestic = animalDataByte.isDomestic();
        haveClaws = animalDataByte.isHaveClaws();
        legsCount = in.readInt();
        animalTypeWithMethods = animalDataByte.isTypeNotNull() ? AnimalTypeWithMethods.valueOf(in.readUTF()) : null;
        organizationWithMethods = animalDataByte.isOrgNotNull() ? (OrganizationWithMethods) in.readObject() : null;
    }
}

enum AnimalTypeWithMethods {

    FISH,
    BIRD,
    MAMMAL,
    REPTILE,
    AMPHIBIAN,
    INVERTEBRATE
}

class OrganizationWithMethods implements Serializable {

    private String title;
    private boolean isCommercial;
    private int animalsCount;

    public OrganizationWithMethods(String title, boolean isCommercial, int animalsCount) {
        this.title = title;
        this.isCommercial = isCommercial;
        this.animalsCount = animalsCount;
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
        OrganizationWithMethods that = (OrganizationWithMethods) o;
        return isCommercial == that.isCommercial && animalsCount == that.animalsCount && Objects.equals(title,
                that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isCommercial, animalsCount);
    }

    @Override
    public String toString() {
        return "OrganizationWithMethods{" +
                "title='" + title + '\'' +
                ", isCommercial=" + isCommercial +
                ", animalsCount=" + animalsCount +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        OrganizationDataByte organizationDataByte = new OrganizationDataByte(this);
        out.writeByte(organizationDataByte.getByte());

        if (organizationDataByte.isOrgNotNull()) {
            if (organizationDataByte.isTitleNotNull()) {
                out.writeUTF(title);
            }
            out.writeInt(animalsCount);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        OrganizationDataByte organizationDataByte = new OrganizationDataByte(in.readByte());

        title = organizationDataByte.isTitleNotNull() ? in.readUTF() : null;
        isCommercial = organizationDataByte.isCommercial();
        animalsCount = in.readInt();
    }
}
