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
        StringBuilder stringBuilder = new StringBuilder();
        boolean pointFlag = false;
        boolean expFlag = false;
        for (int i = 0; i < str.length(); i++) {
            char symbol = str.charAt(i);
            if (Character.isDigit(symbol)) {
                stringBuilder.append(symbol);
            } else if (symbol == '.') {
                if (!pointFlag) {
                    stringBuilder.append(symbol);
                    pointFlag = true;
                } else {
                    return null;
                }
            } else if (symbol == 'e') {
                if (!expFlag && i != str.length() - 1) {
                    stringBuilder.append(symbol);
                    expFlag = true;
                } else {
                    return null;
                }
            } else if (symbol == '-') {
                if ((stringBuilder.length() == 0 || stringBuilder.charAt(stringBuilder.length() - 1) == 'e')
                        && i != str.length() - 1) {
                    stringBuilder.append(symbol);
                } else {
                    return null;
                }
            }
        }
        String completedString = stringBuilder.toString();

        if (pointFlag || expFlag) {
            return Double.parseDouble(completedString);
        }
        long longResult = Long.parseLong(completedString);
        if (longResult <= Integer.MAX_VALUE &&
                longResult >= Integer.MIN_VALUE) {
            return Integer.parseInt(completedString);
        } else return longResult;

    }
}
