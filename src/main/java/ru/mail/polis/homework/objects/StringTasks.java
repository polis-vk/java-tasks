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
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            if (Character.isDigit(str.charAt(j)) || str.charAt(j) == 'e' || str.charAt(j) == '.' || str.charAt(j) == '-') {
                sb.append(str.charAt(j));
            }
        }
        boolean negative = false;
        boolean hasDot = false;
        boolean hasE = false;
        double result = 0;
        int eCount = 0;
        int i = 0;
        // Проверяем, что первый символ - знак "-" (если есть)
        if (sb.charAt(0) == '-') {
            negative = true;
            i++;
        }

        // Проходим по каждому символу в строке
        for (; i < sb.length(); i++) {
            char c = sb.charAt(i);
            // Проверяем, является ли символ цифрой, точкой, знаком "-" или "e"
            if (Character.isDigit(c)) {
                if (hasE) {
                    eCount = eCount * 10 + (c - '0');
                } else {
                    result = result * 10 + (c - '0');
                }
                continue;
            }
            if (c == '.') {
                if (hasDot || hasE) {
                    return null; // Ошибка: точка уже встречалась или число имеет экспоненциальную запись
                }
                hasDot = true;
                continue;
            }
            if (c == 'e') {
                if (hasE || i == sb.length() - 1) {
                    return null; // Ошибка: число уже имеет экспоненциальную запись, "e" находится в начале или в конце строки
                }
                hasE = true;
                continue;
            }
            if (c == '-') {
                if (sb.charAt(i - 1) != 'e') {
                    return null; // Ошибка: знак "+" или "-" может следовать только за "e" или "E"
                }
            } else {
                return null; // Ошибка: недопустимый символ
            }
        }

        // Если число отрицательное, меняем знак
        if (negative) {
            result = -result;
        }
        // Если есть точка, делим результат на 10 в степени dotPosition
        if (hasDot) {
            result = result / Math.pow(10, sb.length() - sb.indexOf(".") - 1 - (hasE ? (sb.length() - sb.indexOf("e")) : 0));
        }

        // Если есть "e", умножаем результат на 10 в степени ePosition или делаем на 10 в степени ePosition (если ePosition отрицательное)
        if (hasE) {
            result = result * Math.pow(10, eCount * (sb.charAt(sb.indexOf("e") + 1) == '-' ? -1 : 1));
        }
        if (hasE || hasDot) {
            return result;
        }
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
            return (long) result;
        }
        return (int) result;
    }
}
