package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 * <p>
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 * <p>
 * где field_i - имя поля
 * value_i - его строковое представление (String.valueOf),
 * за исключением массивов, для которых value формируется как:
 * [element_1, element_2, ..., element_m]
 * где element_i - строковое представление элемента (String.valueOf)
 * элементы должны идти в том же порядке, что и в массиве.
 * <p>
 * Все null'ы следует представлять строкой "null".
 * <p>
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 * <p>
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 * <p>
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 * <p>
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 * <p>
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 * Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 * Easy + Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Easy + Medium + Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 10 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }

        StringBuilder result = new StringBuilder("{");

        for (Field field : getAllFields(object)) {
            result.append(field.getName()).append(":").append(" ");

            boolean isPublic = Modifier.isPublic(field.getModifiers());
            if (!isPublic) {
                field.setAccessible(true);
            }
            appendFieldToStringBuilder(result, field, object);
            if (!isPublic) {
                field.setAccessible(false);
            }

            result.append(",").append(" ");
        }
        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }
        return result.append('}').toString();
    }

    private static void appendFieldToStringBuilder(StringBuilder stringBuilder, Field field, Object object) {
        Object fieldData;

        try {
            fieldData = field.get(object);
        } catch (IllegalAccessException error) {
            throw new RuntimeException("ACCESS ERROR : " + error);
        }

        if (fieldData == null) {
            stringBuilder.append("null");
            return;
        }

        if (field.getType().isArray()) {
            appendArrayFieldToStringBuilder(stringBuilder, fieldData);
            return;
        }

        stringBuilder.append(fieldData);
    }

    private static void appendArrayFieldToStringBuilder(StringBuilder stringBuilder, Object fieldData) {
        stringBuilder.append('[');
        if (Array.getLength(fieldData) > 0) {
            stringBuilder.append(Array.get(fieldData, 0));

            for (int i = 1; i < Array.getLength(fieldData); i++) {
                stringBuilder.append(",").append(" ").append(Array.get(fieldData, i));
            }
        }
        stringBuilder.append(']');
    }

    private static List<Field> getAllFields(Object object) {
        List<Field> result = new ArrayList<>();

        for (Class<?> objectClass = object.getClass(); objectClass != Object.class; objectClass = objectClass.getSuperclass()) {
            result.addAll(Arrays.stream(objectClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList()));
        }
        return result;
    }
}
