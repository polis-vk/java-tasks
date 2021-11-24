package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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

    public static final String ITEM_SEPARATOR = ", ";
    public static final String FIELD_SEPARATOR = ": ";
    public static final String NULL = "null";
    public static final char OBJECT_BEGIN = '{';
    public static final char OBJECT_END = '}';
    public static final char ARRAY_BEGIN = '[';
    public static final char ARRAY_END = ']';

    public static String reflectiveToString(Object object) {
        return append(new StringBuilder(), object).toString();
    }

    private static StringBuilder append(StringBuilder sb, Object object) {
        if (object == null) {
            return sb.append(NULL);
        }
        sb.append(OBJECT_BEGIN);
        boolean first = true;
        for (Class<?> aClass = object.getClass(); aClass != null; aClass = aClass.getSuperclass()) {
            Field[] fields = aClass.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                if (shouldSkip(field)) {
                    continue;
                }
                if (first) {
                    first = false;
                } else {
                    sb.append(ITEM_SEPARATOR);
                }
                field.setAccessible(true);
                try {
                    sb.append(field.getName()).append(FIELD_SEPARATOR);
                    if (field.getType().isArray()) {
                        appendArray(sb, field.get(object));
                    } else {
                        sb.append(field.get(object));
                    }
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return sb.append(OBJECT_END);
    }

    private static StringBuilder appendArray(StringBuilder sb, Object objects) {
        if (objects == null) {
            return sb.append(NULL);
        }
        sb.append(ARRAY_BEGIN);
        boolean first = true;
        for (int i = 0; i < Array.getLength(objects); i++) {
            if (first) {
                first = false;
            } else {
                sb.append(ITEM_SEPARATOR);
            }
            sb.append(Array.get(objects, i));
        }
        return sb.append(ARRAY_END);
    }

    private static boolean shouldSkip(Field field) {
        return field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers());
    }
}
