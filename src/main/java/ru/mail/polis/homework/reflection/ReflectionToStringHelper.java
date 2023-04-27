package ru.mail.polis.homework.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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
        StringBuilder result = new StringBuilder();
        Class<?> givenClass = object.getClass();
        result.append(getFieldsInfo(object, givenClass));

        Class<?> superClass = givenClass.getSuperclass();

        while (superClass != null) {
            result.append(getFieldsInfo(object, superClass));
            superClass = superClass.getSuperclass();
        }

        return "{" + result.substring(0, result.length() - 2) + "}";
    }

    private static StringBuilder getFieldsInfo(Object object, Class<?> givenClass) {
        StringBuilder result = new StringBuilder();

        Arrays.stream(givenClass.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .forEach(field -> {
                    field.setAccessible(true);

                    String fieldName = field.getName();
                    try {
                        if (!Modifier.isStatic(field.getModifiers()) && field.getAnnotation(SkipField.class) == null) { //проверка поля на статичность и на присутствие аннотации SkipField
                            result.append(fieldName).append(": ").append(getValue(object, field)).append(", ");
                        }
                    } catch (IllegalAccessException e) {
                        result.append(fieldName).append(": null, ");
                    }

                    field.setAccessible(false);
                });

        return result;
    }

    private static String getValue(Object object, Field field) throws IllegalAccessException {
        if (field.get(object) == null) {
            return "null";
        }

        StringBuilder result = new StringBuilder();
        Class<?> fieldType = field.getType();

        if (fieldType.isArray()) {
            if (fieldType.equals(int[].class)) {
                int[] array = (int[]) field.get(object);
                if (array.length == 0) {
                    return "[]";
                }

                Arrays.stream(array).forEach(element -> result.append((element)).append(", "));
            } else {
                Object[] array = (Object[]) field.get(object);
                if (array.length == 0) {
                    return "[]";
                }

                Arrays.stream(array).forEach(element -> result.append((element)).append(", "));
            }
            //result.append(arrayToString(object, field));

            return "[" + result.substring(0, result.length() - 2) + "]";
        }

        return String.valueOf(field.get(object));
    }

    private static <T> String arrayToString(Object object, Field field) throws IllegalAccessException {
        StringBuilder result = new StringBuilder();

        T[] array = (T[]) field.get(object);
        if (array.length == 0) {
            return "[]";
        }

        Arrays.stream(array).forEach(element -> result.append((element)).append(", "));
        return result.toString();
    }
}