package ru.mail.polis.homework.io.blocking;

import java.util.Objects;

/**
 * Нельзя изменять/удалять/добавлять поля.
 * Можно изменять/удалять СУЩЕСТВУЮЩИЕ методы.
 */
public class SubStructure {
    private final int id;
    //NotNull
    private final String name;
    private final boolean flag;
    private final double score;

    public SubStructure(int id, String name, boolean flag, double score) {
        this.id = id;
        this.name = name;
        this.flag = flag;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFlag() {
        return flag;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubStructure that = (SubStructure) o;
        return id == that.id && flag == that.flag && Double.compare(that.score, score) == 0 && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flag, score);
    }

    @Override
    public String toString() {
        return "SubStructure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", flag=" + flag +
                ", score=" + score +
                '}';
    }
}
