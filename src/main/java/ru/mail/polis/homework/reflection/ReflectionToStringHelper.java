package ru.mail.polis.homework.reflection;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

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

    private static final String NULL = "null";
    private static final String SEPARATOR_BETWEEN_NAME_VALUE = ": ";
    private static final String SEPARATOR_BETWEEN_MEANINGS = ", ";

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return NULL;
        }
        StringBuilder result = new StringBuilder("{");

        try {
            Class<?> clazz = Class.forName(object.getClass().getName());
            getFieldsFromObject(clazz.getDeclaredFields(), object, result);
            Class<?> superClazz = clazz.getSuperclass();
            while (superClazz != null) {
                getFieldsFromObject(superClazz.getDeclaredFields(), object, result);
                superClazz = superClazz.getSuperclass();
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        if (result.length() > 1) {
            result.setLength(result.length() - 2);
        }
        return result.append("}").toString();
    }

    private static void getFieldsFromObject(Field[] fields, Object object, StringBuilder buffer)
            throws IllegalAccessException {
        if (buffer == null || fields == null || fields.length == 0) {
            return;
        }

        Arrays.sort(fields, Comparator.comparing(Field::getName));
        Object valueOfField;
        for (Field field : fields) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) || field.isAnnotationPresent(SkipField.class)) {
                continue;
            }
            valueOfField = field.get(object);
            buffer.append(field.getName()).append(SEPARATOR_BETWEEN_NAME_VALUE);

            if (valueOfField != null && valueOfField.getClass().isArray()) {
                getArrayFromField(buffer, valueOfField);
            } else {
                buffer.append(valueOfField);
            }
            buffer.append(SEPARATOR_BETWEEN_MEANINGS);
        }
    }

    private static void getArrayFromField(@NotNull StringBuilder bufferFields, @NotNull Object valueOfField) {
        bufferFields.append("[");
        for (int i = 0; i < Array.getLength(valueOfField) - 1; i++) {
            bufferFields.append(Array.get(valueOfField, i))
                    .append(SEPARATOR_BETWEEN_MEANINGS);
        }
        if (Array.getLength(valueOfField) > 0) {
            bufferFields.append(Array.get(valueOfField, Array.getLength(valueOfField) - 1));
        }
        bufferFields.append("]");
    }

}
