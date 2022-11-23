package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
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
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {
    private static void appendClassesList(Class<?> c, List<Class<?>> classes) {
        while (c != null) {
            classes.add(c);
            c = c.getSuperclass();
        }
    }

    private static List<Field> getSortedDeclaredFields(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .sorted(Comparator.comparing(Field::getName))
                .filter(field -> !field.toString().contains("static"))
                .filter(field -> field.getAnnotation(SkipField.class) == null)
                .collect(Collectors.toList());
    }

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }

        StringBuilder stringBuilder = new StringBuilder();
        List<Class<?>> classes = new ArrayList<>();
        appendClassesList(object.getClass(), classes);

        stringBuilder.append("{");
        try {
            for (Class<?> clazz : classes) {
                for (Field field : getSortedDeclaredFields(clazz)) {
                    field.setAccessible(true);

                    if (!field.getType().isArray()) {
                        stringBuilder.append(field.getName()).append(": ").append(field.get(object)).append(", ");
                    } else if (field.get(object) == null) {
                        stringBuilder.append(field.getName()).append(": ").append("null").append(", ");
                    } else {
                        List<Object> array = new ArrayList<>();
                        for (int i = 0; i < Array.getLength(field.get(object)); i++) {
                            array.add(Array.get(field.get(object), i));
                        }
                        stringBuilder.append(field.getName()).append(": ").append(array).append(", ");
                    }
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        int newLength = stringBuilder.lastIndexOf(", ");
        if (newLength > 0) {
            stringBuilder.setLength(newLength);
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
