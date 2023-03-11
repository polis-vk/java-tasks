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
        String number = "";
        byte[] countLimited = {0, 0};
        for (char ch: str.toCharArray()) {
            if (Character.isDigit(ch)) {
                number += ch;
            } else if (ch == 'e' || ch == '.') {
                countLimited[(int) ch % 2] += 1; // In ASCII 'e' is odd, '.' is even
                if (countLimited[0] > 1 || countLimited[1] > 1) {
                    return null;
                }
                number += ch;
            } else if (ch == '-') {
                if (!(number.isEmpty() || number.charAt(number.length() - 1) == 'e')) {
                    return null;
                }
                number += ch;
            }
        }

        if (!Character.isDigit(number.charAt(number.length() - 1))) {
            return null;
        }
        if (countLimited[0] > 0 || countLimited[1] > 0) {
            return Double.parseDouble(number);
        }
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return Long.valueOf(number);
        }
    }
}
