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
        StringBuilder objectString = new StringBuilder("{");

        Class<?> clazz = object.getClass();
        do {
            appendFields(clazz, object, objectString);
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        if (objectString.length() == 1) {
            return "{}";
        }
        objectString.setLength(objectString.length() - 2);
        return objectString.append('}').toString();
    }

    private static void appendFields(Class<?> clazz, Object obj, StringBuilder data) {
        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
        if (fields.isEmpty()) return;
        fields.sort(Comparator.comparing(Field::getName));
        for (Field field : fields) {
            if (!field.isAnnotationPresent(SkipField.class) && !Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                Object value = null;
                try {
                    value = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                data.append(field.getName()).append(": ");
                if (value == null) {
                    data.append("null, ");
                    continue;
                }
                if (field.getType().isArray()) {
                    printArray(value, data);
                    continue;
                }
                data.append(value).append(", ");
            }
        }
    }

    private static void printArray(Object arr, StringBuilder data) {
        data.append("[");
        Object value;
        int i = 0;
        for (; i < Array.getLength(arr); i++) {
            value = Array.get(arr, i);
            data.append((value != null) ? value : "null").append(", ");
        }
        if (i != 0) {
            data.setLength(data.length() - 2);
        }
        data.append("], ");
    }
}
