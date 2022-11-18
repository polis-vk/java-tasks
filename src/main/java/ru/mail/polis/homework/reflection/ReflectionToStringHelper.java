package ru.mail.polis.homework.reflection;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final String nullToString = "null";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return nullToString;
        }

        StringBuilder stringBuilder = new StringBuilder();
        Class<?> currentReflectionClass = object.getClass();

        while (currentReflectionClass != null) {
            Set<Field> fields = getFields(currentReflectionClass);
            fields.stream()
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> addToFinalString(stringBuilder, object, field));
            currentReflectionClass = currentReflectionClass.getSuperclass();
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }

        return '{' + stringBuilder.toString() + '}';
    }

    public static Set<Field> getFields(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .filter(field -> !field.isAnnotationPresent(SkipField.class))
                .collect(Collectors.toSet());
    }

    public static void addToFinalString(StringBuilder stringBuilder, Object object, Field field) {
        stringBuilder.append(field.getName() + ": ");
        field.setAccessible(true);

        Object fieldValue = isNullField(object, field);

        if (fieldValue == null) {
            stringBuilder.append(nullToString + ", ");
            return;
        }

        if (field.getType().isArray()) {
            stringBuilder.append('[');
            int sizeArray = Array.getLength(fieldValue);

            for (int i = 0; i < sizeArray; i++) {
                stringBuilder.append(Array.get(fieldValue, i) + ", ");
            }

            if (sizeArray > 0) {
                deleteExtraComma(stringBuilder);
            }

            stringBuilder.append(']');
        } else {
            stringBuilder.append(fieldValue);
        }

        stringBuilder.append(", ");
        field.setAccessible(false);
    }

    private static Object isNullField(Object object, Field field) {
        Object fieldValue = null;

        try {
            fieldValue = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return fieldValue;
    }

    private static void deleteExtraComma(StringBuilder stringBuilder) {
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
    }
}