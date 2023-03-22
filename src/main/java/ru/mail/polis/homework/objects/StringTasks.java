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
        StringBuilder tmpStr = new StringBuilder();
        char[] ch = str.toCharArray();
        boolean haveE = false;
        boolean haveDot = false;
        char lastCharacter = 'e';
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(ch[i])) {
                tmpStr.append(ch[i]);
                lastCharacter = tmpStr.charAt(tmpStr.length() - 1);
            }
            if (ch[i] == 'e' && Character.isDigit(lastCharacter)) {
                if (haveE) {
                    return null;
                }
                tmpStr.append(ch[i]);
                lastCharacter = tmpStr.charAt(tmpStr.length() - 1);
                haveE = true;
            }
            if (ch[i] == '-') {
                if ((tmpStr.length() == 0 || lastCharacter == 'e')) {
                    tmpStr.append(ch[i]);
                    lastCharacter = tmpStr.charAt(tmpStr.length() - 1);
                } else if (Character.isDigit(lastCharacter) || lastCharacter == '-') {
                    return null;
                }
            }
            if (ch[i] == '.') {
                if (haveDot) {
                    return null;
                }
                if (Character.isDigit(lastCharacter)) {
                    tmpStr.append(ch[i]);
                    lastCharacter = tmpStr.charAt(tmpStr.length() - 1);
                    haveDot = true;
                }
            }
        }
        if (lastCharacter == '.' || lastCharacter == 'e' || lastCharacter == '-') {
            return null;
        }
        String finalStr = tmpStr.toString();
        if (haveDot | haveE) {
            return Double.valueOf(finalStr);
        }
        long tmpLongValue = Long.valueOf(finalStr);
        if (tmpLongValue <= Integer.MAX_VALUE & tmpLongValue >= Integer.MIN_VALUE) {
            return Integer.valueOf(finalStr);
        }
        return tmpLongValue;
    }
}
