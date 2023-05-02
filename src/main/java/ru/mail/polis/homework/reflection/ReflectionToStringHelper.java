package ru.mail.polis.homework.reflection;

import ru.mail.polis.homework.reflection.objects.easy.Easy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
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

        StringJoiner result = new StringJoiner(", ", "{", "}");
        for (Field field : getAllFields(object)) {
            result.add(getFieldDescription(field, object));
        }
        return result.toString();
    }

    private static String getFieldDescription(Field field, Object object) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
        String result = field.getName() + ": " + getFieldValueString(field, object);
        field.setAccessible(false);
        return result;
    }

    private static String getFieldValueString(Field field, Object object) {
        Object fieldData = null;
        try {
            fieldData = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Access error: " + e);
        }

        if (fieldData == null) {
            return "null";
        }

        if (!fieldData.getClass().isArray()) {
            return String.valueOf(fieldData);
        }
        StringJoiner arr = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < Array.getLength(fieldData); i++) {
            Object temp = Array.get(fieldData, i);
            arr.add(temp == null ? "null" : String.valueOf(temp));
        }
        return arr.toString();
    }

    private static List<Field> getAllFields(Object object) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> objectsClass = object.getClass(); objectsClass != null; objectsClass = objectsClass.getSuperclass()) {
            fields.addAll(Arrays.stream(objectsClass.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList()));
        }
        return fields;
    }
}
