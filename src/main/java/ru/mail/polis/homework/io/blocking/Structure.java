package ru.mail.polis.homework.io.blocking;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Нельзя изменять/удалять/добавлять поля.
 * Можно изменять/удалять СУЩЕСТВУЮЩИЕ методы.
 *
 * Все остальное можно
 */
public class Structure implements Serializable{

    public static final int UNDEFINED_INT = 0;
    public static final long UNDEFINED_LONG = -1_000_000;
    public static final String UNDEFINED_STRING = "UNDEFINED";
    public static final byte UNDEFINED_BYTE = Byte.MIN_VALUE;
    public static final float UNDEFINED_FLOAT = Float.NaN;
    public static final double UNDEFINED_DOUBLE = Double.NaN;

    private long id;
    //Nullable
    private String name;
    //Nullable
    private SubStructure[] subStructures;
    private float coeff;
    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private byte param;

    public long getId() {
        return id;
    }

    public Structure setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Structure setName(String name) {
        this.name = name;
        return this;
    }

    public SubStructure[] getSubStructures() {
        return subStructures;
    }

    public Structure setSubStructures(SubStructure[] subStructures) {
        this.subStructures = subStructures;
        return this;
    }

    public float getCoeff() {
        return coeff;
    }

    public Structure setCoeff(float coeff) {
        this.coeff = coeff;
        return this;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public Structure setFlag1(boolean flag1) {
        this.flag1 = flag1;
        return this;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public Structure setFlag2(boolean flag2) {
        this.flag2 = flag2;
        return this;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public Structure setFlag3(boolean flag3) {
        this.flag3 = flag3;
        return this;
    }

    public boolean isFlag4() {
        return flag4;
    }

    public Structure setFlag4(boolean flag4) {
        this.flag4 = flag4;
        return this;
    }

    public byte getParam() {
        return param;
    }

    public Structure setParam(byte param) {
        this.param = param;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Structure structure = (Structure) o;
        return id == structure.id && Double.compare(structure.coeff, coeff) == 0 && flag1 == structure.flag1
                && flag2 == structure.flag2 && flag3 == structure.flag3 && flag4 == structure.flag4
                && param == structure.param && Objects.equals(name, structure.name)
                && Arrays.equals(subStructures, structure.subStructures);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, coeff, flag1, flag2, flag3, flag4, param);
        result = 31 * result + Arrays.hashCode(subStructures);
        return result;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subStructures=" + Arrays.toString(subStructures) +
                ", coeff=" + coeff +
                ", flag1=" + flag1 +
                ", flag2=" + flag2 +
                ", flag3=" + flag3 +
                ", flag4=" + flag4 +
                ", param=" + param +
                '}';
    }
}
