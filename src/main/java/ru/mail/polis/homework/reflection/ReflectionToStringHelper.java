package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
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

    public static final String NULL = "null";
    public static final String ARRAY_DELIMITER = ", ";
    public static final Predicate<Field> isStaticOrSkipField =
            (field) -> !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class);


    // working with object's classes (exact class and super classes)
    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder("{");
        try {
            for (Class<?> clazz = object.getClass(); ; clazz = clazz.getSuperclass()) {
                proceedClass(object, clazz, stringBuilder);

                // Adding delimiter if need
                if (clazz.getSuperclass() == Object.class)
                    break;
                stringBuilder.append(ARRAY_DELIMITER);
            }
        } catch (IllegalAccessException e) {
            System.err.println("IllegalAccessException while working" + e.getMessage());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    // Working with exact class (not super class)
    private static void proceedClass(Object object, Class<?> clazz, StringBuilder stringBuilder) throws IllegalAccessException {
        List<Field> listOfFields = getClassFields(clazz);

        Field lastField = listOfFields.get(listOfFields.size() - 1);
        for (Field field : listOfFields) {
            proceedField(object, field, stringBuilder);

            // Adding delimiter if need
            if (field == lastField)
                break;
            stringBuilder.append(ARRAY_DELIMITER);
        }
    }

    private static List<Field> getClassFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .filter(isStaticOrSkipField)
                .collect(Collectors.toList());
    }

    private static void proceedField(Object object, Field field, StringBuilder stringBuilder) throws IllegalAccessException {
        field.setAccessible(true);
        stringBuilder.append(field.getName()).append(": ");

        if (field.getType().isArray())
            stringBuilder.append((Object) Arrays.deepToString(covertToArray(field.get(object))));
        else {
            stringBuilder.append(field.get(object));
        }
    }

    public static Object[] covertToArray(Object value) {
        if (value == null) return null;
        if (value.getClass().isArray()) { // array of Object predecessors
            if (value instanceof Object[]) {
                return (Object[]) value;
            } else { // array of primitives types
                Object[] boxedArray = new Object[Array.getLength(value)];
                for (int index = 0; index < boxedArray.length; index++) {
                    boxedArray[index] = Array.get(value, index);
                }
                return boxedArray;
            }
        } else throw new IllegalArgumentException("Not an array");
    }
}
