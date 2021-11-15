package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
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
 * Medium - нет наследования, массивов, но есть статические поля и поля с аннотацией SkipField (6 баллов)
 * Hard - нужно реализовать все требования задания (10 баллов)
 * <p>
 * Итого, по заданию можно набрать 20 баллов
 * Баллы могут снижаться за неэффективный или неаккуратный код
 */
public class ReflectionToStringHelper {

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }

        StringBuilder strB = new StringBuilder();
        strB.append("{");

        Class<?> clazz = object.getClass();
        String prefix = "";
        while (clazz != Object.class) {
            strB.append(prefix);
            addClassFields(strB, clazz, object);
            clazz = clazz.getSuperclass();
            prefix = ", ";
        }

        strB.append("}");

        return strB.toString();
    }

    private static void addClassFields(StringBuilder strB, Class<?> clazz, Object object) {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !(Modifier.isStatic(field.getModifiers())))
                .filter(field -> !(field.isAnnotationPresent(SkipField.class)))
                .collect(Collectors.toList());

        Map<String, Object> pairs = getPairs(fields, object);

        putPairs(strB, pairs);
    }

    private static Map<String, Object> getPairs(List<Field> fields, Object object) {
        HashMap<String, Object> pairs = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                pairs.put(field.getName(), field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return pairs;
    }

    private static void putPairs(StringBuilder strB, Map<String, Object> pairs) {
        List<String> names = new ArrayList<>(pairs.keySet());
        java.util.Collections.sort(names);

        String prefix = "";
        for (String name : names) {
            strB.append(prefix);
            strB.append(name);
            strB.append(": ");

            Object value = pairs.get(name);
            if (value != null && value.getClass().isArray()) {
                putArray(strB, value);
            } else {
                strB.append(value);
            }
            prefix = ", ";
        }
    }

    private static void putArray(StringBuilder strB, Object array) {
        strB.append("[");

        int length = Array.getLength(array);
        String prefix = "";
        for (int i = 0; i < length; i++) {
            strB.append(prefix);
            strB.append(Array.get(array, i));
            prefix = ", ";
        }

        strB.append("]");
    }
}
