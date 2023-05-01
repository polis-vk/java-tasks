package ru.mail.polis.homework.reflection;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
        StringBuilder sb = new StringBuilder("{");
        Class<?> clazz = object.getClass();
        for (Field f : getDeclaredFieldsInCorrectOrder(clazz)) {
            if (Modifier.isStatic(f.getModifiers()) || f.isAnnotationPresent(SkipField.class)) {
                continue;
            }
            String fieldName = f.getName();
            f.setAccessible(true);
            String value;
            try {
                if (f.getType().isArray()) {
                    value = getValuesForArrayField(object, f);
                } else {
                    value = f.get(object).toString();
                }
            } catch (NullPointerException | IllegalAccessException e) {
                value = "null";
            }
            sb.append(fieldName)
                    .append(": ")
                    .append(value)
                    .append(", ");
        }
        if (sb.length() > 1) {
            return sb.substring(0, sb.length() - 2) + "}";
        }
        return sb.append("}").toString();
    }

    private static List<Field> getDeclaredFieldsInCorrectOrder(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(Arrays.stream(clazz.getDeclaredFields())
                    .sorted(Comparator.comparing(Field::getName))
                    .collect(Collectors.toList()));
            clazz = clazz.getSuperclass();
        } while (clazz != Object.class);
        return fields;
    }

    private static String getValuesForArrayField(Object object, Field f) throws IllegalAccessException {
        Object arrayField = f.get(object);
        Object[] fieldsValues = new Object[Array.getLength(arrayField)];
        for (int i = 0; i < fieldsValues.length; i++)
            fieldsValues[i] = Array.get(arrayField, i);
        return Arrays.toString(fieldsValues);
    }
}
