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
        char[] strArray = str.toCharArray();
        boolean pointHave = false;
        boolean eHave = false;

        StringBuilder digit = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            switch (cur) {
                case '.':
                    if (pointHave) {
                        return null;
                    }
                    pointHave = true;
                    digit.append(cur);
                    break;
                case '-':
                    //Нет выхода за предел массива благодаря первому условию
                    if (i == strArray.length - 1 || strArray[i + 1] == '-') {
                        return null;
                    }
                    if (digit.length() != 0 && digit.charAt(digit.length() - 1) != 'e') {
                        return null;
                    }
                    digit.append(cur);
                    break;
                case 'e':
                    if (eHave || i == strArray.length - 1) {
                        return null;
                    }
                    eHave = true;
                    digit.append(cur);
                    break;
                default:
                    if (Character.isDigit(cur)) {
                        digit.append(cur);
                    }
                    break;
            }
        }
        if (pointHave || eHave) {
            return Double.parseDouble(digit.toString());
        } else {
            long resultLong = Long.parseLong(digit.toString());
            if (resultLong <= Integer.MAX_VALUE && resultLong >= Integer.MIN_VALUE) {
                return Integer.parseInt(digit.toString());
            } else {
                return resultLong;
            }
        }
    }

}
