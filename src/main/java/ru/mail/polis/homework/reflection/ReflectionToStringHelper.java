package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        return "{" + classToString(object.getClass(), object) + "}";
    }

    private static String classToString(Class clazz, Object object) {
        StringBuilder result = new StringBuilder();
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class))
                .collect(Collectors.toList());

        if (fields.size() > 0) {
            List<String> fieldStrings = new ArrayList<>();
            fieldStrings.add(fieldToString(fields.get(0), object));
            for (int i = 1; i < fields.size(); i++) {
                fieldStrings.add(fieldToString(fields.get(i), object));
            }
            Collections.sort(fieldStrings);

            result.append(fieldStrings.get(0));
            for (int i = 1; i < fieldStrings.size(); i++) {
                result.append(", ").append(fieldStrings.get(i));
            }
        }

        Class superclass = clazz.getSuperclass();
        if (Object.class != superclass) {
            result.append(", ").append(classToString(superclass, object));
        }

        return result.toString();
    }

    private static String fieldToString(Field field, Object object) {
        StringBuilder result = new StringBuilder();
        field.setAccessible(true);

        try {
            result.append(field.getName())
                    .append(": ");
            Object value = field.get(object);

            if (value == null) {
                result.append("null");
            } else if (field.getType().isArray()) {
                result.append("[");
                int length = Array.getLength(value);
                if (length > 0) {
                    result.append(Array.get(value, 0));
                    for (int i = 1; i < length; i++) {
                        result.append(", ").append(Array.get(value, i));
                    }
                }
                result.append("]");
            } else {
                result.append(value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
