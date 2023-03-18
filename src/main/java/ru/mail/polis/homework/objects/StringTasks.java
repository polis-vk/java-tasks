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

        StringBuilder sb = new StringBuilder();

        boolean countE = true;
        boolean countDots = true;

        char[] arrayOfStr = str.toCharArray();
        for (int i = 0; i < arrayOfStr.length; i++) {
            if (Character.isDigit(arrayOfStr[i])) {
                sb.append(str.charAt(i));
                continue;
            }

            if (arrayOfStr[i] == 'e') {
                if (!countE || i == str.length() - 1) {
                    return null;
                }
                sb.append(str.charAt(i));
                countE = false;
                continue;
            }

            if (arrayOfStr[i] == '.') {
                if (!countDots) {
                    return null;
                }
                sb.append(str.charAt(i));
                countDots = false;
                continue;
            }

            if (arrayOfStr[i] == '-') {
                if (sb.length() == 0 || sb.charAt(sb.length() - 1) == 'e') {
                    sb.append(str.charAt(i));
                    continue;
                }
                return null;
            }
        }

        if (!countE || !countDots) {
            return Double.valueOf(sb.toString());
        }

        long number = Long.parseLong(sb.toString());
        if (Integer.MIN_VALUE > number ||
                number > Integer.MAX_VALUE) {
            return number;
        }

        return Integer.valueOf(sb.toString());
    }
}
