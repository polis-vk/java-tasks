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

        String digitString = "";
        int lenghtOfStr = str.length();
        char currentChar;
        boolean wasMinus = false;
        boolean wasE = false;
        boolean wasDot = false;

        for (int i = 0; i < lenghtOfStr; i++) {
            currentChar = str.charAt(i);

            if (currentChar == '-' && !wasMinus) {
                digitString = digitString + "-";
                wasMinus = true;
            }
            if (wasMinus && wasE) {
                return null;
            }
            if (currentChar == '.' && !wasDot) {
                digitString = digitString + ".";
                wasDot = true;
            }
            if (currentChar == 'e' && wasE) {
                return null;
            }
            if (currentChar == 'e' && !wasE) {
                digitString = digitString + "e";
                wasE = true;
                wasMinus = false; // после числа Эйлера может быть еще один минус
            }

            if (Character.isDigit(currentChar)) {
                digitString = digitString + currentChar;
            }

//            if (Character.isDigit(currentChar)) {
//                if (wasDot && !wasE) {
//                    numberAfterDot += 1;
//                    if (digit < 0) {
//                        digitD = digitD - Character.getNumericValue(currentChar) * Math.pow(0.1, numberAfterDot);
//                    } else {
//                        digitD = digitD + Character.getNumericValue(currentChar) * Math.pow(0.1, numberAfterDot);
//                    }
//                    result = digitD;
//                } else {
//                    if (digit < 0) {
//                        digit = digit * 10 - Character.getNumericValue(currentChar);
//                    } else {
//                        digit = digit * 10 + Character.getNumericValue(currentChar);
//                    }
//                    result = digit;
//                }
//
//                if (wasMinus) {
//                    digit *= -1;
//                    wasMinus = false;
//                }
//            }

        }

        return null;
    }
}
