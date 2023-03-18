package ru.mail.polis.homework.objects;

import ru.mail.polis.homework.simple.DoubleTask;

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
        if ((str == null) || str.isEmpty()) return null;

        StringBuilder temp = new StringBuilder();
        char currentChar;
        boolean hasDot = false;
        boolean hasE = false;
        int minusCount = 0;

        for (int i = 0; i < str.length(); i++) {
            currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                temp.append(currentChar);
            } else if (currentChar == '.') {
                if (hasDot) {
                    return null;
                }
                hasDot = true;
                temp.append(currentChar);
            } else if (currentChar == 'e') {
                if (hasE || (i == str.length() - 1)) {
                    return null;
                }
                hasE = true;
                temp.append(currentChar);
            } else if (currentChar == '-') {
                minusCount++;
                temp.append(currentChar);
                if ((temp.length() != 1) && !hasE
                        || minusCount > 2
                        || ((temp.length() - 2 >= 0) && (temp.charAt(temp.length() - 2) == '-'))
                        || i == str.length() - 1) {
                    return null;
                }
            }
        }

        if (!(hasDot || hasE)) {
            if (Long.parseLong(temp.toString()) <= Integer.MAX_VALUE && Long.parseLong(temp.toString()) >= Integer.MIN_VALUE) {
                return Integer.valueOf(temp.toString());
            }
            return Long.valueOf(temp.toString());
        }
        return Double.valueOf(temp.toString());
    }
}
