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
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.endsWith("-") || str.endsWith("e") || str.indexOf('.') != str.lastIndexOf('.') || str.indexOf('e') != str.lastIndexOf('e'))
            return null;

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                res.append(c);
                continue;
            }
            if (c == '-') {
                if (res.length() == 0 || res.charAt(res.length() - 1) == 'e') {
                    res.append(c);
                    continue;
                }
                return null;
            }
            if (c == '.') {
                if (res.length() > 0) {
                    res.append(c);
                    continue;
                }
                return null;
            }
            if (c == 'e') {
                if (res.length() > 0) {
                    res.append(c);
                    continue;
                }
                return null;
            }
        }

        if (res.indexOf("e") >= 0 || res.indexOf(".") >= 0) {
            return Double.valueOf(res.toString());
        }
        String toStr = res.toString();
        if (Long.parseLong(toStr) <= Integer.MAX_VALUE
                && Long.parseLong(toStr) >= Integer.MIN_VALUE) {
            return Integer.valueOf(toStr);
        }
        return Long.valueOf(toStr);
    }
}
