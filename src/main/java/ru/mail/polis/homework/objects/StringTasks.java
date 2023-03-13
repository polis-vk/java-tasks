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
        boolean isExp = false;
        boolean isDot = false;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (c == '.') {
                if (isDot) {
                    return null;
                }
                isDot = true;
                sb.append(c);
            } else if (c == 'e') {
                if (isExp) {
                    return null;
                }
                isExp = true;
                sb.append(c);
            } else if (c == '-') {
                if (sb.length() != 0 && sb.charAt(sb.length() - 1) != 'e') {
                    return null;
                }
                sb.append(c);
            }
        }
        String strNum = sb.toString();
        if (!Character.isDigit(strNum.charAt(strNum.length() - 1))) {
            return null;
        }
        if (isDot || isExp) {
            return Double.parseDouble(strNum);
        }
        long num = Long.parseLong(strNum);
        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
            return num;
        }
        return (int) num;
    }

}
