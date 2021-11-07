package ru.mail.polis.homework.io.blocking;


import java.util.Arrays;
import java.util.Objects;

/**
 * Нельзя изменять/удалять/добавлять поля.
 * Можно изменять/удалять СУЩЕСТВУЮЩИЕ методы.
 *
 * Все остальное можно
 */
public class Structure {

    public static final int UNDEFINED_INT = 0;
    public static final long UNDEFINED_LONG = -1_000_000;
    public static final String UNDEFINED_STRING = "UNDEFINED";
    public static final byte UNDEFINED_BYTE = Byte.MIN_VALUE;
    public static final float UNDEFINED_FLOAT = Float.NaN;
    public static final double UNDEFINED_DOUBLE = Double.NaN;

    private boolean flag1;
    private boolean flag2;
    private boolean flag3;
    private boolean flag4;
    private long id;
    //Nullable
    private String name;
    private double coeff;
    private byte param;
    //Nullable
    private SubStructure[] subStructures;

    public Structure() {
    }

    public Structure(boolean flag1, boolean flag2, boolean flag3, boolean flag4, long id,
                     String name, double coeff, byte param, SubStructure[] subStructures) {
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.flag3 = flag3;
        this.flag4 = flag4;
        this.id = id;
        this.name = name;
        this.coeff = coeff;
        this.param = param;
        this.subStructures = subStructures;
    }

    public boolean isFlagI(int i) {
        switch(i) {
            case 1:
                return isFlag1();
            case 2:
                return isFlag2();
            case 3:
                return isFlag3();
            case 4:
                return isFlag4();
            default:
                throw new IllegalArgumentException();
        }
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }

    public boolean isFlag4() {
        return flag4;
    }

    public void setFlag4(boolean flag4) {
        this.flag4 = flag4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    public byte getParam() {
        return param;
    }

    public void setParam(byte param) {
        this.param = param;
    }

    public SubStructure[] getSubStructures() {
        return subStructures;
    }

    public void setSubStructures(SubStructure[] subStructures) {
        this.subStructures = subStructures;
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

