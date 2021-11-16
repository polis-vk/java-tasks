package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;

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

        Class clazz = object.getClass();

        final StringBuilder builder = new StringBuilder();
        builder.append("{");

        // сортирую
        Map<String, Object> fields = new TreeMap<String, Object>();
        try {
            do {
                for (Field field : clazz.getDeclaredFields()) {
                    if (Modifier.isStatic(field.getModifiers())
                            || field.getAnnotation(SkipField.class) != null) {
                        continue;
                    }
                    field.setAccessible(true);
                    fields.put(field.getName(), field.get(object));
                }
                // добавляю в builder
                fields.forEach((name, value) -> appendValue(builder.append(name)
                        .append(": "), value).append(", "));
                fields.clear();
                clazz = clazz.getSuperclass();

            } while (clazz != Object.class);
            
        } catch (IllegalAccessException access) {
            RuntimeException runtime = new RuntimeException();
            runtime.addSuppressed(access);
            throw runtime;
        }
        
        // полей нет
        if (builder.length() == 1) {
            return "{}";
        }
        // удаляю последнее ", "
        builder.delete(builder.length() - 2, builder.length());
        builder.append("}");
        
        return builder.toString();
    }

    private static StringBuilder appendValue(StringBuilder builder, Object o) {
        if (o == null || o == null) {
            return builder.append("null");
        }

        if (o.getClass().isArray()) {
            int length = Array.getLength(o);
            if (length == 0) {
                return builder.append("[]");
            }
            builder.append("[");
            for (int i = 0; i < length; ++i) {
                // рекурсивный обход на случай если это n-мерный массив
                appendValue(builder, Array.get(o, i)).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("]");

            return builder;
        }

        return builder.append(o.toString());
    }
}
