package ru.mail.polis.homework.io.blocking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Вам нужно реализовать StructureInputStream, который умеет читать данные из файла.
 * Читать поля нужно ручками, с помощью массива байт и методов {@link #read()}, {@link #read(byte[])} и так далее
 * 3 балла
 */
public class StructureInputStream extends FileInputStream {

    //private Structure[] structures;
    List<Structure> structures = new ArrayList<>();

    public StructureInputStream(File fileName) throws FileNotFoundException {
        super(fileName);
    }


    /**
     * Метод должен вернуть следующую прочитанную структуру.
     * Если структур в файле больше нет, то вернуть null
     */
    public final Structure readStructure() {
        Structure structure;
        try {
            long id = this.readLong();
            String name = this.readUTF();
            SubStructure[] subStructures = this.readSubStructures();
            double coeff = this.readDouble();
            byte k = (byte) super.read();
            boolean flag1 = (k & 8) != 0;
            boolean flag2 = (k & 4) != 0;
            boolean flag3 = (k & 2) != 0;
            boolean flag4 = (k & 1) != 0;
            byte param = (byte) super.read();
            structure = new Structure(id, name, subStructures, coeff, flag1, flag2, flag3, flag4, param);
            structures.add(structure);
            return structure;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Метод должен вернуть все структуры, которые есть в файле.
     * Если файл уже прочитан, но возвращается полный массив.
     */
    public Structure[] readStructures() throws IOException {
        while (available() != 0) {
            readStructure();
        }
        return structures.toArray(new Structure[0]);
    }

    public SubStructure readSubStructure() throws IOException {
        final int id = this.readInt();
        final String name = this.readUTF();
        final boolean flag = this.readBoolean();
        final double score = this.readDouble();
        return new SubStructure(id, name, flag, score);
    }

    public SubStructure[] readSubStructures() throws IOException {
        int len = this.readInt();
        if (len == -1) {
            return null;
        }
        SubStructure[] subStructures = new SubStructure[len];

        for (int i = 0; i < len; i++) {
            subStructures[i] = readSubStructure();
        }
        return subStructures;
    }

    public final void readFully(byte[] b, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);
        int n = 0;
        while (n < len) {
            int count = super.read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    public final boolean readBoolean() throws IOException {
        int ch = super.read();
        if (ch < 0)
            throw new EOFException();
        return (ch != 0);
    }

    private final byte[] readBuffer = new byte[8];

    public final long readLong() throws IOException {
        readFully(readBuffer, 0, 8);
        return (((long) readBuffer[0] << 56) +
                ((long) (readBuffer[1] & 255) << 48) +
                ((long) (readBuffer[2] & 255) << 40) +
                ((long) (readBuffer[3] & 255) << 32) +
                ((long) (readBuffer[4] & 255) << 24) +
                ((readBuffer[5] & 255) << 16) +
                ((readBuffer[6] & 255) << 8) +
                ((readBuffer[7] & 255)));
    }

    public final int readInt() throws IOException {
        readFully(readBuffer, 0, 4);
        return (((readBuffer[0] & 255) << 24) +
                ((readBuffer[1] & 255) << 16) +
                ((readBuffer[2] & 255) << 8) +
                ((readBuffer[3] & 255)));
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final String readUTF() throws IOException {
        int utflen = this.readInt();

        if (utflen == -1) {
            return null;
        }
        byte[] bytearr = new byte[utflen];
        char[] chararr = new char[utflen];

        int c, char2, char3;
        int count = 0;
        int chararr_count = 0;

        this.readFully(bytearr, 0, utflen);

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            if (c > 127) break;
            count++;
            chararr[chararr_count++] = (char) c;
        }

        while (count < utflen) {
            c = (int) bytearr[count] & 0xff;
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    /* 0xxxxxxx*/
                    count++;
                    chararr[chararr_count++] = (char) c;
                    break;
                case 12:
                case 13:
                    /* 110x xxxx   10xx xxxx*/
                    count += 2;
                    if (count > utflen)
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    char2 = bytearr[count - 1];
                    if ((char2 & 0xC0) != 0x80)
                        throw new UTFDataFormatException(
                                "malformed input around byte " + count);
                    chararr[chararr_count++] = (char) (((c & 0x1F) << 6) |
                            (char2 & 0x3F));
                    break;
                case 14:
                    /* 1110 xxxx  10xx xxxx  10xx xxxx */
                    count += 3;
                    if (count > utflen)
                        throw new UTFDataFormatException(
                                "malformed input: partial character at end");
                    char2 = bytearr[count - 2];
                    char3 = bytearr[count - 1];
                    if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80))
                        throw new UTFDataFormatException(
                                "malformed input around byte " + (count - 1));
                    chararr[chararr_count++] = (char) (((c & 0x0F) << 12) |
                            ((char2 & 0x3F) << 6) |
                            ((char3 & 0x3F)));
                    break;
                default:
                    /* 10xx xxxx,  1111 xxxx */
                    throw new UTFDataFormatException(
                            "malformed input around byte " + count);
            }
        }
        // The number of chars produced may be less than utflen
        return new String(chararr, 0, chararr_count);
    }
}
