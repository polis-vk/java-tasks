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
        StringBuilder currentStr = new StringBuilder(str);
        boolean hasDote = false;
        boolean hasE = false;

        for (int i = 0; i < str.length(); i++) {
            while (i != currentStr.length()
                    && currentStr.charAt(i) != 'e'
                    && currentStr.charAt(i) != '-'
                    && currentStr.charAt(i) != '.'
                    && !Character.isDigit(currentStr.charAt(i))) {
                currentStr.deleteCharAt(i);
            }
            if (currentStr.length() == i) {
                break;
            }
            if (currentStr.charAt(i) == '-') {
                if (i != 0) {
                    if (currentStr.charAt(i - 1) != 'e') {
                        return null;
                    }
                }
            } else if (currentStr.charAt(i) == '.') {
                if (i == 0 || !Character.isDigit(currentStr.charAt(i - 1)) || hasDote) {
                    return null;
                }
                hasDote = true;
            } else if (currentStr.charAt(i) == 'e') {
                if (i == 0 || !Character.isDigit(currentStr.charAt(i - 1)) || hasE || i == currentStr.length() - 1) {
                    return null;
                }
                hasE = true;
            } else {
                continue;
            }
        }
        String resultStr = currentStr.toString();
        if (resultStr.isEmpty()) {
            return null;
        }
        if (hasDote || hasE) {
            return Double.valueOf(resultStr);
        }
        Long result = Long.valueOf(resultStr);
        if (result.intValue() == result.longValue()) {
            return Integer.valueOf(resultStr);
        } else {
            return result;
        }
    }
}
