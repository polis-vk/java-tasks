package ru.mail.polis.homework.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Array;
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
        List<Field> currentClassFields = new LinkedList<>();
        while (clazz != null) {
            currentClassFields.addAll(getSortedAndFilteredFields(clazz));
            clazz = clazz.getSuperclass();
        }
        if (currentClassFields.size() == 0) {
            return "{}";
        }
        StringBuffer objectInfo = new StringBuffer("{");
        for (Field field : currentClassFields) {
            field.setAccessible(true);
            try {
                if (field.getType().isArray()) {
                    objectInfo.append(field.getName());
                    objectInfo.append(": ");
                    if (field.getType().getComponentType().isPrimitive()) {
                        List<Object> array = new ArrayList<>();
                        if (field.get(object) == null) {
                            objectInfo.append(field.get(object));
                        } else {
                            for (int i = 0; i < Array.getLength(field.get(object)); i++) {
                                array.add(Array.get(field.get(object), i));
                            }
                            objectInfo.append(array);
                        }
                    } else {
                        objectInfo.append(Arrays.toString((Object[]) field.get(object)));
                    }
                    objectInfo.append(", ");
                } else if (field.getType().isLocalClass()) {
                    Objects.requireNonNull(getSortedAndFilteredFields(field.getClass())).forEach(field1 ->
                    {
                        try {
                            objectInfo.append(field1.getName()).append(": ").append(field1.get(field1.getClass() + ", "));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    objectInfo.append(field.getName()).append(": ").append(field.get(object)).append(", ");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        objectInfo.delete(objectInfo.length() - 2, objectInfo.length()).append('}');
        return objectInfo.toString();
    }

    public static List<Field> getSortedAndFilteredFields(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())
                        && !Arrays.stream(field.getAnnotations()).collect(Collectors.groupingBy(Annotation::annotationType)).containsKey((SkipField.class)))
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());
    }
}
