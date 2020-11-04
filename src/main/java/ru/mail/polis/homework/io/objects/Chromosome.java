package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Chromosome implements Serializable {
    private int[] genome;
    private boolean isImmutable;

    public Chromosome(int[] genes, boolean isImmutable) {
        this.genome = Arrays.copyOf(genes, genes.length);
        this.isImmutable = isImmutable;
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
}
