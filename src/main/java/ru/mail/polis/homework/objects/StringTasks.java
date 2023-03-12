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
        for (char c : str.toCharArray()) {
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
        String num = sb.toString();
        if (!Character.isDigit(num.charAt(num.length() - 1))) {
            return null;
        }
        if (isDot || isExp) {
            return Double.parseDouble(num);
        }
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return Long.valueOf(num);
        }
    }

}
