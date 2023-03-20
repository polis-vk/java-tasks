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
        if (str == null || str.length() == 0) {
            return null;
        }
        int expCounter = 0;
        int expIndex = -1;
        int dotsCounter = 0;
        int minusCounter = 0;
        int firstMinusIndex = -1;
        int secondMinusIndex = -1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (Character.isDigit(symbol)) {
                result.append(symbol);
            } else if (symbol == 'e') {
                if (minusCounter == 2) {
                    return null;
                }
                expCounter++;
                expIndex = result.length();
                result.append(symbol);
            } else if (symbol == '.') {
                dotsCounter++;
                result.append(symbol);
            } else if (symbol == '-') {
                minusCounter++;
                if (minusCounter == 1) {
                    firstMinusIndex = result.length();
                } else if (minusCounter == 2) {
                    secondMinusIndex = result.length();
                }
                result.append(symbol);
            }
            if (dotsCounter > 1 || expCounter > 1 || minusCounter > 2) {
                return null;
            }
        }
        if (minusCounter == 1 && firstMinusIndex != 0 && expIndex + 1 != firstMinusIndex) {
            return null;
        }
        if (minusCounter == 2 && firstMinusIndex + 1 == secondMinusIndex) {
            return null;
        }
        if (expIndex == result.length() - 1) {
            return null;
        }
        if (dotsCounter == 1 || expCounter == 1) {
            return Double.parseDouble(result.toString());
        }
        long resultNumber = Long.parseLong(result.toString());
        if (resultNumber > Integer.MAX_VALUE || resultNumber < Integer.MIN_VALUE) {
            return resultNumber;
        }
        return (int) resultNumber;
    }
}
