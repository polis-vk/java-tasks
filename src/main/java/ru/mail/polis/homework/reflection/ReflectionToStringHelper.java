package ru.mail.polis.homework.reflection;

import ru.mail.polis.homework.reflection.objects.easy.Easy;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

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
        if(object == null){
            return "null";
        }

        Class<?> clazz = object.getClass();
        StringBuilder stringBuilder = new StringBuilder();

        Field[] fields = clazz.getDeclaredFields();
        Comparator<Field> fieldComparator = Comparator.comparing(Field::getName);
        Arrays.sort(fields, fieldComparator);

        int countOfFields = fields.length, countCommas = 0, countOfFieldsWithAnnotationSkip = 0;

        stringBuilder.append("{");
        for (Field field : fields) {
            field.setAccessible(true);

            if (field.getType().isArray()) {
                Object valueField = null;
                field.setAccessible(true);
                try {
                    valueField = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (valueField != null) {
                    stringBuilder.append(field.getName()).append(": ");
                    stringBuilder.append("[");
                    if (Array.getLength(valueField) > 0) {
                        stringBuilder.append(Array.get(valueField, 0));
                    }
                    for (int i = 1; i < Array.getLength(valueField); i++) {
                        stringBuilder.append(", ").append(Array.get(valueField, i));
                    }
                    stringBuilder.append("]");
                    field.setAccessible(false);
                    return stringBuilder.append("}").toString();
                }
            }
            int mod = field.getModifiers();
            if (field.isAnnotationPresent(SkipField.class) || Modifier.isStatic(mod)) {
                countOfFieldsWithAnnotationSkip++;
                continue;
            }

            String nameOfField = field.getName();
            stringBuilder.append(nameOfField).append(": ");
            Object valueOfField = null;
            try {
                valueOfField = field.get(object);
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
            stringBuilder.append(valueOfField);
            if (countOfFields == countCommas + 1) {
                stringBuilder.append("}");
                return stringBuilder.toString();
            }
            stringBuilder.append(", ");
            countCommas++;
            field.setAccessible(false);
        }

        if(countOfFieldsWithAnnotationSkip != 0){
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length() ,"}");
            return stringBuilder.toString();
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
