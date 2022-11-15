package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;

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

        Class<?> clazz = object.getClass();
        StringBuilder sb = new StringBuilder().append("{");
        AtomicBoolean isSomethingRecorded = new AtomicBoolean(false);

        while (clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> !Modifier.isStatic(field.getModifiers()))
                    .filter(field -> !field.isAnnotationPresent(SkipField.class))
                    .sorted(Comparator.comparing(Field::getName))
                    .forEach(field -> {
                        try {
                            if (!field.canAccess(object)) {
                                field.setAccessible(true);
                            }
                            sb.append(field.getName()).append(": ");
                            if (field.getType().isArray()) {
                                fillStringBuilderFromArray(sb, field.get(object));
                            } else {
                                sb.append(field.get(object));
                            }
                            sb.append(", ");
                            isSomethingRecorded.set(true);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    });

            clazz = clazz.getSuperclass();
        }

        if (isSomethingRecorded.get()) {
            removeExtraComma(sb);
        }
        return sb.append("}").toString();
    }

    private static void fillStringBuilderFromArray(StringBuilder sb, Object array) throws IllegalAccessException {
        if (array == null) {
            sb.append("null");
            return;
        }

        sb.append("[");
        AtomicBoolean isSomethingRecorded = new AtomicBoolean(false);

        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            sb.append(Array.get(array, i)).append(", ");
            isSomethingRecorded.set(true);
        }

        if (isSomethingRecorded.get()) {
            removeExtraComma(sb);
        }
        sb.append("]");
    }

    private static void removeExtraComma(StringBuilder sb) {
        sb.setLength(sb.length() - 2);
    }
}
