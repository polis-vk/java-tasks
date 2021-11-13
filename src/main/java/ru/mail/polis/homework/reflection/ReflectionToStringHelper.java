package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Необходимо реализовать метод reflectiveToString, который для произвольного объекта
 * возвращает его строковое описание в формате:
 *
 * {field_1: value_1, field_2: value_2, ..., field_n: value_n}
 *
 * где field_i - имя поля
 *     value_i - его строковое представление (String.valueOf),
 *               за исключением массивов, для которых value формируется как:
 *               [element_1, element_2, ..., element_m]
 *                 где element_i - строковое представление элемента (String.valueOf)
 *                 элементы должны идти в том же порядке, что и в массиве.
 *
 * Все null'ы следует представлять строкой "null".
 *
 * Порядок полей
 * Сначала следует перечислить в алфавитном порядке поля, объявленные непосредственно в классе объекта,
 * потом в алфавитном порядке поля объявленные в родительском классе и так далее по иерархии наследования.
 * Примеры можно посмотреть в тестах.
 *
 * Какие поля выводить
 * Необходимо включать только нестатические поля. Также нужно пропускать поля, помеченные аннотацией @SkipField
 *
 * Упрощения
 * Чтобы не усложнять задание, предполагаем, что нет циклических ссылок, inner классов, и transient полей
 *
 * Реализация
 * В пакете ru.mail.polis.homework.reflection можно редактировать только этот файл
 * или добавлять новые (не рекомендуется, т.к. решение вполне умещается тут в несколько методов).
 * Редактировать остальные файлы нельзя.
 *
 * Баллы
 * В задании 3 уровня сложности, для каждого свой набор тестов:
 *   Easy - простой класс, нет наследования, массивов, статических полей, аннотации SkipField (4 балла)
 *   Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 *   Hard - нужно реализовать все требования задания (10 баллов)
 *
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("{");
        try {
            Class<?> c = Class.forName(object.getClass().getName());
            getFieldsToString(c.getDeclaredFields(), sb, object);
            Class<?> parent = c.getSuperclass();
            while (parent != null) {
                getFieldsToString(parent.getDeclaredFields(), sb, object);
                parent = parent.getSuperclass();
            }
            if (sb.length() > 1) {
                sb.delete(sb.lastIndexOf(","), sb.lastIndexOf(" ") + 1);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sb.append("}");
        return sb.toString();
    }

    private static void getFieldsToString(Field[] fields, StringBuilder sb, Object object) {
        if (fields == null || fields.length == 0) {
            return;
        }
        List<StringBuilder> array = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            Object tmp = null;
            try {
                tmp = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (tmp != null && tmp.getClass().isArray()) {
                StringBuilder tmpArray = new StringBuilder("[");
                for (int i = 0; i < Array.getLength(tmp); i++) {
                    tmpArray.append(Array.get(tmp, i));
                    if (i != Array.getLength(tmp) - 1) {
                        tmpArray.append(", ");
                    }
                }
                tmpArray.append("]");
                tmp = tmpArray.toString();
            }
            array.add(new StringBuilder(field.getName()).append(": ").append(tmp == null ? "null" : String.valueOf(tmp))
                    .append(", "));
        }
        Collections.sort(array);
        for (StringBuilder s : array) {
            sb.append(s);
        }
    }
}
