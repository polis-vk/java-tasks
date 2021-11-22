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

    public static String reflectiveToString(Object object) {
        StringBuilder sbResult = new StringBuilder();
        sbResult.append("{");
        if (object == null) {
            return "null";
        }
        Class<?> currentClass = object.getClass();
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            Arrays.sort(fields, Comparator.comparing(Field::getName));
            for (Field field : fields) {
                if ((field.getModifiers() & Modifier.STATIC) != 0 ||
                        field.getAnnotationsByType(SkipField.class).length != 0) {
                    continue;
                }
                try {
                    parseField(object, field, sbResult);
                } catch (IllegalAccessException ignored) {
                }
                sbResult.append(", ");
            }
            currentClass = currentClass.getSuperclass();
        }
        if (sbResult.length() != 1) {
            sbResult.delete(sbResult.length() - 2, sbResult.length());
        }
        sbResult.append("}");
        return sbResult.toString();
    }

    private static void parseField(Object object, Field field, StringBuilder sbResult) throws IllegalAccessException {
        sbResult.append(field.getName()).append(": ");
        if (!field.canAccess(object)) {
            field.setAccessible(true);
        }
        Object value = field.get(object);
        if (value == null) {
            sbResult.append("null");
            return;
        }
        if (value.getClass().isArray()) {
            sbResult.append("[");
            for (int i = 0; i < Array.getLength(value); i++) {
                sbResult.append(Array.get(value, i));
                sbResult.append(", ");
            }
            if (Array.getLength(value) != 0) {
                sbResult.delete(sbResult.length() - 2, sbResult.length());
            }
            sbResult.append("]");
        } else {
            sbResult.append(value);
        }
    }
}
