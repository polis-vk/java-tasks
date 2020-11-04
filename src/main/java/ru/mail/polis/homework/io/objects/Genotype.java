package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Genotype implements Serializable {
    private List<Chromosome> chromosomes;
    private boolean isImmutable;

    public Genotype(List<Chromosome> chromosomes, boolean isImmutable) {
        this.chromosomes = new ArrayList<>(chromosomes.size());
        this.isImmutable = isImmutable;
    }

    public List<Chromosome> getChromosomes(int index) {
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
}