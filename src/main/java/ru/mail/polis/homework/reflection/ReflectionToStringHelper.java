package ru.mail.polis.homework.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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

    private static List<Field> fields = new ArrayList<>();

    public static String reflectiveToString(Object object) {
        if (object == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        fields = filterAndSortFields(object.getClass().getDeclaredFields());
        checkForSuperclass(object.getClass());
        sb.append("{");
        if (fields.size() > 0) {
            try {
                for (Field field : fields) {
                    sb.append(field.getName()).append(": ");
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null) {
                        if (value.getClass().isArray()) {
                            sb.append(Arrays.toString(convertToObjectArray(value)));
                        } else {
                            sb.append(field.get(object));
                        }
                    } else {
                        sb.append("null");
                    }
                    sb.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                sb.replace(sb.length() - 2, sb.length(), "");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private static List<Field> filterAndSortFields(Field[] fields) {
        return Arrays.stream(fields).filter(field -> !Modifier.isStatic(field.getModifiers())
                        && !field.isAnnotationPresent(SkipField.class))
                .sorted(Comparator.comparing(Field::getName)).collect(Collectors.toList());
    }

    private static void checkForSuperclass(Class clazz) {
        if (clazz.getSuperclass() != Object.class) {
            List<Field> superClassFields = filterAndSortFields(clazz.getSuperclass().getDeclaredFields());
            fields.addAll(superClassFields);
            checkForSuperclass(clazz.getSuperclass());
        }
    }

    private static Object[] convertToObjectArray(Object obj) {
        Class arrType = obj.getClass().getComponentType();
        if (arrType.isPrimitive()) {
            List<Object> primitiveArr = new ArrayList<>();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                primitiveArr.add(Array.get(obj, i));
            }
            return primitiveArr.toArray();
        } else {
            return (Object[]) obj;
        }
    }
}
