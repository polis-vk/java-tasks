package ru.mail.polis.homework.reflection;

import ru.mail.polis.homework.reflection.objects.easy.Easy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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

    public static void main(String[] args) {
        reflectiveToString(new Easy(1, "Damn", 12.5));
    }

    public static String reflectiveToString(Object object) {
        if (Objects.equals(object, null)) {
            return "null";
        }
        Class<?> clazz = object.getClass();
        List<Field> fields = new ArrayList<>();
        do {
            fields.addAll(
                    Arrays.stream(clazz.getDeclaredFields())
                            .filter(f -> (!Modifier.isStatic(f.getModifiers()) && !hasSkipFieldAnnotation(f)))
                            .sorted(Comparator.comparing(Field::getName))
                            .collect(Collectors.toList())
            );
            clazz = clazz.getSuperclass();
        }
        while (clazz != null);
        String result = null;
        try {
            result = getStringOfObject(object, fields);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getStringOfObject(Object obj, List<Field> fields) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Field f : fields) {
            if (Modifier.isPrivate(f.getModifiers())) {
                f.setAccessible(true);
            }
            if (f.getType().isArray()) {
                addStringOfArrayField(obj, f, stringBuilder);
            } else {
                stringBuilder.append(f.getName()).append(": ").append(f.get(obj)).append(", ");
            }
            f.setAccessible(false);
        }
        if (!fields.isEmpty()) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private static void addStringOfArrayField(Object obj, Field f, StringBuilder stringBuilder) throws IllegalAccessException {
        Object array = f.get(obj);
        stringBuilder.append(f.getName()).append(": ");
        if (array != null) {
            stringBuilder.append("[");
            int i;
            for (i = 0; i < Array.getLength(array); i++) {
                stringBuilder.append(Array.get(array, i)).append(", ");
            }
            if (i != 0) {
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            }
            stringBuilder.append("], ");
        } else {
            stringBuilder.append("null, ");
        }
    }

    private static boolean hasSkipFieldAnnotation(Field f) {
        Annotation[] annotations = f.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof SkipField) {
                return true;
            }
        }
        return false;
    }
}
