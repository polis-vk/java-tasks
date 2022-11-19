package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
        StringBuilder stringBuilder = new StringBuilder("{");
        Map<String, String> fieldValueMap = new TreeMap<>();
        try {
            Class clazz = object.getClass();
            while (clazz != Object.class) {
                if (stringBuilder.length() != 1) {
                    stringBuilder.append(", ");
                }
                List<Field> fields = getFields(clazz.getDeclaredFields());
                fillMap(fieldValueMap, fields, object);
                stringBuilder.append(mapToString(fieldValueMap));
                fieldValueMap.clear();
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static void fillMap(Map<String, String> fieldValueMap,
                                List<Field> fields,
                                Object object) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            String value = "";
            if (field.getType().isArray()) {
                value = arrayToStirng(field.get(object));
            } else {
                value = field.get(object) == null ? "null" : field.get(object).toString();
            }
            fieldValueMap.put(field.getName(), value);
        }
    }

    private static String arrayToStirng(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < Array.getLength(object); i++) {
            stringBuilder.append(Array.get(object, i));
            if (i != Array.getLength(object) - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private static String mapToString(Map<String, String> fieldValueMap) {
        return fieldValueMap.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }

    private static List<Field> getFields(Field[] fields) {
        List<Field> fieldsList = new ArrayList<>();
        for (Field field : fields) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    !field.isAnnotationPresent(SkipField.class)) {
                fieldsList.add(field);
            }
        }
        return fieldsList;
    }
}
