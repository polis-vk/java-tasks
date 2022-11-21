package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
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
        Class<?> clazz = object.getClass();
        StringBuilder objectInfo = new StringBuilder("{");
        while (clazz != null) {
            for (Field field : getSortedAndFilteredFields(clazz)) {
                allClassFieldsToString(field, object, objectInfo);
            }
            clazz = clazz.getSuperclass();
        }
        if (objectInfo.length() == 1) {
            return "{}";
        }
        objectInfo.delete(objectInfo.length() - 2, objectInfo.length()).append('}');
        return objectInfo.toString();
    }

    public static List<Field> getSortedAndFilteredFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())
                        && !field.isAnnotationPresent(SkipField.class))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }

    public static void arrayFieldProcessing(StringBuilder objectInfo, Field field, Object object) throws IllegalAccessException {
        objectInfo.append(field.getName()).append(": ");
        if (field.get(object) == null) {
            objectInfo.append(field.get(object));
        } else {
            objectInfo.append("[");
            for (int i = 0; i < Array.getLength(field.get(object)); i++) {
                if (Array.get(field.get(object), i) == null) {
                    objectInfo.append("null");
                } else {
                    objectInfo.append(Array.get(field.get(object), i).toString());
                }
                if (i != Array.getLength(field.get(object)) - 1) {
                    objectInfo.append(", ");
                }
            }
            objectInfo.append("]");
        }
        objectInfo.append(", ");
    }

    public static void allClassFieldsToString(Field field, Object object, StringBuilder objectInfo) {
        if (Modifier.isPrivate(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
            field.setAccessible(true);
        }
        try {
            if (field.getType().isArray()) {
                arrayFieldProcessing(objectInfo, field, object);
            } else {
                objectInfo.append(field.getName()).append(": ").append(field.get(object)).append(", ");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
