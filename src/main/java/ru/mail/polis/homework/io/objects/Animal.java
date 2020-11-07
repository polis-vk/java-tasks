package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.*;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы одно поле должен быть списком! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {
    public enum Diet {
        PREDATOR,
        SCAVENGER,
        HERBIVORE,
        OMNIVORE
    }

    private String name;
    private final Diet diet;
    private final Genotype genotype;
    private final int speciesId;
    private final List<Integer> scaredOf;
    private final boolean singleCell;

    public Animal(String name,
                  Diet diet,
                  Genotype genotype,
                  int speciesId,
                  List<Integer> scaredOf,
                  boolean singleCell) {
        this.name = name;
        this.diet = diet;
        this.genotype = genotype;
        this.speciesId = speciesId;
        this.scaredOf = new ArrayList<>(scaredOf);
        this.singleCell = singleCell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genotype getGenotype() {
        return genotype;
    }

    public Diet getDiet() {
        return diet;
    }

    public int getSpeciesId() {
        return speciesId;
    }

    public List<Integer> getScaredOf() {
        return scaredOf;
    }

    public void scareOf(int speciesId) {
        scaredOf.add(speciesId);
    }

    public void notScareOf(int speciesId) {
        scaredOf.remove(Integer.valueOf(speciesId));
    }

    public boolean isSingleCell() {
        return singleCell;
    }

    public void writeObjectCustom(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeUTF(diet.name());
        genotype.writeObjectCustom(out);
        out.writeInt(speciesId);
        out.writeInt(scaredOf.size());
        for (Integer val : scaredOf) {
            out.writeInt(val);
        }
        out.writeBoolean(singleCell);
    }

    public static Animal readObjectCustom(ObjectInput in) throws IOException {
        String name = in.readUTF();
        Diet diet = Diet.valueOf(in.readUTF());
        Genotype genotype = Genotype.readObjectCustom(in);
        int speciesId = in.readInt();
        int size = in.readInt();
        List<Integer> scaredOf = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scaredOf.add(in.readInt());
        }
        return new Animal(name, diet, genotype, speciesId, scaredOf, in.readBoolean());
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", diet=" + diet +
                ", genotype=" + genotype +
                ", speciesId=" + speciesId +
                ", scaredOf=" + scaredOf +
                ", singleCell=" + singleCell +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return speciesId == animal.speciesId &&
                singleCell == animal.singleCell &&
                Objects.equals(name, animal.name) &&
                diet == animal.diet &&
                Objects.equals(genotype, animal.genotype) &&
                Objects.equals(scaredOf, animal.scaredOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diet, genotype, speciesId, scaredOf, singleCell);
    }

    public static class Genotype implements Serializable {
        private List<Chromosome> chromosomes;
        private boolean isImmutable;

        public Genotype(List<Chromosome> chromosomes, boolean isImmutable) {
            this.chromosomes = new ArrayList<>(chromosomes.size());
            this.isImmutable = isImmutable;
        }

        public List<Chromosome> getChromosomes() {
            if (isImmutable) {
                return Collections.unmodifiableList(chromosomes);
            }
            return chromosomes;
        }

        public int getSize() {
            return chromosomes.size();
        }

        public Chromosome getChromosome(int index) {
            return chromosomes.get(index);
        }

        public boolean isImmutable() {
            return isImmutable;
        }

        @Override
        public String toString() {
            return "Genotype{" +
                    "chromosomes=" + chromosomes +
                    ", isImmutable=" + isImmutable +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Genotype genotype = (Genotype) o;
            return isImmutable == genotype.isImmutable &&
                    Objects.equals(chromosomes, genotype.chromosomes);
        }

        @Override
        public int hashCode() {
            return Objects.hash(chromosomes, isImmutable);
        }

        public void writeObjectCustom(ObjectOutput out) throws IOException {
            out.writeBoolean(isImmutable);
            out.writeInt(chromosomes.size());
            for (Chromosome c : chromosomes) {
                c.writeObjectCustom(out);
            }
        }

        public static Genotype readObjectCustom(ObjectInput in) throws IOException {
            boolean isImmutable = in.readBoolean();
            int size = in.readInt();
            List<Chromosome> chromosomes = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                chromosomes.add(Chromosome.readObjectCustom(in));
            }
            return new Genotype(chromosomes, isImmutable);
        }
    }

    public static class Chromosome implements Serializable {
        private int[] genome;
        private boolean isImmutable;

        public Chromosome(int[] genes, boolean isImmutable) {
            this.genome = Arrays.copyOf(genes, genes.length);
            this.isImmutable = isImmutable;
        }

        public int[] getGenes() {
            return Arrays.copyOf(genome, genome.length);
        }

        public int getGene(int index) {
            return genome[index];
        }

        public boolean insertGenes(int[] genes, int index) {
            if (!isImmutable) {
                int[] newGenome = new int[genome.length + genes.length];
                System.arraycopy(genome, 0, newGenome, 0, index);
                System.arraycopy(genes, 0, newGenome, index, genes.length);
                System.arraycopy(genome, index, newGenome, index + genes.length, genome.length - index);
                genome = newGenome;
                return true;
            }
            return false;
        }

        public boolean cutGenes(int index, int length) {
            if (!isImmutable) {
                int[] newGenome = new int[genome.length - length];
                System.arraycopy(genome, 0, newGenome, 0, index);
                System.arraycopy(genome, index + length, newGenome, index, genome.length - index - length);
                genome = newGenome;
                return true;
            }
            return false;
        }

        public boolean isImmutable() {
            return isImmutable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Chromosome that = (Chromosome) o;
            return isImmutable == that.isImmutable &&
                    Arrays.equals(genome, that.genome);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(isImmutable);
            result = 31 * result + Arrays.hashCode(genome);
            return result;
        }

        @Override
        public String toString() {
            return "Chromosome{" +
                    "genome=" + Arrays.toString(genome) +
                    ", isImmutable=" + isImmutable +
                    '}';
        }

        public void writeObjectCustom(ObjectOutput out) throws IOException {
            out.writeBoolean(isImmutable);
            out.writeInt(genome.length);
            for (int i = 0; i < genome.length; i++) {
                out.writeInt(genome[i]);
            }
        }

        public static Chromosome readObjectCustom(ObjectInput in) throws IOException {
            boolean isImmutable = in.readBoolean();
            int size = in.readInt();
            int[] genome = new int[size];
            for (int i = 0; i < size; i++) {
                genome[i] = in.readInt();
            }
            return new Chromosome(genome, isImmutable);
        }
    }
}
