package ru.mail.polis.homework.io.objects;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SerializerUtils {

    private static final byte TRUE = 1;
    private static final byte FALSE = 0;
    public static final byte NULL = -1;
    public static final byte NOT_NULL = 0;

    public static void writeOrNull(DataOutput output, String string) throws IOException {
        if (string == null) {
            output.writeByte(NULL);
        } else {
            output.writeByte(NOT_NULL);
            output.writeUTF(string);
        }
    }

    public static String readOrNull(DataInput input) throws IOException {
        return input.readByte() == NULL ? null : input.readUTF();
    }

    public static boolean byteToBoolean(byte value) {
        return value == TRUE;
    }

    public static byte booleanToByte(boolean value) {
        return value ? TRUE : FALSE;
    }

    public static String enumToString(Enum<?> enumObject) {
        return enumObject == null ? null : enumObject.toString();
    }

    public static <T extends Enum<T>> T stringToEnum(String string, Class<T> enumObject) {
        return string == null ? null : Enum.valueOf(enumObject, string);
    }
}
