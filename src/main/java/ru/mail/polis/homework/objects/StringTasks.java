package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) return null;
        if (str.endsWith("-") || str.endsWith("e") || str.indexOf('.') != str.lastIndexOf('.') || str.indexOf('e') != str.lastIndexOf('e'))
            return null;

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '-' || str.charAt(i) == '.' || str.charAt(i) == 'e' || Character.isDigit(str.charAt(i))) {
                res.append(str.charAt(i));
            }
        }

        char c = '.';
        for (int i = 0; i < res.length(); i++) {
            if ((res.charAt(i) == '-' && (res.charAt(i) == res.charAt(i + 1) || res.charAt(i + 1) == 'e')) ||
                    (Character.isDigit(c) && res.charAt(i) == '-') ||
                    (res.charAt(res.length() - 1) == 'e')) {
                return null;
            }
            c = res.charAt(i);
        }

        if (res.indexOf("e") >= 0 || res.indexOf(".") >= 0) {
            return Double.valueOf(res.toString());
        } else if (Long.parseLong(res.toString()) <= Integer.MAX_VALUE
                && Long.parseLong(res.toString()) >= Integer.MIN_VALUE) {
            return Integer.valueOf(res.toString());
        }
        return Long.valueOf(res.toString());
    }
}
