package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
        Field[] fields = object.getClass().getDeclaredFields();
        Map<String, String> fieldsMap = new HashMap<>();
        Object value;
        try {
            for (Field field :
                    fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                if (field.isAnnotationPresent(SkipField.class)) {
                    continue;
                }
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                value = field.get(object);
                if (value == null) {
                    fieldsMap.put(field.getName(), "null");
                    continue;
                }
                if (field.getType().isArray()) {
                    fieldsMap.put(field.getName(), arrayToString(value));
                    continue;
                }
                fieldsMap.put(field.getName(), value.toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return createString(fieldsMap);
    }

    private static String arrayToString(Object value) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < Array.getLength(value); i++) {
            result.append(Array.get(value, i));
            if (i == Array.getLength(value) - 1) {
                break;
            }
            result.append(", ");
        }
        return result.append("]").toString();
    }

    private static String createString(Map<String, String> fieldsMap) {
        StringBuilder result = new StringBuilder("{");
        Map<String, String> sortedFieldsMap = new TreeMap<>(fieldsMap);
        Map.Entry<String, String>[] entrySet = sortedFieldsMap.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < entrySet.length; i++) {
            result.append(entrySet[i].getKey());
            result.append(": ");
            result.append(entrySet[i].getValue());
            if (i == entrySet.length - 1) {
                break;
            }
            result.append(", ");
        }
        return result.append("}").toString();
    }
}
