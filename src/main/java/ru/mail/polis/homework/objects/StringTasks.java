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
        StringBuilder res = new StringBuilder();
        boolean hasDot = false;
        boolean hasExponent = false;
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
                if (!hasExponent && !hasDot && res.length() > 0) {
                    res.append(c);
                    hasDot = true;
                    continue;
                }
                return null;
            }
            if (c == 'e') {
                if (!hasExponent && res.length() > 0) {
                    res.append(c);
                    hasExponent = true;
                    continue;
                }
                return null;
            }
        }
        // исключительные случаи когда результативная строка оканчивается на 'e' или '-' например 1.3e или 1.3e-
        if (res.charAt(res.length() - 1) == 'e' || res.charAt(res.length() - 1) == '-') {
            return null;
        }
        if (hasDot || hasExponent) {
            return Double.parseDouble(res.toString());
        }
        long temp = Long.parseLong(res.toString());
        if (temp >= Integer.MIN_VALUE && temp <= Integer.MAX_VALUE) {
            return (int) temp;
        }
        return temp;
    }
}
