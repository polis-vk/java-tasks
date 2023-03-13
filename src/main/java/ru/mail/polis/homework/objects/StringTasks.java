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

        StringBuilder result = new StringBuilder();
        byte countMinus = 0;
        boolean havePoint = false;
        boolean haveExp = false;

        for (int i = 0; i < str.length(); ++i) {
            char symbol = str.charAt(i);

            switch (symbol) {
                case '-':
                    if (countMinus == 2) {
                        return null;
                    }
                    if (countMinus == 1 && result.charAt(result.length() - 1) != 'e') {
                        return null;
                    }
                    if (countMinus == 0 && (result.length() != 0 && result.charAt(result.length() - 1) != 'e')) {
                        return null;
                    }
                    countMinus++;
                    break;
                case '.':
                    if (havePoint) {
                        return null;
                    }
                    havePoint = true;
                    break;
                case 'e':
                    if (haveExp) {
                        return null;
                    }
                    haveExp = true;
                    break;
                default:
                    if (!Character.isDigit(symbol)) {
                        continue;
                    }
            }
            result.append(symbol);
        }

        if (result.charAt(result.length() - 1) == '-') {
            return null;
        }
        if (haveExp && result.charAt(result.length() - 1) == 'e') {
            return null;
        }

        if (havePoint || haveExp) {
            return (Number) Double.valueOf(result.toString());
        }
        long number = Long.parseLong(result.toString());
        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return number;
        }
        return (int) number;
    }
}
