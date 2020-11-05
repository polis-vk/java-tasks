package ru.mail.polis.homework.io.objects;

import sun.jvm.hotspot.gc.shared.OopStorage;

import java.io.*;
import java.util.Objects;

public class BioTag implements Serializable {
    private long id;
    private String series;

    public BioTag() {
    }

    public BioTag(long id, String series) {
        this.id = id;
        this.series = series;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BioTag bioTag = (BioTag) o;
        return id == bioTag.id &&
                Objects.equals(series, bioTag.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series);
    }

    @Override
    public String toString() {
        return "BioTag{" +
                "id=" + id +
                ", series='" + series + '\'' +
                '}';
    }

    public void myWriteObject(ObjectOutputStream out) throws IOException {
        out.writeLong(id);
        out.writeUTF(series);
    }

    public void myReadObject(ObjectInputStream in) throws IOException {
        id =in.readLong();
        series = in.readUTF();
    }
}
