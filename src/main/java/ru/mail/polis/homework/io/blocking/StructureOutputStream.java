package ru.mail.polis.homework.io.blocking;

import java.io.*;

/**
 * Вам нужно реализовать StructureOutputStream, который умеет писать данные в файл.
 * Писать поля нужно ручками, с помощью массива байт и методов {@link #write(int)}, {@link #write(byte[])} и так далее
 * 3 балла
 */
public class StructureOutputStream extends FileOutputStream {

    protected int written;

    public StructureOutputStream(File name) throws FileNotFoundException {
        super(name);
    }

    /**
     * Метод должен вернуть записать прочитанную структуру.
     */
    public void write(Structure structure) throws IOException {
        this.writeLong(structure.getId());
        this.writeUTF(structure.getName());
        this.write(structure.getSubStructures());
        this.writeDouble(structure.getCoeff());
        byte k = (byte) (
                (((byte) (structure.isFlag1() ? 1 : 0)) << 3)
                        + (((byte) (structure.isFlag2() ? 1 : 0)) << 2)
                        + (((byte) (structure.isFlag3() ? 1 : 0)) << 1)
                        + (((byte) (structure.isFlag4() ? 1 : 0)))
        );
        this.writeByte(k);
        this.writeByte(structure.getParam());
    }

    /**
     * Метод должен вернуть записать массив прочитанных структур.
     */
    public void write(Structure[] structures) throws IOException {
        for (Structure structure : structures) {
            this.write(structure);
        }
    }

    public void write(SubStructure subStructure) throws IOException {
        this.writeInt(subStructure.getId());
        this.writeUTF(subStructure.getName());
        this.writeBoolean(subStructure.isFlag());
        this.writeDouble(subStructure.getScore());
    }

    public void write(SubStructure[] subStructures) throws IOException {
        if (subStructures == null) {
            this.writeInt(-1);
            return;
        }
        this.writeInt(subStructures.length);
        for (SubStructure subStructure : subStructures) {
            this.write(subStructure);
        }
    }

    private void incCount(int value) {
        int temp = written + value;
        if (temp < 0) {
            temp = Integer.MAX_VALUE;
        }
        written = temp;
    }

    public final void writeBoolean(boolean v) throws IOException {
        super.write(v ? 1 : 0);
        incCount(1);
    }

    public final void writeByte(int v) throws IOException {
        super.write(v);
        incCount(1);
    }

    private final byte[] writeBuffer = new byte[8];

    public final void writeInt(int v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 24);
        writeBuffer[1] = (byte) (v >>> 16);
        writeBuffer[2] = (byte) (v >>> 8);
        writeBuffer[3] = (byte) (v);
        super.write(writeBuffer, 0, 4);
        incCount(4);
    }

    public final void writeLong(long v) throws IOException {
        writeBuffer[0] = (byte) (v >>> 56);
        writeBuffer[1] = (byte) (v >>> 48);
        writeBuffer[2] = (byte) (v >>> 40);
        writeBuffer[3] = (byte) (v >>> 32);
        writeBuffer[4] = (byte) (v >>> 24);
        writeBuffer[5] = (byte) (v >>> 16);
        writeBuffer[6] = (byte) (v >>> 8);
        writeBuffer[7] = (byte) (v);
        super.write(writeBuffer, 0, 8);
        incCount(8);
    }

    public final void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    void writeUTF(String str) throws IOException {
        if (str == null) {
            writeInt(-1);
            return;
        }
        final int strlen = str.length();
        long utflen = strlen; // optimized for ASCII

        for (int i = 0; i < strlen; i++) {
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0)
                utflen += (c >= 0x800) ? 2 : 1;
        }

        if (utflen > Integer.MAX_VALUE || /* overflow */ utflen < strlen)
            throw new UTFDataFormatException(tooLongMsg(str, (int) utflen));

        final byte[] bytearr = new byte[(int) utflen + 4];

        int count = 0;
        bytearr[count++] = (byte) ((utflen >>> 24) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 16) & 0xFF);
        bytearr[count++] = (byte) ((utflen >>> 8) & 0xFF);
        bytearr[count++] = (byte) (utflen & 0xFF);

        int i;
        for (i = 0; i < strlen; i++) { // optimized for initial run of ASCII
            int c = str.charAt(i);
            if (c >= 0x80 || c == 0) break;
            bytearr[count++] = (byte) c;
        }

        for (; i < strlen; i++) {
            int c = str.charAt(i);
            if (c < 0x80 && c != 0) {
                bytearr[count++] = (byte) c;
            } else if (c >= 0x800) {
                bytearr[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                bytearr[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                bytearr[count++] = (byte) (0x80 | ((c) & 0x3F));
            } else {
                bytearr[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                bytearr[count++] = (byte) (0x80 | ((c) & 0x3F));
            }
        }
        super.write(bytearr, 0, (int) utflen + 4);
    }

    private static String tooLongMsg(String s, int bits32) {
        int slen = s.length();
        String head = s.substring(0, 8);
        String tail = s.substring(slen - 8, slen);
        // handle int overflow with max 3x expansion
        long actualLength = (long) slen + Integer.toUnsignedLong(bits32 - slen);
        return "encoded string (" + head + "..." + tail + ") too long: "
                + actualLength + " bytes";
    }

    public final int size() {
        return written;
    }
}
