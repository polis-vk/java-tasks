package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        String clean = str.replaceAll("[^e.\\- 0-9]", "");      // удаляем все лишние символы кроме нужных
        try {
            long num = Long.parseLong(clean);
            if (num <= Integer.MAX_VALUE & num >= Integer.MIN_VALUE) {   // приводим к int
                return (int) num;
            } else {
                return num;
            }
        } catch (Exception e) {
            try {
                return Double.parseDouble(clean);   // если не удалось привести ни к одному из видов, выводим null
            } catch (Exception i) {
                return null;
            }
        }
    }
}
