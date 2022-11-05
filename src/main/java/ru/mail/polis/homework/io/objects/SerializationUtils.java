package ru.mail.polis.homework.io.objects;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public final class SerializationUtils {
    public static final byte NULL = -1;
    public static final byte NOT_NULL = 0;
    private static final byte FALSE = 0;
    private static final byte TRUE = 1;

    private SerializationUtils() {
        throw new AssertionError();
    }

    public static boolean byteToBoolean(byte value) {
        return value == TRUE;
    }

    public static byte booleanToByte(boolean value) {
        if (value) {
            return TRUE;
        }
        return FALSE;
    }

    public static String enumToString(Enum<?> e) {
        return e == null ? null : e.toString();
    }

    public static <T extends Enum<T>> T stringToEnum(String s, Class<T> enumClass) {
        return s == null ? null : Enum.valueOf(enumClass, s);
    }

    // It's used for preventing from boilerplate code
    public static void writeUTFOrNull(String s, DataOutput out) throws IOException {
        if (s == null) {
            out.writeByte(SerializationUtils.NULL);
        } else {
            out.writeByte(SerializationUtils.NOT_NULL);
            out.writeUTF(s);
        }
    }

    public static void writeUTFOrNull(String s, ObjectOutput out) throws IOException {
        if (s == null) {
            out.writeByte(SerializationUtils.NULL);
        } else {
            out.writeByte(SerializationUtils.NOT_NULL);
            out.writeUTF(s);
        }
    }

    public static String readUTFOrNull(DataInput in) throws IOException {
        byte read = in.readByte();
        if (read == NULL) {
            return null;
        }
        return in.readUTF();
    }

    public static String readUTFOrNull(ObjectInput in) throws IOException {
        byte read = in.readByte();
        if (read == NULL) {
            return null;
        }
        return in.readUTF();
    }
}
