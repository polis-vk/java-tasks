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
        int dotsCounter = 0;
        int minusCounter = 0;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char newChar = str.charAt(i);
            if (Character.isDigit(newChar)) {
                result.append(newChar);
            } else if (newChar == 'e') {
                result.append(newChar);
                expCounter++;
                if (minusCounter == 2) {
                    return null;
                }
            } else if (newChar == '.') {
                result.append(newChar);
                dotsCounter++;
            } else if (newChar == '-') {
                result.append(newChar);
                minusCounter++;
                if (minusCounter == 2) {
                    if (result.charAt(result.length() - 2) != 'e') {
                        return null;
                    }
                }
            }
            if (dotsCounter > 1 || expCounter > 1 || minusCounter > 2) {
                return null;
            }
        }
        if (minusCounter == 1 && result.charAt(0) != '-' && result.indexOf("e") + 1 != result.indexOf("-")) {
            return null;
        }
        if (result.charAt(result.length() - 1) == 'e') {
            return null;
        }
        if (dotsCounter == 1 || expCounter == 1) {
            return Double.valueOf(result.toString());
        }
        Long resultNumber = Long.parseLong(result.toString());
        if (resultNumber > Integer.MAX_VALUE || resultNumber < Integer.MIN_VALUE) {
            return resultNumber;
        }
        return resultNumber.intValue();

    }
}
