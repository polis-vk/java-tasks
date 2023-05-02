package ru.mail.polis.homework.reflection;

import ru.mail.polis.homework.reflection.objects.easy.Easy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

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
        StringBuilder res = new StringBuilder();
        res.append('{');
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> {
                        field.setAccessible(true);
                        res.append(field.getName()).append(':').append(' ');
                        try {
                            if (field.get(object).getClass().isArray()) {
                                writeArrayToResultStringBuilder(field.get(object), res);
                            } else {
                                res.append(field.get(object).toString());
                            }
                        } catch (NullPointerException e) {
                            res.append('n').append('u').append('l').append('l');
                        } catch (Exception e) {
                        }
                        res.append(',').append(' ');
                    });
            clazz = clazz.getSuperclass();
        }
        if (res.length() != 1) {
            res.delete(res.length() - 2, res.length());
        }
        res.append('}');
        return res.toString();
    }

    private static void writeArrayToResultStringBuilder(Object field, StringBuilder res) {
        int count = res.length();
        res.append('[');
        int length = Array.getLength(field);
        for (int i = 0; i < length; i++) {
            res.append(Array.get(field, i)).append(',').append(' ');
        }
        if (res.length() - count != 1) {
            res.delete(res.length() - 2, res.length());
        }
        res.append(']');
    }
}
