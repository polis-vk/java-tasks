package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ReflectionToStringHelper {
    public static final String NULL = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL;
        }
        StringBuilder builder = new StringBuilder("{");
        Class<?> objectClass = object.getClass();
        while (objectClass != null) {
            try {
                builder.append(filter(objectClass.getDeclaredFields(), object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            objectClass = objectClass.getSuperclass();
        }
        if (builder.length() != 1) {
            builder.setLength(builder.length() - 2);
        }
        return builder.append("}").toString();
    }

    private static StringBuilder filter(Field[] fields, Object object) throws IllegalAccessException {
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class)) {
                continue;
            }
            field.setAccessible(true);
            builder.append(field.getName()).append(": ");
            addField(field.get(object), builder);
            builder.append(", ");
        }
        return builder;
    }

    private static void addField(Object object, StringBuilder builder) {
        if (object == null) {
            builder.append(NULL);
            return;
        }
        if (!object.getClass().isArray()) {
            builder.append(object);
            return;
        }
        builder.append("[");
        for (int i = 0; i < Array.getLength(object); ++i) {
            builder.append(Array.get(object, i));
            if (i != Array.getLength(object) - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
    }
}
