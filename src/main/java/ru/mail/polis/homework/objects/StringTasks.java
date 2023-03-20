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

        StringBuilder numberStr = new StringBuilder();

        char symbol;
        boolean isThereDot = false;
        boolean isThereEps = false;

        for (int i = 0; i < str.length(); ++i) {
            symbol = str.charAt(i);
            if (Character.isDigit(symbol)) {
                numberStr.append(symbol);
                continue;
            }
            switch (symbol) {
                case '-':
                    if (numberStr.length() != 0) {
                        if (numberStr.charAt(numberStr.length() - 1) != 'e') {
                            return null;
                        }
                    }
                    numberStr.append(symbol);
                    break;
                case '.':
                    if (isThereDot) {
                        return null;
                    }
                    numberStr.append(symbol);
                    isThereDot = true;
                    break;
                case 'e':
                    if (isThereEps || i + 1 == str.length()) {
                        return null;
                    }
                    numberStr.append(symbol);
                    isThereEps = true;
                    break;
                default:
                    break;
            }
        }

        if (isThereDot || isThereEps) {
            return Double.parseDouble(numberStr.toString());
        }
        long longNumber = Long.parseLong(numberStr.toString());
        if (longNumber >= Integer.MIN_VALUE && longNumber <= Integer.MAX_VALUE) {
            return (int) longNumber;
        }
        return longNumber;
    }
}
