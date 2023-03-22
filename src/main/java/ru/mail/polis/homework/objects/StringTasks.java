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
        boolean isNegative = false;
        boolean singleE = false;
        char prev = ' ';
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '.') {
                if (singleDot) {
                    return null;
                }
                singleDot = true;
            } else if (c == '-') {
                if (prev != 'e') {
                    if (isNegative) {
                        return null;
                    }
                    isNegative = true;
                }
            } else if (c == 'e') {
                if (singleE) {
                    return null;
                }
                singleE = true;
            } else if (!Character.isDigit(c)) {
                continue;
            }
            prev = c;
            builder.append(c);
        }

        if (!Character.isDigit(prev)) {
            return null;
        }

        if (isNegative && builder.charAt(0) != '-') {
            return null;
        }

        if (builder.charAt(0) == 'e' || builder.charAt(0) == '.') {
            return null;
        }

        String res = builder.toString();
        if (singleE || singleDot) {
            return Double.parseDouble(res);
        } else {
            long val = Long.parseLong(res);
            if (val >= Integer.MIN_VALUE && val <= Integer.MAX_VALUE) {
                return Integer.parseInt(res);
            }
            return val;
        }
    }
}
