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
        char[] ch = str.toCharArray();
        boolean haveE = false;
        boolean haveDot = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(ch[i])) {
                sb.append(ch[i]);
            }
            if (ch[i] == 'e' && Character.isDigit(sb.charAt(sb.length() - 1))) {
                if (haveE) {
                    return null;
                }
                sb.append(ch[i]);
                haveE = true;
            }
            if (ch[i] == '-') {
                if ((sb.length() == 0 || sb.charAt(sb.length() - 1) == 'e')) {
                    sb.append(ch[i]);
                } else if (Character.isDigit(sb.charAt(sb.length() - 1)) || sb.charAt(sb.length() - 1) == '-') {
                    return null;
                }
            }
            if (ch[i] == '.') {
                if (haveDot) {
                    return null;
                }
                if (Character.isDigit(sb.charAt(sb.length() - 1))) {
                    sb.append(ch[i]);
                    haveDot = true;
                }
            }
        }
        if (sb.charAt(sb.length() - 1) == '.' || sb.charAt(sb.length() - 1) == 'e' || sb.charAt(sb.length() - 1) == '-') {
            return null;
        }
        String finalStr = sb.toString();
        if (haveDot | haveE) {
            return Double.valueOf(finalStr);
        }
        if (Long.valueOf(finalStr) > Integer.MAX_VALUE || Long.valueOf(finalStr) < Integer.MIN_VALUE) {
            return Long.valueOf(finalStr);
        }
        return Integer.valueOf(finalStr);

    }
}
