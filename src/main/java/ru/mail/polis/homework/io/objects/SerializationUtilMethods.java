package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SerializationUtilMethods {
    private static final byte NULL_BYTE = 0;
    private static final byte NOT_NULL_BYTE = 1;

    public static void writeString(String str, ObjectOutput out) throws IOException {
        if (str == null) {
            out.writeByte(NULL_BYTE);
            return;
        }
        out.writeByte(NOT_NULL_BYTE);
        out.writeUTF(str);
    }

    public static String readString(ObjectInput in) throws IOException {
        byte stringIsNull = in.readByte();
        if (stringIsNull == NULL_BYTE) {
            return null;
        }
        return in.readUTF();
    }

}
