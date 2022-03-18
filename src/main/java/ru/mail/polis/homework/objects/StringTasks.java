package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (Character.isDigit(str.charAt(i)) || Constant.isConstant(str.charAt(i))) {
                resultString.append(str.charAt(i));
            }
        }
        int indexDot = resultString.indexOf(String.valueOf(Constant.DOT));
        int indexExp = resultString.indexOf(String.valueOf(Constant.EXP));
        try {
            if (indexDot != -1 || indexExp != -1) {
                return Double.valueOf(resultString.toString());
            }
            long resValue = Long.parseLong(resultString.toString());
            if (resValue > Integer.MAX_VALUE || resValue < Integer.MIN_VALUE) {
                return resValue;
            }

            return (int) resValue;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static class Constant {
        private final static char MINUS = '-';
        private final static char EXP = 'e';
        private final static char DOT = '.';

        private static boolean isConstant(char el) {
            return el == MINUS || el == EXP || el == DOT;
        }
    }
}
