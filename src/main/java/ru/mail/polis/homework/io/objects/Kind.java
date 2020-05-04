package ru.mail.polis.homework.io.objects;

import java.io.Serializable;

public class Kind implements Serializable {
    private final String name;
    private final long populationSize;
    
    public Kind(String name, long populationSize) {
        this.name = name;
        this.populationSize = populationSize;
    }
    
    public String getName() {
        return name;
    }
    
    public long getPopulationSize() {
        return populationSize;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Kind that = (Kind) obj;
        return this.populationSize == that.populationSize
                && this.name.equals(that.name);
    }
    
    @Override
    public String toString() {
        return "Kind{" +
                "name='" + name + '\'' +
                ", populationSize=" + populationSize +
                '}';
    }
}

