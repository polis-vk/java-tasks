package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
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
        StringBuilder res = new StringBuilder();
        try {
            processObject(object, object.getClass(), res);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return res.toString();
    }

    private static void processObject(Object object, Class<?> clazz, StringBuilder res) throws IllegalAccessException {
        TreeMap<String, String> name_values = new TreeMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!field.isAnnotationPresent(SkipField.class) && !isModifierSet(field.getModifiers(), Modifier.STATIC)) {
                if (field.getType().isArray()) {
                    name_values.put(field.getName(), processArray(field, object));
                } else if (isPrimitiveOrPrimitiveWrapperOrStringOrEnum(field.getType())) {
                    name_values.put(field.getName(), String.valueOf(field.get(object)));
                } else {
                    Object obj = field.get(object);
                    if (obj != null) {
                        processObject(obj, obj.getClass(), res);
                        name_values.put(field.getName(), res.toString());
                    } else {
                        name_values.put(field.getName(), "null");
                    }
                }
            }
        }
        if (clazz.getSuperclass() != Object.class) {
            res.delete(0, res.length());
            processObject(object, clazz.getSuperclass(), res);
            if (res.length() != 2) {
                res.deleteCharAt(0);
                res.deleteCharAt(res.length() - 1);
                if (!name_values.isEmpty()) {
                    res.insert(0, ", ");
                }
            }
        }
        res.insert(0, mapToString(name_values));
        res.append('}');
    }

    private static String mapToString(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        if (!i.hasNext())
            return "{";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Map.Entry<String, String> e = i.next();
            sb.append(e.getKey()).append(": ").append(e.getValue());
            if (!i.hasNext())
                return sb.toString();
//            .append('}')
            sb.append(',').append(' ');
        }
    }

    private static boolean isPrimitiveOrPrimitiveWrapperOrStringOrEnum(Class<?> type) {
        return type.isPrimitive() || type.isEnum() ||
                type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class || type == String.class;
    }

    private static String processArray(Field field, Object object) throws IllegalAccessException {
        Object arr = field.get(object);
        if (arr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        int length = Array.getLength(arr);
        for (int i = 0; i < length; i++) {
            sb.append(Array.get(arr, i)).append(", ");
        }
        if (length != 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(']');
        return sb.toString();
    }

    private static boolean isModifierSet(int allModifiers, int specificModifier) {
        return (allModifiers & specificModifier) > 0;
    }

}
