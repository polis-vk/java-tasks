package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

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

        if (object.getClass().getDeclaredFields().length == 0) {
            return "{}";
        }

        return "{" + reflect(object.getClass(), object) + "}";
    }

    private static String reflect(Class<?> clazz, Object object) {
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return "";
        }
        Arrays.sort(fields, Comparator.comparingInt(o -> o.getName().charAt(0)));
        StringBuilder result = new StringBuilder();

        for (Field field : fields) {
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            result.append(field.getName()).append(": ");
            if (value != null && field.getType().isArray()) {
                result.append(arrayToString(value));
            } else {
                result.append(value);
            }
            result.append(", ");
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));

        Class<?> superClazz = clazz.getSuperclass();
        if (!Objects.equals(superClazz, Object.class)) {
            result.append(", ");
            result.append(reflect(superClazz, object));
        }

        return result.toString();
    }

    private static String arrayToString(Object array) {
        int length = Array.getLength(array);
        if (length == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");

        for (int i = 0; i < length; i++) {
            if (Array.get(array, i) == null) {
                result.append("null").append(", ");
            } else {
                result.append(Array.get(array, i)).append(", ");
            }
        }

        return result.substring(0, result.length() - 2) + "]";
    }
}
