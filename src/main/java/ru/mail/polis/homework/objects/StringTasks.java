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

        StringBuilder builder = new StringBuilder();
        boolean singleDot = false;
        boolean singleMinus = false;
        boolean singleE = false;
        char prev = ' ';

        for (char c : str.toCharArray()) {
            if (c == '.') {
                if (singleDot) {
                    return null;
                }
                singleDot = true;
            } else if (c == '-') {
                if (prev != 'e') {
                    if (singleMinus) {
                        return null;
                    }
                    singleMinus = true;
                }
            } else if (c == 'e') {
                if (singleE) {
                    return null;
                }
                singleE = true;
            } else if (!(c >= '0' && c <= '9')) {
                continue;
            }
            prev = c;
            builder.append(c);
        }
        if (!(prev >= '0' && prev <= '9')) {
            return null;
        }

        if (singleMinus && builder.charAt(0) != '-') {
            return null;
        }

        String res = builder.toString();
        if (singleE || singleDot) {
            return Double.parseDouble(res);
        } else {
            Long val = Long.parseLong(res);
            if (val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE) {
                return Integer.parseInt(res);
            }
            return val;
        }
    }
}
