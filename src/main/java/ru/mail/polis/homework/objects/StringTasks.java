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

        int dotCount = 0;
        int eCount = 0;
        boolean isDashPrevious = false;

        int i = 0;
        while (i < str.length()) {
            if (Character.isDigit(str.charAt(i))) {
                isDashPrevious = false;
                ++i;
                continue;
            }
            if (Character.isLetter(str.charAt(i)) && str.charAt(i) != 'e') {
                isDashPrevious = false;
                str = str.substring(0, i) + str.substring(i + 1);
                continue;
            }
            switch (str.charAt(i)) {
                case ('-'):
                    boolean isWrong = i != 0 && str.charAt(i - 1) != 'e';
                    if (isDashPrevious || isWrong) {
                        return null;
                    }
                    isDashPrevious = true;
                    break;
                case ('.'):
                    ++dotCount;
                    break;
                case ('e'):
                    if (i + 1 == str.length()) {
                        return null;
                    }
                    ++eCount;
                    break;
                default:
                    break;
            }

            if (dotCount > 1 || eCount > 1) {
                return null;
            }
            ++i;
        }

        if (dotCount == 1 || eCount == 1) {
            return Double.parseDouble(str);
        }
        long longNumber = Long.parseLong(str);
        if (longNumber >= Integer.MIN_VALUE && longNumber <= Integer.MAX_VALUE) {
            return Integer.parseInt(str);
        }

        return longNumber;
    }
}
