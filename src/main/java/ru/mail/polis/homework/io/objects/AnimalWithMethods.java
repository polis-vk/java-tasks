package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.*;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    public enum Diet {
        PREDATOR,
        SCAVENGER,
        HERBIVORE,
        OMNIVORE
    }

    private String name;
    private Diet diet;
    private Genotype genotype;
    private int speciesId;
    private List<Integer> scaredOf;
    private boolean singleCell;

    public AnimalWithMethods(String name,
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
        return Collections.unmodifiableList(scaredOf);
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
        AnimalWithMethods that = (AnimalWithMethods) o;
        return speciesId == that.speciesId &&
                singleCell == that.singleCell &&
                Objects.equals(name, that.name) &&
                diet == that.diet &&
                Objects.equals(genotype, that.genotype) &&
                Objects.equals(scaredOf, that.scaredOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, diet, genotype, speciesId, scaredOf, singleCell);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeObject(diet);
        out.writeObject(genotype);
        out.writeInt(speciesId);
        out.writeInt(scaredOf.size());
        for (Integer integer : scaredOf) {
            out.writeInt(integer);
        }
        out.writeBoolean(singleCell);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        diet = (Diet) in.readObject();
        genotype = (Genotype) in.readObject();
        speciesId = in.readInt();
        int size = in.readInt();
        scaredOf = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scaredOf.add(in.readInt());
        }
        singleCell = in.readBoolean();
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

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeBoolean(isImmutable);
            out.writeInt(chromosomes.size());
            for (Chromosome chromosome : chromosomes) {
                out.writeObject(chromosome);
            }
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            isImmutable = in.readBoolean();
            int size = in.readInt();
            chromosomes = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                chromosomes.add((Chromosome) in.readObject());
            }
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

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.writeBoolean(isImmutable);
            out.writeInt(genome.length);
            for (int i = 0; i < genome.length; i++) {
                out.writeInt(genome[i]);
            }
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            isImmutable = in.readBoolean();
            int size = in.readInt();
            genome = new int[size];
            for (int i = 0; i < size; i++) {
                genome[i] = in.readInt();
            }
        }
    }
}
