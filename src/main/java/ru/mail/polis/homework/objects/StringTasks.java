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
        if (str == null || str.equals("")) {
            return null;
        }
        String newstr = str.replaceAll("[^-.e\\d]", "");
        boolean negative = false;
        boolean hasDot = false;
        boolean hasE = false;
        double result = 0;
        int eCount = 0;
        int dotPosition = 0;
        int ePosition = 0;
        int i = 0;

        // Проверяем, что первый символ - знак "-" (если есть)
        if (newstr.charAt(0) == '-') {
            negative = true;
            i++;
        }

        // Проходим по каждому символу в строке
        for (; i < newstr.length(); i++) {
            char c = newstr.charAt(i);

            // Проверяем, является ли символ цифрой, точкой, знаком "-" или "e"
            if (c >= '0' && c <= '9') {
                if (hasDot && !hasE) {
                    dotPosition++;
                }
                if (hasE) {
                    eCount = eCount * 10 + (c - '0');
                }
                else {
                    result = result * 10 + (c - '0');
                }
            } else if (c == '.') {
                if (hasDot || hasE) {
                    return null; // Ошибка: точка уже встречалась или число имеет экспоненциальную запись
                }
                hasDot = true;
            } else if (c == 'e' || c == 'E') {
                if (hasE || i == 0 || i == newstr.length() - 1) {
                    return null; // Ошибка: число уже имеет экспоненциальную запись, "e" находится в начале или в конце строки
                }
                hasE = true;
                ePosition = i;
            } else if (c == '+' || c == '-') {
                if (i == 0 || newstr.charAt(i - 1) != 'e' && newstr.charAt(i - 1) != 'E') {
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
            result = result / Math.pow(10, dotPosition);
        }

        // Если есть "e", умножаем результат на 10 в степени ePosition или делаем на 10 в степени ePosition (если ePosition отрицательное)
        if (hasE) {
            result = result * Math.pow(10, eCount * (newstr.charAt(ePosition + 1) == '-' ? -1 : 1));
        }
        if (hasE || hasDot) {
            return result;
        } else {
            if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                return (long) result;
            } else {
                return (int) result;
            }
        }
    }
}
