package ru.mail.polis.homework.objects;

import com.sun.tools.javac.util.StringUtils;

import java.util.Objects;

public class StringTasks {


    private static int podschetSimvola(StringBuilder str, char c) {
        int k = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                k += 1;
            }
        }
        return k;
    }

    private static int inDex(StringBuilder str, char s) {

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == s) {
                return i;
            }
        }
        return -1;
    }

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
        if (Objects.equals(str, null) || str.length() == 0) {
            return null;
        }

        StringBuilder chislo = new StringBuilder();
        int N = str.length();
        for (int i = 0; i < N; i++) {
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == 'e' || str.charAt(i) == '.') {
                chislo.append(str.charAt(i));
            }
        }


        int indexDot = inDex(chislo, '.');
        int indexE = inDex(chislo, 'e');
        if (podschetSimvola(chislo, 'e') > 1 || podschetSimvola(chislo, '.') > 1 || podschetSimvola(chislo, '-') > 2
                || indexDot == 0 || indexDot == chislo.length()) {
            return null;
        }


        int lenChislo = chislo.length();
        chislo.insert(0, '?');
        chislo.insert(chislo.length(), '?');
        for (int i = 1; i < lenChislo + 1; i++) {
            if (chislo.charAt(i) == '-') {
                if (chislo.charAt(i - 1) == '.' || Character.isDigit(chislo.charAt(i - 1)) ||
                        !Character.isDigit(chislo.charAt(i + 1))) {
                    return null;
                }
            }
            if (chislo.charAt(i) == 'e') {
                if (!Character.isDigit(chislo.charAt(i - 1)) || chislo.charAt(i + 1) == '?' || chislo.charAt(i + 1) == '.') {
                    return null;
                }
            }

            if (chislo.charAt(i) == '.') {
                if (!Character.isDigit(chislo.charAt(i - 1)) || !Character.isDigit(chislo.charAt(i + 1))) {
                    return null;
                }
            }
        }

        if (indexDot != -1 || indexE != -1) {
            return Double.valueOf(chislo.substring(1, lenChislo + 1));
        }
        if (Integer.MIN_VALUE <= Long.parseLong(chislo.substring(1, lenChislo + 1)) &&
                Long.parseLong(chislo.substring(1, lenChislo + 1)) <= Integer.MAX_VALUE) {
            return Integer.parseInt(chislo.substring(1, lenChislo + 1));
        }
        return Long.valueOf(chislo.substring(1, lenChislo + 1));

    }
}
