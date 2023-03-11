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
        if (str == null || str.isEmpty()) return null;

        StringBuilder temp = new StringBuilder(str);

        for (int i = 0; i < temp.length(); i++) {
            char currentChar = temp.charAt(i);
            if (!(Character.isDigit(currentChar) || currentChar == 'e' || currentChar == '-' || currentChar == '.')) {
                temp.deleteCharAt(i);
                i--;
            }
        }

        try {
            try {
                try {
                    return Integer.valueOf(temp.toString());
                } catch (NumberFormatException e) {
                    return Long.valueOf(temp.toString());
                }
            } catch (NumberFormatException e) {
                return Double.valueOf(temp.toString());
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
