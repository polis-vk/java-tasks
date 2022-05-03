package ru.mail.polis.homework.reflection;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        // TODO: implement
        if (object == null) {
            return "null";
        }
        StringBuilder result = new StringBuilder("{");
        Class<?> objClass = object.getClass();
        do {
            recordFields(objClass, object, result);
            objClass = objClass.getSuperclass();
        } while (!objClass.equals(Object.class));
        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }
        return result.append("}").toString();
    }

    public static void recordFields(Class<?> objClass, Object object, StringBuilder result) {
        Field[] fieldsData = objClass.getDeclaredFields();
        Arrays.sort(fieldsData, Comparator.comparing(Field::getName));
        try {
            for (Field field : fieldsData) {
                if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                result.append(field.getName());
                result.append(": ");
                field.setAccessible(true);
                if (field.get(object) != null && field.getType().isArray()) {
                    arraysToStringBuilder(field.get(object), result);
                } else {
                    result.append(field.get(object));
                }
                result.append(", ");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void arraysToStringBuilder(Object object, StringBuilder result) {
        result.append("[");
        Object buff;
        for (int i = 0; i < Array.getLength(object); i++) {
            buff = Array.get(object, i);
            if (buff != null) {
                result.append(Array.get(object, i));
            } else {
                result.append(buff);
            }
            result.append(", ");
        }
        if (Array.getLength(object) > 0) {
            result.setLength(result.length() - 2);
        }
        result.append("]");
    }
}
